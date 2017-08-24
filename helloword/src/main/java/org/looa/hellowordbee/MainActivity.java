package org.looa.hellowordbee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.looa.nest.plugin.PluginActivity;

public class MainActivity extends PluginActivity implements View.OnClickListener {

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
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), TestActivity.class);
        startActivity(intent);
    }
}
