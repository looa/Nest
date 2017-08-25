package org.looa.nest;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.looa.nest.host.PluginInfo;
import org.looa.nest.host.PluginManager;
import org.looa.nest.host.ProxyActivity;
import org.looa.view.ViewShadow;

public class MainActivity extends Activity implements View.OnClickListener {

    private RelativeLayout pluginEnter;
    private TextView pluginName;
    private TextView pluginIntro;
    private ImageView pluginLogo;

    private String packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        pluginEnter = (RelativeLayout) findViewById(R.id.rl_plugin_enter);
        pluginName = (TextView) findViewById(R.id.tv_plugin_name);
        pluginIntro = (TextView) findViewById(R.id.tv_plugin_intro);
        pluginLogo = (ImageView) findViewById(R.id.iv_plugin_logo);

        ViewShadow.setElevation(pluginEnter, 30, Color.parseColor("#20000000"));
        pluginEnter.setOnClickListener(this);

        packageName = MyApplication.packageNames[0];

        PluginInfo pluginInfo = PluginManager.getInstance().getPluginInfo(packageName);
        String name = pluginInfo.getPluginAppName();
        String intro = pluginInfo.getPluginAppIntroduce();
        int icon = pluginInfo.getPluginAppIcon();
        if (name != null) pluginName.setText(name);
        if (intro != null) pluginIntro.setText(intro);
        if (icon > 0) {
            Resources resources = PluginManager.getInstance().getResources(pluginInfo.getPackageName());
            pluginLogo.setImageDrawable(resources.getDrawable(icon));
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra(PluginManager.KEY_PLUGIN_PACKAGE, packageName);
        startActivity(intent);
    }
}
