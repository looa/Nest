package org.looa.nest.host;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;

import org.looa.nest.plugin.PluginActivityService;

import java.lang.reflect.Constructor;

import dalvik.system.DexClassLoader;

/**
 * 代理Activity（Host Activity）
 * <p>
 * Created by ran on 2017/8/18.
 */

public class ProxyActivity extends FragmentActivity {

    private String TAG;
    private String pluginPackage;
    private String packageName;
    private String pluginClass;
    private PluginActivityService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        obtainProxyData();
        resetOrientation();
        super.onCreate(savedInstanceState);
        launchTargetActivity(pluginClass, savedInstanceState);
    }

    private void obtainProxyData() {
        Intent intent = getIntent();
        pluginPackage = intent.getStringExtra(PluginManager.KEY_PLUGIN_PACKAGE);
        pluginClass = intent.getStringExtra(PluginManager.KEY_PLUGIN_CLASS);
        PluginInfo pluginInfo = PluginManager.getInstance().getPluginInfo(pluginPackage);
        packageName = pluginInfo.getPackageName();
        if (pluginClass == null) {
            pluginClass = PluginManager.getInstance().getPluginLauncherActivity(packageName);
        }
        TAG = ProxyActivity.this.getClass().getSimpleName() + "<" + pluginClass + ">";
    }

    private void resetOrientation() {
        ActivityInfo info = PluginManager.getInstance().getPluginActivity(packageName, pluginClass);
        int screenOrientation = info.screenOrientation;
        if (screenOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (screenOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private void launchTargetActivity(String pluginClass, @Nullable Bundle savedInstanceState) {
        Class<?> localClass;
        DexClassLoader dexClassLoader = PluginManager.getInstance().getDexClassLoader(this, packageName);
        try {
            localClass = dexClassLoader.loadClass(pluginClass);
            Constructor<?> localConstructor = localClass.getConstructor();
            Object instance = localConstructor.newInstance();
            service = (PluginActivityService) instance;
            service.attach(this);
            service.outCreate(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (service != null) service.outResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (service != null) service.outStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (service != null) service.outRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (service != null) service.outPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (service != null) service.outStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (service != null) service.outDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (service != null) service.outActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (service != null) service.outSaveInstanceState(outState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (service != null) service.outNewIntent(intent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (service != null) service.outRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (service != null) return service.outTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
        if (service != null) service.outWindowAttributesChanged(params);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (service != null) service.outWindowFocusChanged(hasFocus);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (service != null) service.outBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (service != null) return service.outCreateOptionsMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (service != null) service.outOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startActivity(Intent intent) {
        Class targetClass = ProxyActivity.class;
        intent.setClass(this, targetClass);
        intent.putExtra(PluginManager.KEY_PLUGIN_PACKAGE, pluginPackage);
        super.startActivity(intent);
    }

    @Override
    public ClassLoader getClassLoader() {
        if (packageName == null) {
            return super.getClassLoader();
        } else {
            return PluginManager.getInstance().getDexClassLoader(getBaseContext(), packageName);
        }
    }

    @Override
    public Resources getResources() {
        if (packageName == null) {
            return super.getResources();
        } else {
            return PluginManager.getInstance().getResources(packageName);
        }
    }

    @Override
    public AssetManager getAssets() {
        if (packageName == null) {
            return super.getAssets();
        } else {
            return PluginManager.getInstance().getAssets(packageName);
        }
    }

    @Override
    public Resources.Theme getTheme() {
        if (packageName == null) {
            return super.getTheme();
        } else {
            return PluginManager.getInstance().getTheme(packageName);
        }
    }
}
