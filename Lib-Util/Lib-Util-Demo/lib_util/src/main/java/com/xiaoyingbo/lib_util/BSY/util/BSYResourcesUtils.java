package com.xiaoyingbo.lib_util.BSY.util;


import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;

public class BSYResourcesUtils {
    public static int getColor(Context context, @ColorRes int colorId){
        return context.getResources().getColor(colorId, context.getTheme());
    }

    public static String getString(Context context, @StringRes int stringId){
        return context.getResources().getString(stringId);
    }

    public static String getString(Context context, @StringRes int stringId, Object... formatArgs){
        return context.getResources().getString(stringId,formatArgs);
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawableId){
        return ResourcesCompat.getDrawable(context.getResources(),drawableId, context.getTheme());
    }

    public static float getDimen(Context context, @DimenRes int dimenId){
        return context.getResources().getDimension(dimenId);
    }

    public static String[] getStringArray(Context context, @ArrayRes int arrayId){
        return context.getResources().getStringArray(arrayId);
    }
}
