package org.looa.nest.plugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
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

public class PluginActivity extends FragmentActivity implements PluginActivityService {

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
        if (!isPlugin()) {
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
        if (!isPlugin()) {
            super.onStart();
        }
    }

    @Override
    public void outStart() {
        onStart();
    }

    @Override
    protected void onRestart() {
        if (!isPlugin()) {
            super.onRestart();
        }
    }

    @Override
    public void outRestart() {
        onRestart();
    }


    @Override
    protected void onResume() {
        if (!isPlugin()) {
            super.onResume();
        }
    }

    @Override
    public void outResume() {
        onResume();
    }

    @Override
    protected void onPause() {
        if (!isPlugin()) {
            super.onPause();
        }
    }

    @Override
    public void outPause() {
        onPause();
    }

    @Override
    protected void onStop() {
        if (!isPlugin()) {
            super.onStop();
        }
    }

    @Override
    public void outStop() {
        onStop();
    }

    @Override
    protected void onDestroy() {
        if (!isPlugin()) {
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
        if (!isPlugin()) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void outActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (!isPlugin()) {
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void outSaveInstanceState(Bundle outState) {
        onSaveInstanceState(outState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (!isPlugin()) {
            super.onNewIntent(intent);
        }
    }

    @Override
    public void outNewIntent(Intent intent) {
        onNewIntent(intent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (!isPlugin()) {
            super.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void outRestoreInstanceState(Bundle savedInstanceState) {
        onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isPlugin()) {
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
        if (!isPlugin()) {
            super.onWindowAttributesChanged(params);
        }
    }

    @Override
    public void outWindowAttributesChanged(WindowManager.LayoutParams params) {
        onWindowAttributesChanged(params);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!isPlugin()) {
            super.onWindowFocusChanged(hasFocus);
        }
    }

    @Override
    public void outWindowFocusChanged(boolean hasFocus) {
        onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onBackPressed() {
        if (!isPlugin()) {
            super.onBackPressed();
        }
    }

    @Override
    public void outBackPressed() {
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isPlugin()) {
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
        if (!isPlugin()) {
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
        if (!isPlugin()) {
            super.setContentView(layoutResID);
        } else {
            mProxyActivity.setContentView(layoutResID);
        }
    }

    @Override
    public void setContentView(View view) {
        if (!isPlugin()) {
            super.setContentView(view);
        } else {
            mProxyActivity.setContentView(view);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (!isPlugin()) {
            super.setContentView(view, params);
        } else {
            mProxyActivity.setContentView(view, params);
        }
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        if (!isPlugin()) {
            super.addContentView(view, params);
        } else {
            mProxyActivity.addContentView(view, params);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if (!isPlugin()) {
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
        if (!isPlugin()) {
            super.overridePendingTransition(enterAnim, exitAnim);
        } else {
            mProxyActivity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        if (!isPlugin()) {
            return super.getClassLoader();
        } else {
            return mProxyActivity.getClassLoader();
        }
    }

    @Override
    public Resources getResources() {
        if (!isPlugin()) {
            return super.getResources();
        } else {
            return mProxyActivity.getResources();
        }
    }

    @Override
    public AssetManager getAssets() {
        if (!isPlugin()) {
            return super.getAssets();
        } else {
            return mProxyActivity.getAssets();
        }
    }

    @Override
    public Context getApplicationContext() {
        if (!isPlugin()) {
            return super.getApplicationContext();
        } else {
            return mProxyActivity.getApplicationContext();
        }
    }

    @Override
    public Window getWindow() {
        if (!isPlugin()) {
            return super.getWindow();
        } else {
            return mProxyActivity.getWindow();
        }
    }

    @Override
    public Resources.Theme getTheme() {
        if (!isPlugin()) {
            return super.getTheme();
        } else {
            return mProxyActivity.getTheme();
        }
    }


    @Override
    public MenuInflater getMenuInflater() {
        if (!isPlugin()) {
            return super.getMenuInflater();
        } else {
            return mProxyActivity.getMenuInflater();
        }
    }

    @Override
    public Object getSystemService(String name) {
        if (!isPlugin()) {
            return super.getSystemService(name);
        } else {
            return mProxyActivity.getSystemService(name);
        }
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        if (!isPlugin()) {
            return super.getSupportFragmentManager();
        } else {
            return mProxyActivity.getSupportFragmentManager();
        }
    }

    @Override
    public LoaderManager getSupportLoaderManager() {
        if (!isPlugin()) {
            return super.getSupportLoaderManager();
        } else {
            return mProxyActivity.getSupportLoaderManager();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (!isPlugin()) {
            super.onAttachFragment(fragment);
        } else {
            mProxyActivity.onAttachFragment(fragment);
        }
    }

    @Override
    public void finish() {
        if (!isPlugin()) {
            super.finish();
        } else {
            mProxyActivity.finish();
        }
    }

    @Override
    public void onLowMemory() {
        if (!isPlugin()) {
            super.onLowMemory();
        } else {
            mProxyActivity.onLowMemory();
        }
    }

    public boolean isPlugin() {
        return mProxyActivity != null;
    }
}
