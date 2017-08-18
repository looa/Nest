package org.looa.hellowordbee;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * test.
 * <p>
 * Created by ran on 2017/8/18.
 */

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(mProxyActivity);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        button.setBackgroundColor(Color.WHITE);
        button.setText("这是测试页面");
        setContentView(button);
    }

    @Override
    protected void setProxy(Activity proxyActivity) {
        super.setProxy(proxyActivity);
    }
}
