package org.looa.hellowordbee;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.looa.nest.plugin.PluginActivity;
import org.looa.tabview.widget.TabView;


/**
 * test.
 * <p>
 * Created by ran on 2017/8/18.
 */

public class TestActivity extends PluginActivity implements View.OnClickListener {

    private Button button;
    private View view;
    private TabView tabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button = (Button) findViewById(R.id.button_toast);
        button.setOnClickListener(this);

        tabView = (TabView) findViewById(R.id.tab);
//        tabView.setBackgroundColor(getResources().getColor(R.color.colorTest));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (view != null) {
            view.setBackgroundColor(Color.CYAN);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        String content;
        if (mProxyActivity == null) content = "Plugin is running independently!";
        else content = "Plugin is running inside Nest!";
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
        if (view == null) {
            view = LayoutInflater.from(getBaseContext()).inflate(R.layout.view_test, null);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addContentView(view, layoutParams);
        }
    }
}
