package org.looa.nest.host;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;

/**
 * proxy application.
 * <p>
 * Created by ran on 2017/8/18.
 */

public abstract class ProxyApplication extends Application {

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
}
