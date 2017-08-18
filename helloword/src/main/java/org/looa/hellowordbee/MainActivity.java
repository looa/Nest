package org.looa.hellowordbee;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            Log.d("Bee's Activity >>>>>>>>", "onCreate: from= " + mFrom);
            initView(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView(Bundle savedInstanceState) {
        mProxyActivity.setContentView(generateContentView(mProxyActivity));
    }

    private View generateContentView(final Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        Button button = new Button(context);
        button.setText("button");
        layout.addView(button, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked button",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(context, TestActivity.class);
                startActivity(intent);
            }
        });
        return layout;
    }

    @Override
    protected void setProxy(Activity proxyActivity) {
        super.setProxy(proxyActivity);
    }
}
