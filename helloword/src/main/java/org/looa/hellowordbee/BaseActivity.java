package org.looa.hellowordbee;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;


/**
 * 插件Activity
 * <p>
 * Created by ran on 2017/8/18.
 */

public class BaseActivity extends Activity {

    private static final String TAG = "Client-BaseActivity";

    public static final String FROM = "plugin.from";
    public static final int FROM_EXTERNAL = 0;
    public static final int FROM_INTERNAL = 1;

    public static final String PLUGIN_CLASS = "PLUGIN_CLASS";

    protected Activity mProxyActivity;
    protected int mFrom = FROM_INTERNAL;

    protected void setProxy(Activity proxyActivity) {
        mProxyActivity = proxyActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: from pre = " + mFrom);
        if (savedInstanceState != null) {
            mFrom = savedInstanceState.getInt(FROM, FROM_INTERNAL);
        }
        if (mFrom == FROM_INTERNAL) {
            super.onCreate(savedInstanceState);
            mProxyActivity = this;
        }
        if (mProxyActivity == null) mProxyActivity = this;
        Log.d(TAG, "onCreate: from= " + mFrom);
    }

    @Override
    public void startActivity(Intent intent) {
        if (mProxyActivity == this) {
            super.startActivity(intent);
        } else {
            ComponentName componentName = intent.getComponent();
            if (componentName != null) {
                String className = componentName.getClassName();
                intent.putExtra(PLUGIN_CLASS, className);
                intent.setClassName(mProxyActivity, className);
                mProxyActivity.startActivity(intent);
            }
        }
    }

    @Override
    public Resources getResources() {
        if (mProxyActivity == this || mProxyActivity == null) {
            return super.getResources();
        } else {
            return mProxyActivity.getResources();
        }
    }

    @Override
    public AssetManager getAssets() {
        if (mProxyActivity == this || mProxyActivity == null) {
            return super.getAssets();
        } else {
            return mProxyActivity.getAssets();
        }
    }

    @Override
    public Context getApplicationContext() {
        if (mProxyActivity == this || mProxyActivity == null) {
            return super.getApplicationContext();
        } else {
            return mProxyActivity.getApplicationContext();
        }
    }

    @Override
    public Window getWindow() {
        if (mProxyActivity == this || mProxyActivity == null) {
            return super.getWindow();
        } else {
            return mProxyActivity.getWindow();
        }
    }
}
