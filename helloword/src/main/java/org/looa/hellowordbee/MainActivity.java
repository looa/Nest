package org.looa.hellowordbee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button_next);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("Plugin >>>", "onClick: running!!!");
        Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), TestActivity.class);
        startActivity(intent);
    }


    @Override
    protected void setProxy(Activity proxyActivity) {
        super.setProxy(proxyActivity);
    }
}
