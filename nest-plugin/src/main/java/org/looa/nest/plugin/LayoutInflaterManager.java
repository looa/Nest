package org.looa.nest.plugin;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;

import java.util.HashMap;
import java.util.Map;

public class LayoutInflaterManager {

    private LayoutInflaterManager() {
    }

    private static LayoutInflaterManager sInstance = new LayoutInflaterManager();

    public static LayoutInflaterManager getInstance() {
        return sInstance;
    }

    Map<Resources, LayoutInflater> mLayoutInflaterCache = new HashMap<>();

    public LayoutInflater getLayoutInflater(Context context) {
        LayoutInflater layoutInflater = mLayoutInflaterCache.get(context.getResources());
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context).cloneInContext(context);
            LayoutInflaterFactory factory = new LayoutInflaterFactory();
            layoutInflater.setFactory2(factory);
            mLayoutInflaterCache.put(context.getResources(), layoutInflater);
        }
        return layoutInflater;
    }

    public void clear() {
        mLayoutInflaterCache.clear();
    }
}
