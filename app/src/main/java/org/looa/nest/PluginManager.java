package org.looa.nest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;

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
            Log.w(TAG, "initPlugin: " + isPluginInstall(packageName));
            if (!isPluginInstall(packageName)) {
                installPluginFromAssets(packageName);
            }
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
            File file2 = new File(pluginPath);
            FileUtils.deleteFile(cacheFilePath);
            Log.e(TAG, "installPluginFromAssets2Cache: pluginPath    = " + file2.length());
            return true;
        } catch (IOException e) {
            Log.e(TAG, "installPluginFromAssets2Cache: " + e.getMessage());
            return false;
        }
    }


    private boolean isPluginInstall(String packageName) {
        String path = getPluginInstallPath(packageName);
        File file = new File(path);
        boolean exists = file.exists();
        Log.w(TAG, "isPluginInstall: " + file.length());
        return exists && !BuildConfig.DEBUG;
    }

    public PluginInfo getPluginInfo(Context context, String packageName) {
        PluginInfo info = new PluginInfo();
        if (packageName != null && packageName.lastIndexOf(".") != -1) {
            String pluginName = packageName.substring(packageName.lastIndexOf(".") + 1);
            String pluginPath = null;
            String versionName = null;
            int versionCode = 0;
            if (plugins != null && plugins.length > 0) {
                for (String plugin : plugins) {
                    if (plugin.contains(pluginName)) {
                        pluginPath = getPluginInstallPath(packageName);
                        Log.w(TAG, "getPluginInfo: " + pluginPath);
                        PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(
                                pluginPath, PackageManager.GET_META_DATA);
                        if (packageInfo == null) return info;
                        if (!packageName.equals(packageInfo.packageName)) return info;
                        versionCode = packageInfo.versionCode;
                        versionName = packageInfo.versionName;
                        break;
                    }
                }
            }
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
