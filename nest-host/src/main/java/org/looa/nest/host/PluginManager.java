package org.looa.nest.host;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import dalvik.system.DexClassLoader;

import static android.content.Context.MODE_PRIVATE;

/**
 * 插件管理
 * <p>
 * Created by ran on 2017/8/18.
 */

public class PluginManager {

    private static final String TAG = "PluginManager";

    private Context context;

    public static final String KEY_PLUGIN_CLASS = "PLUGIN_CLASS";
    public static final String KEY_PLUGIN_PACKAGE = "PLUGIN_PACKAGE";

    private static final String ASSETS_PLUGIN_PATH = "plugin";

    private String[] plugins;
    private String pluginInstallDir;

    private HashMap<String, AssetManager> assetManagerHashMap = new HashMap<>();
    private HashMap<String, Resources> resourcesHashMap = new HashMap<>();
    private HashMap<String, Resources.Theme> themeHashMap = new HashMap<>();
    private HashMap<String, ActivityInfo> activityInfoHashMap = new HashMap<>();
    private HashMap<String, DexClassLoader> dexClassLoaderHashMap = new HashMap<>();

    private PluginManager() {
    }

    private static class Holder {
        final static PluginManager INSTANCE = new PluginManager();
    }

    public static PluginManager getInstance() {
        return Holder.INSTANCE;
    }

    public void initPlugin(Context context, String... packageNames) {
        this.context = context;
        pluginInstallDir = context.getFilesDir().getPath()
                + File.separator
                + "plugin"
                + File.separator
                + "install";
        initPluginsAssetsPath();
        if (packageNames == null) return;
        for (String packageName : packageNames) {
            if (!isPluginInstall(packageName)) {
                installPluginFromAssets(packageName);
            }
            initPluginsResources(packageName);
        }
    }

