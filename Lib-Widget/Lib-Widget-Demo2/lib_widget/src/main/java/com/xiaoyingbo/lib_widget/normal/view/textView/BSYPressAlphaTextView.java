package com.xiaoyingbo.lib_widget.normal.view.textView;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 *    desc   : 长按半透明松手恢复的 TextView
 */
public final class BSYPressAlphaTextView extends AppCompatTextView {

    public BSYPressAlphaTextView(@NonNull Context context) {
        super(context);
    }

    public BSYPressAlphaTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BSYPressAlphaTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        setFocusable(true);
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        // 判断当前手指是否按下了
        if (pressed) {
            setAlpha(0.5f);
        } else {
            setAlpha(1f);
        }
    }
}