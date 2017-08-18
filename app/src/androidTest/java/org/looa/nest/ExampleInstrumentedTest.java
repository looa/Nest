package org.looa.nest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("org.looa.nest", appContext.getPackageName());
    }

    @Test
    public void testPluginManger() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        String path = appContext.getFilesDir() + File.separator + "com.hello.test" + File.separator;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(path + "bee.apk");
        if (!file.exists()) {
            file.createNewFile();
        }
        Log.i("TEST", "testPluginManger: " + file.exists() + "  path = " + file.getAbsolutePath());
    }
}
