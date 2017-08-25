package org.looa.nest.plugin;

import android.app.Application;
import android.content.Context;

/**
 * plugin application.
 * <p>
 * Created by ran on 2017/8/25.
 */

public class PluginApplication extends Application implements PluginApplicationService {

    private Context mProxyApplication;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void attach(Context application) {
        mProxyApplication = application;
        attachBaseContext(mProxyApplication);
    }

    @Override
    public void outCreate() {
        onCreate();
    }
}
