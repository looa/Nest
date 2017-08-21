package org.looa.nest;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;

/**
 * my application.
 * <p>
 * Created by ran on 2017/8/18.
 */

public class MyApplication extends Application {

    public static final String[] packageNames = {
            "org.looa.hellowordbee"
    };

    @Override
    public void onCreate() {
        super.onCreate();
        PluginManager.getInstance().initPlugin(this, packageNames);
    }

    @Override
    public Resources getResources() {
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
