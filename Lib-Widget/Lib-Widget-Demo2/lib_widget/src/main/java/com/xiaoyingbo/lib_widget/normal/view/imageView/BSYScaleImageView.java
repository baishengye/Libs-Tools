package com.xiaoyingbo.lib_widget.normal.view.imageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.xiaoyingbo.lib_widget.R;

/**
 *    desc   : 长按缩放松手恢复的 ImageView
 */
public final class BSYScaleImageView extends AppCompatImageView {

    private float mScaleSize = 1.2f;

    public BSYScaleImageView(Context context) {
        this(context, null);
    }

    public BSYScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BSYScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BSYScaleImageView);
        setScaleSize(array.getFloat(R.styleable.BSYScaleImageView_scaleRatio, mScaleSize));
        array.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        setClickable(true);
        setFocusable(true);
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        // 判断当前手指是否按下了
        if (pressed) {
            setScaleX(mScaleSize);
            setScaleY(mScaleSize);
        } else {
            setScaleX(1);
            setScaleY(1);
        }
    }

    public void setScaleSize(float size) {
        mScaleSize = size;
    }
}