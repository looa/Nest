package org.looa.nest.plugin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;

/**
 * plugin interface.
 * <p>
 * Created by ran out 2017/8/22.
 */

public interface PluginActivityService {
    void attach(FragmentActivity proxyActivity);

    void outCreate(Bundle savedInstanceState);

    void outStart();

    void outRestart();

    void outActivityResult(int requestCode, int resultCode, Intent data);

    void outResume();

    void outPause();

    void outStop();

    void outDestroy();

    void outSaveInstanceState(Bundle outState);

    void outNewIntent(Intent intent);

    void outRestoreInstanceState(Bundle savedInstanceState);

    boolean outTouchEvent(MotionEvent event);

    void outWindowAttributesChanged(WindowManager.LayoutParams params);

    void outWindowFocusChanged(boolean hasFocus);

    void outBackPressed();

    boolean outCreateOptionsMenu(Menu menu);

    boolean outOptionsItemSelected(MenuItem item);
}
