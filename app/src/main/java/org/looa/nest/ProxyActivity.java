package org.looa.nest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

import static android.content.ContentValues.TAG;

/**
 * 代理Activity（Host Activity）
 * <p>
 * Created by ran on 2017/8/18.
 */

public class ProxyActivity extends Activity {

    public static final String FROM = "plugin.from";
    public static final int FROM_EXTERNAL = 0;
    public static final int FROM_INTERNAL = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainProxyData();
    }

    private void obtainProxyData() {
        Intent intent = getIntent();
        String pluginPackage = intent.getStringExtra(PluginManager.KEY_PLUGIN_PACKAGE);

        PluginInfo pluginInfo = PluginManager.getInstance().getPluginInfo(this, pluginPackage);

        String pluginDexPath = pluginInfo.getPluginPath();
        String pluginClass = intent.getStringExtra(PluginManager.KEY_PLUGIN_CLASS);
        launchTargetActivity(pluginDexPath, pluginClass);
    }

    private void launchTargetActivity(String pluginDexPath, String pluginClass) {
        if (pluginDexPath == null) {
            finish();
            return;
        } else if (pluginClass == null) {
            PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(pluginDexPath, PackageManager.GET_ACTIVITIES);
            if (packageInfo.activities != null && packageInfo.activities.length > 0) {
                pluginClass = packageInfo.activities[0].name;
            }
        }
        if (pluginClass == null) {
            finish();
            return;
        }
        File dexOutputDir = this.getDir("dex", MODE_PRIVATE);
        final String dexOutputPath = dexOutputDir.getAbsolutePath();
        ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
        DexClassLoader dexClassLoader = new DexClassLoader(pluginDexPath, dexOutputPath, null, localClassLoader);
        try {
            Class<?> localClass = dexClassLoader.loadClass(pluginClass);
            Constructor<?> localConstructor = localClass.getConstructor(new Class[]{});
            Object instance = localConstructor.newInstance(new Object[]{});

            Method[] methods = localClass.getDeclaredMethods();
            for (Method m : methods) {
                Log.e(TAG, "launchTargetActivity: " + m.getName());
            }


            Method setProxy = localClass.getDeclaredMethod("setProxy", Activity.class);
            setProxy.setAccessible(true);
            setProxy.invoke(instance, this);

            Method onCreate = localClass.getDeclaredMethod("onCreate", Bundle.class);
            onCreate.setAccessible(true);
            Bundle bundle = new Bundle();
            bundle.putInt(FROM, FROM_EXTERNAL);
            onCreate.invoke(instance, new Object[]{bundle});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        intent.setClass(this, ProxyActivity.class);
        intent.putExtra(PluginManager.KEY_PLUGIN_PACKAGE, MyApplication.packageNames[0]);
        super.startActivity(intent);
    }
}