    private void initPluginsAssetsPath() {
        try {
            plugins = context.getAssets().list(ASSETS_PLUGIN_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean installPluginFromAssets(String packageName) {
        if (packageName != null && packageName.lastIndexOf(".") != -1) {
            String pluginName = packageName.substring(packageName.lastIndexOf(".") + 1);
            if (plugins != null && plugins.length > 0) {
                for (String plugin : plugins) {
                    if (plugin.contains(pluginName)) {
                        return installPluginFromAssets2Cache(packageName, ASSETS_PLUGIN_PATH + File.separator + plugin);
                    }
                }
            }
        }
        return false;
    }

    private boolean installPluginFromAssets2Cache(String packageName, String path) {
        String cacheFilePath = context.getCacheDir() + File.separator + path;
        try {
            FileUtils.copyAsserts(context, path, cacheFilePath);
            String pluginPath = getPluginInstallPath(packageName);
            FileUtils.copyFileToFile(cacheFilePath, pluginPath);
            FileUtils.deleteFile(cacheFilePath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    private void initPluginsResources(String packageName) {
        AssetManager assetManager = null;
        try {
            assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, getPluginInstallPath(packageName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resources res = context.getResources();
        Resources mResources = new Resources(assetManager, res.getDisplayMetrics(), res.getConfiguration());
        Resources.Theme mTheme = mResources.newTheme();
        mTheme.setTo(context.getTheme());

        assetManagerHashMap.put(packageName, assetManager);
        resourcesHashMap.put(packageName, mResources);
        themeHashMap.put(packageName, mTheme);
    }

    public Resources getResources(String packageName) {
        return resourcesHashMap.get(packageName);
    }

    public AssetManager getAssets(String packageName) {
        return assetManagerHashMap.get(packageName);
    }

    public Resources.Theme getTheme(String packageName) {
        Resources.Theme theme = themeHashMap.get(packageName);
//        ActivityInfo activityInfo = getPluginActivity(packageName, activityName);
//        int themeResID = activityInfo.theme;
//        if (themeResID == 0) {
//            try {
//                themeResID = getPluginPackageInfo(packageName).applicationInfo.theme;
//            } catch (Exception e) {
//                themeResID = this.context.getApplicationInfo().theme;
//            }
//        }
//        context.setTheme(themeResID);
//        theme.setTo(context.getTheme());
        return theme;
    }


    private boolean isPluginInstall(String packageName) {
        String path = getPluginInstallPath(packageName);
        File file = new File(path);
        boolean exists = file.exists();
        //TODO
        return false;
    }

    public PackageInfo getPluginPackageInfo(String packageName) {
        String pluginDexPath = getPluginInstallPath(packageName);
        return context.getPackageManager().getPackageArchiveInfo(pluginDexPath, PackageManager.GET_ACTIVITIES | PackageManager.GET_META_DATA);
    }

    public ActivityInfo[] getPluginActivities(String packageName) {
        PackageInfo packageInfo = getPluginPackageInfo(packageName);
        return packageInfo.activities;
    }

    public ActivityInfo getPluginActivity(String packageName, String activityName) {
        ActivityInfo result;
        if ((result = activityInfoHashMap.get(packageName + activityName)) == null) {
            ActivityInfo[] info = getPluginActivities(packageName);
            if (info != null) {
                for (ActivityInfo activityInfo : info) {
                    if (activityName.equals(activityInfo.name)) {
                        activityInfoHashMap.put(packageName + activityName, activityInfo);
                        return activityInfo;
                    }
                }
            }
            return null;
        } else {
            return result;
        }
    }

    public String getPluginLauncherActivity(String packageName) {
        String pluginClass = null;
        ActivityInfo[] info = getPluginActivities(packageName);
        if (info != null && info.length > 0) {
            pluginClass = info[0].name;
        }
        return pluginClass;
    }

    /**
     * 获取dexClassLoader
     *
     * @param context     不能是ApplicationContext
     * @param packageName 待加载dex的包名
     * @return
     */
    public DexClassLoader getDexClassLoader(Context context, String packageName) {
        DexClassLoader dexClassLoader = dexClassLoaderHashMap.get(packageName);
        if (dexClassLoader == null) {
            PluginInfo pluginInfo = PluginManager.getInstance().getPluginInfo(packageName);
            String pluginDexPath = pluginInfo.getPluginPath();
            File dexOutputDir = context.getDir("dex", MODE_PRIVATE);
            final String dexOutputPath = dexOutputDir.getAbsolutePath();
            ClassLoader localClassLoader = context.getClassLoader();
            dexClassLoader = new DexClassLoader(pluginDexPath, dexOutputPath, null, localClassLoader);
            dexClassLoaderHashMap.put(packageName, dexClassLoader);
        }
        return dexClassLoader;
    }

    public PluginInfo getPluginInfo(String packageName) {
        PluginInfo info = new PluginInfo();
        if (packageName != null && packageName.lastIndexOf(".") != -1) {
            String pluginName = packageName.substring(packageName.lastIndexOf(".") + 1);
            String pluginPath = null;
            String versionName = null;
            String pluginAppName = null;
            String pluginAppIntroduce = null;
            int pluginAppIcon = 0;
            int versionCode = 0;
            if (plugins != null && plugins.length > 0) {
                for (String plugin : plugins) {
                    if (plugin.contains(pluginName)) {
                        pluginPath = getPluginInstallPath(packageName);
                        PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(
                                pluginPath, PackageManager.GET_META_DATA);
                        if (packageInfo == null) return info;
                        Bundle bundle = packageInfo.applicationInfo.metaData;
                        if (bundle != null) {
                            pluginAppName = bundle.getString("pluginName");
                            pluginAppIntroduce = bundle.getString("pluginIntroduce");
                        }
                        if (!packageName.equals(packageInfo.packageName)) return info;
                        versionCode = packageInfo.versionCode;
                        versionName = packageInfo.versionName;
                        pluginAppIcon = packageInfo.applicationInfo.icon;
                        break;
                    }
                }
            }
            info.setPluginAppIcon(pluginAppIcon);
            info.setPluginAppName(pluginAppName);
            info.setPluginAppIntroduce(pluginAppIntroduce);
            info.setPackageName(packageName);
            info.setVersionName(versionName);
            info.setVersionCode(versionCode);
            info.setPluginName(pluginName);
            info.setPluginPath(pluginPath);
        }
        return info;
    }

    private String getPluginInstallPath(String packageName) {
        if (packageName != null) {
            return pluginInstallDir + File.separator + packageName + File.separator + "bee.apk";
        }
        return null;
    }

}
