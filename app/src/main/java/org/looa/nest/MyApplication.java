package org.looa.nest;

import org.looa.nest.host.PluginManager;
import org.looa.nest.host.ProxyApplication;

/**
 * my application.
 * <p>
 * Created by ran on 2017/8/18.
 */

public class MyApplication extends ProxyApplication {

    public static final String[] packageNames = {
            "org.looa.hellowordbee"
    };

    @Override
    public void onCreate() {
        super.onCreate();
        PluginManager.getInstance().initPlugin(this, MyApplication.packageNames);
    }

    @Override
    public String[] getPluginPackageNames() {
        return packageNames;
    }
}
