package org.looa.hellowordbee;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * test.
 * <p>
 * Created by ran on 2017/8/18.
 */

public class TestActivity extends BaseActivity implements View.OnClickListener {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button = (Button) findViewById(R.id.button_toast);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String content;
        if (mProxyActivity == this) content = "Plugin is running independently!";
        else content = "Plugin is running inside Nest!";
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void setProxy(Activity proxyActivity) {
        super.setProxy(proxyActivity);
    }
}
