package org.looa.nest.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;

/**
 * plugin interface.
 * <p>
 * Created by ran out 2017/8/22.
 */

public interface PluginService {
    void outCreate(Bundle savedInstanceState);

    void outStart();

    void outRestart();

    void outActivityResult(int requestCode, int resultCode, Intent data);

    void outResume();

    void outPause();

    void outStop();

    void outDestroy();

    void attach(Activity proxyActivity);

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
