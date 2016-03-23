package com.rafakob.drawme.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.SimpleArrayMap;

public class FontCache {
    private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap<>();

    public static Typeface get(Context c, String name) {
        synchronized (cache) {
            if (!cache.containsKey(name)) {
                try {
                    Typeface t = Typeface.createFromAsset(c.getAssets(), String.format("fonts/%s", name));
                    cache.put(name, t);
                    return t;
                } catch (RuntimeException e) {
                    return null;
                }
            }
            return cache.get(name);
        }
    }
}
