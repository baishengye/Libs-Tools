package com.xiaoyingbo.lib_widget.normal.view.titleBar.style;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.xiaoyingbo.lib_widget.R;
import com.xiaoyingbo.lib_widget.normal.view.titleBar.BSYSelectorDrawable;
import com.xiaoyingbo.lib_widget.normal.view.titleBar.BSYTitleBarSupport;

/**
 *    desc   : 透明主题样式实现（对应布局属性：app:barStyle="transparent"）
 */
public class TransparentBarStyle extends BSYCommonBarStyle {

    @Override
    public Drawable getBackButtonDrawable(Context context) {
        return BSYTitleBarSupport.getDrawable(context, R.drawable.bar_arrows_left_white);
    }

    @Override
    public Drawable getLeftTitleBackground(Context context) {
        return new BSYSelectorDrawable.Builder()
                .setDefault(new ColorDrawable(0x00000000))
                .setFocused(new ColorDrawable(0x22000000))
                .setPressed(new ColorDrawable(0x22000000))
                .build();
    }

    @Override
    public Drawable getRightTitleBackground(Context context) {
        return new BSYSelectorDrawable.Builder()
                .setDefault(new ColorDrawable(0x00000000))
                .setFocused(new ColorDrawable(0x22000000))
                .setPressed(new ColorDrawable(0x22000000))
                .build();
    }

    @Override
    public Drawable getTitleBarBackground(Context context) {
        return new ColorDrawable(0x00000000);
    }

    @Override
    public ColorStateList getTitleColor(Context context) {
        return ColorStateList.valueOf(0xFFFFFFFF);
    }

    @Override
    public ColorStateList getLeftTitleColor(Context context) {
        return ColorStateList.valueOf(0xFFFFFFFF);
    }

    @Override
    public ColorStateList getRightTitleColor(Context context) {
        return ColorStateList.valueOf(0xFFFFFFFF);
    }

    @Override
    public Drawable getLineDrawable(Context context) {
        return new ColorDrawable(0x00000000);
    }
}