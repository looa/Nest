package org.looa.nest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;

import org.looa.nest.plugin.PluginService;

import java.io.File;
import java.lang.reflect.Constructor;

import dalvik.system.DexClassLoader;

/**
 * 代理Activity（Host Activity）
 * <p>
 * Created by ran on 2017/8/18.
 */

public class ProxyActivity extends Activity {

    private String packageName;
    private PluginService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainProxyData();
    }

    private void obtainProxyData() {
        Intent intent = getIntent();
        String pluginPackage = intent.getStringExtra(PluginManager.KEY_PLUGIN_PACKAGE);

        PluginInfo pluginInfo = PluginManager.getInstance().getPluginInfo(this, pluginPackage);
        packageName = pluginInfo.getPackageName();

        String pluginDexPath = pluginInfo.getPluginPath();
        String pluginClass = intent.getStringExtra(PluginManager.KEY_PLUGIN_CLASS);
        launchTargetActivity(pluginDexPath, pluginClass);
    }

    private void launchTargetActivity(String pluginDexPath, String pluginClass) {
        if (pluginDexPath == null) {
            finish();
            return;
        } else if (pluginClass == null) {
            PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(pluginDexPath, PackageManager.GET_ACTIVITIES);
            if (packageInfo.activities != null && packageInfo.activities.length > 0) {
                pluginClass = packageInfo.activities[0].name;
            }
        }
        if (pluginClass == null) {
            finish();
            return;
        }
        File dexOutputDir = this.getDir("dex", MODE_PRIVATE);
        final String dexOutputPath = dexOutputDir.getAbsolutePath();
        ClassLoader localClassLoader = getClassLoader();
        DexClassLoader dexClassLoader = new DexClassLoader(pluginDexPath, dexOutputPath, null, localClassLoader);
        try {
            Class<?> localClass = dexClassLoader.loadClass(pluginClass);
            Constructor<?> localConstructor = localClass.getConstructor();
            Object instance = localConstructor.newInstance();
            service = (PluginService) instance;
            service.attach(this);
            Bundle bundle = new Bundle();
            service.outCreate(bundle);
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
        intent.setClass(this, ProxyActivity.class);
        intent.putExtra(PluginManager.KEY_PLUGIN_PACKAGE, MyApplication.packageNames[0]);
        super.startActivity(intent);
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
}
