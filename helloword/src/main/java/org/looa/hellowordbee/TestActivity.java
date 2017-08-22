package org.looa.hellowordbee;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.looa.nest.plugin.PluginActivity;
import org.looa.nest.plugin.PluginService;

import java.lang.reflect.Constructor;

import static android.content.ContentValues.TAG;


/**
 * test.
 * <p>
 * Created by ran on 2017/8/18.
 */

public class TestActivity extends PluginActivity implements View.OnClickListener {

    private Button button;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button = (Button) findViewById(R.id.button_toast);
        button.setOnClickListener(this);
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
        if (mProxyActivity == this) content = "Plugin is running independently!";
        else content = "Plugin is running inside Nest!";
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
        if (view == null) {
            view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_test, null);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addContentView(view, layoutParams);
        }
        test();
    }

    private void test() {
        try {
            Class<?> localClass = Class.forName("org.looa.hellowordbee.MainActivity");
            Constructor<?> localConstructor = localClass.getConstructor();
            Object instance = localConstructor.newInstance();
            PluginService service = (PluginService) instance;
            Log.w(TAG, "test: service " + service.getClass().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
