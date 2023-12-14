package com.xiaoyingbo.lib_util.BSY.util;

import android.view.View;

import androidx.annotation.IntRange;

import com.xiaoyingbo.lib_util.thirdparty.ClickUtils;

public class BSYClickUtils {
    /**
     * Apply single debouncing for the view's click.
     *
     * @param view     The view.
     * @param listener The listener.
     */
    public static void applySingleDebouncing(final View view, final View.OnClickListener listener) {
        ClickUtils.applySingleDebouncing(view,listener);
    }

    /**
     * Apply single debouncing for the views' click.
     *
     * @param views    The views.
     * @param listener The listener.
     */
    public static void applySingleDebouncing(final View[] views, final View.OnClickListener listener) {
        ClickUtils.applySingleDebouncing(views, listener);
    }

    /**
     * Apply single debouncing for the views' click.
     *
     * @param views    The views.
     * @param duration The duration of debouncing.
     * @param listener The listener.
     */
    public static void applySingleDebouncing(final View[] views,
                                             @IntRange(from = 0) long duration,
                                             final View.OnClickListener listener) {
        ClickUtils.applySingleDebouncing(views, duration,listener);
    }
}
