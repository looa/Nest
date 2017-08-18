package org.looa.hellowordbee;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


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
        Log.e(TAG, "setProxy: proxyActivity= " + proxyActivity);
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
                mProxyActivity.startActivity(intent);
            }
        }
    }

    @Override
    public void setContentView(View view) {
        if (mProxyActivity == this) {
            super.setContentView(view);
        } else {
            mProxyActivity.setContentView(view);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (mProxyActivity == this) {
            super.setContentView(view, params);
        } else {
            mProxyActivity.setContentView(view, params);
        }
    }

    @Deprecated
    @Override
    public void setContentView(int layoutResID) {
        if (mProxyActivity == this) {
            super.setContentView(layoutResID);
        } else {
            mProxyActivity.setContentView(layoutResID);
        }

    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        if (mProxyActivity == this) {
            super.addContentView(view, params);
        } else {
            mProxyActivity.addContentView(view, params);
        }
    }
}
