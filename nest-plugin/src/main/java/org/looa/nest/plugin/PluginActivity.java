package org.looa.nest.plugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * 插件Activity
 * <p>
 * Created by ran on 2017/8/18.
 */

public class PluginActivity extends FragmentActivity implements PluginService {

    public static final String PLUGIN_CLASS = "PLUGIN_CLASS";
    private String TAG;

    protected FragmentActivity mProxyActivity;

    @Override
    public void attach(FragmentActivity proxyActivity) {
        mProxyActivity = proxyActivity;
        attachBaseContext(mProxyActivity);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TAG = "PluginActivity<" + getClass().getName() + ">";
        if (mProxyActivity == null) {
            super.onCreate(savedInstanceState);
        } else {
            setIntent(mProxyActivity.getIntent());
        }
    }

    @Override
    public void outCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        if (mProxyActivity == null) {
            super.onStart();
        }
    }

    @Override
    public void outStart() {
        onStart();
    }

    @Override
    protected void onRestart() {
        if (mProxyActivity == null) {
            super.onRestart();
        }
    }

    @Override
    public void outRestart() {
        onRestart();
    }


    @Override
    protected void onResume() {
        if (mProxyActivity == null) {
            super.onResume();
        }
    }

    @Override
    public void outResume() {
        onResume();
    }

    @Override
    protected void onPause() {
        if (mProxyActivity == null) {
            super.onPause();
        }
    }

    @Override
    public void outPause() {
        onPause();
    }

    @Override
    protected void onStop() {
        if (mProxyActivity == null) {
            super.onStop();
        }
    }

    @Override
    public void outStop() {
        onStop();
    }

    @Override
    protected void onDestroy() {
        if (mProxyActivity == null) {
            super.onDestroy();
        }
    }

    @Override
    public void outDestroy() {
        onDestroy();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mProxyActivity == null) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void outActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mProxyActivity == null) {
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void outSaveInstanceState(Bundle outState) {
        onSaveInstanceState(outState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (mProxyActivity == null) {
            super.onNewIntent(intent);
        }
    }

    @Override
    public void outNewIntent(Intent intent) {
        onNewIntent(intent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (mProxyActivity == null) {
            super.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void outRestoreInstanceState(Bundle savedInstanceState) {
        onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mProxyActivity == null) {
            return super.onTouchEvent(event);
        } else {
            return false;
        }
    }

    @Override
    public boolean outTouchEvent(MotionEvent event) {
        return onTouchEvent(event);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        if (mProxyActivity == null) {
            super.onWindowAttributesChanged(params);
        }
    }

    @Override
    public void outWindowAttributesChanged(WindowManager.LayoutParams params) {
        onWindowAttributesChanged(params);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (mProxyActivity == null) {
            super.onWindowFocusChanged(hasFocus);
        }
    }

    @Override
    public void outWindowFocusChanged(boolean hasFocus) {
        onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onBackPressed() {
        if (mProxyActivity == null) {
            super.onBackPressed();
        }
    }

    @Override
    public void outBackPressed() {
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mProxyActivity == null) {
            return super.onCreateOptionsMenu(menu);
        } else {
            if (mProxyActivity.getParent() != null) {
                return mProxyActivity.getParent().onCreateOptionsMenu(menu);
            }
            return false;
        }
    }

    @Override
    public boolean outCreateOptionsMenu(Menu menu) {
        return onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mProxyActivity == null) {
            return super.onOptionsItemSelected(item);
        } else {
            if (mProxyActivity.getParent() != null) {
                return mProxyActivity.getParent().onOptionsItemSelected(item);
            }
            return false;
        }
    }

    @Override
    public boolean outOptionsItemSelected(MenuItem item) {
        return onOptionsItemSelected(item);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (mProxyActivity == null) {
            super.setContentView(layoutResID);
        } else {
            mProxyActivity.setContentView(layoutResID);
        }
    }

    @Override
    public void setContentView(View view) {
        if (mProxyActivity == null) {
            super.setContentView(view);
        } else {
            mProxyActivity.setContentView(view);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (mProxyActivity == null) {
            super.setContentView(view, params);
        } else {
            mProxyActivity.setContentView(view, params);
        }
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        if (mProxyActivity == null) {
            super.addContentView(view, params);
        } else {
            mProxyActivity.addContentView(view, params);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if (mProxyActivity == null) {
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
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        if (mProxyActivity == null) {
            super.overridePendingTransition(enterAnim, exitAnim);
        } else {
            mProxyActivity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        if (mProxyActivity == null) {
            return super.getClassLoader();
        } else {
            return mProxyActivity.getClassLoader();
        }
    }

    @Override
    public Resources getResources() {
        if (mProxyActivity == null) {
            return super.getResources();
        } else {
            return mProxyActivity.getResources();
        }
    }

    @Override
    public AssetManager getAssets() {
        if (mProxyActivity == null) {
            return super.getAssets();
        } else {
            return mProxyActivity.getAssets();
        }
    }

    @Override
    public Context getApplicationContext() {
        if (mProxyActivity == null) {
            return super.getApplicationContext();
        } else {
            return mProxyActivity.getApplicationContext();
        }
    }

    @Override
    public Window getWindow() {
        if (mProxyActivity == null) {
            return super.getWindow();
        } else {
            return mProxyActivity.getWindow();
        }
    }

    @Override
    public Resources.Theme getTheme() {
        if (mProxyActivity == null) {
            return super.getTheme();
        } else {
            return mProxyActivity.getTheme();
        }
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        if (mProxyActivity == null) {
            return super.getSupportFragmentManager();
        } else {
            return mProxyActivity.getSupportFragmentManager();
        }
    }


    @Override
    public MenuInflater getMenuInflater() {
        if (mProxyActivity == null) {
            return super.getMenuInflater();
        } else {
            return mProxyActivity.getMenuInflater();
        }
    }

    @Override
    public Object getSystemService(String name) {
        if (mProxyActivity == null) {
            return super.getSystemService(name);
        } else {
            return mProxyActivity.getSystemService(name);
        }
    }

    @Override
    public void finish() {
        if (mProxyActivity == null) {
            super.finish();
        } else {
            mProxyActivity.finish();
        }
    }

}
