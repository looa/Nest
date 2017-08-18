package org.looa.nest;

import android.app.Application;

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
}
