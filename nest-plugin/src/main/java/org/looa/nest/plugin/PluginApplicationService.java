package org.looa.nest.plugin;

import android.content.Context;

/**
 * application service.
 * <p>
 * Created by ran on 2017/8/25.
 */

public interface PluginApplicationService {

    void attach(Context application);

    void outCreate();
}
