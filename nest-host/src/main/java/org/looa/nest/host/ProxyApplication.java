package org.looa.nest.host;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

/**
 * proxy application.
 * <p>
 * Created by ran on 2017/8/18.
 */

public abstract class ProxyApplication extends Application {

    private String TAG = getClass().getName();

    /**
     * 获取预装插件的包名组
     *
     * @return
     */
    public abstract String[] getPluginPackageNames();

    @Override
    public Resources getResources() {
        String[] packageNames;
        if ((packageNames = getPluginPackageNames()) == null) return super.getResources();
        StackTraceElement[] stackElements = new Throwable().getStackTrace();
        if (stackElements != null) {
            for (StackTraceElement stackElement : stackElements) {
                String className = stackElement.getClassName();
                for (String packageName : packageNames) {
                    if (className.contains(packageName)) {
                        return PluginManager.getInstance().getResources(packageName);
                    }
                }
            }
        }
        return super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        String[] packageNames;
        if ((packageNames = getPluginPackageNames()) == null) return super.getAssets();
        StackTraceElement[] stackElements = new Throwable().getStackTrace();
        if (stackElements != null) {
            for (StackTraceElement stackElement : stackElements) {
                String className = stackElement.getClassName();
                for (String packageName : packageNames) {
                    if (className.contains(packageName)) {
                        return PluginManager.getInstance().getAssets(packageName);
                    }
                }
            }
        }
        return super.getAssets();
    }

    @Override
    public Resources.Theme getTheme() {
        String[] packageNames;
        if ((packageNames = getPluginPackageNames()) == null) return super.getTheme();
        StackTraceElement[] stackElements = new Throwable().getStackTrace();
        if (stackElements != null) {
            for (StackTraceElement stackElement : stackElements) {
                String className = stackElement.getClassName();
                for (String packageName : packageNames) {
                    if (className.contains(packageName)) {
                        return PluginManager.getInstance().getTheme(packageName);
                    }
                }
            }
        }
        return super.getTheme();
    }

    @Override
    public ClassLoader getClassLoader() {
        String[] packageNames;
        if ((packageNames = getPluginPackageNames()) == null) return super.getClassLoader();
        long startTime = System.currentTimeMillis();
        StackTraceElement[] stackElements = new Throwable().getStackTrace();
        if (stackElements != null) {
            for (StackTraceElement stackElement : stackElements) {
                Log.d(TAG, "getClassLoader: " + stackElement.getClassName());
                String className = stackElement.getClassName();
                for (String packageName : packageNames) {
                    if (className.contains(packageName)) {
                        Log.i(TAG, "getClassLoader: success!");
                        Log.w(TAG, "The time spent is " + (System.currentTimeMillis() - startTime) + " millis.");
                        return PluginManager.getInstance().getDexClassLoader(this, packageName);
                    }
                }
            }
        }
        return super.getClassLoader();
    }
}
