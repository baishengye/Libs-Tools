package com.xiaoyingbo.lib_widget.normal.view.editText;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.xiaoyingbo.lib_util.BSY.util.BSYTextUtils;
import com.xiaoyingbo.lib_widget.R;

/**
 * desc   : 带清除按钮的 EditText
 */
public final class BSYClearEditText extends BSYRegexEditText
        implements View.OnTouchListener,
        View.OnFocusChangeListener, TextWatcher {

    private final Drawable mClearDrawable;

    @Nullable
    private View.OnTouchListener mTouchListener;
    @Nullable
    private View.OnFocusChangeListener mFocusChangeListener;

    public BSYClearEditText(Context context) {
        this(context, null);
    }

    public BSYClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    @SuppressWarnings("all")
    public BSYClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BSYClearEditText);
        mClearDrawable = array.getDrawable(R.styleable.BSYClearEditText_clearIcon);
        if(mClearDrawable == null){
            DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.input_delete_ic));
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        array.recycle();
        setDrawableVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        super.addTextChangedListener(this);
    }

    private void setDrawableVisible(boolean visible) {
        if (mClearDrawable.isVisible() == visible) {
            return;
        }

        mClearDrawable.setVisible(visible, false);
        Drawable[] drawables = getCompoundDrawablesRelative();
        setCompoundDrawablesRelative(
                drawables[0],
                drawables[1],
                visible ? mClearDrawable : null,
                drawables[3]);
    }

    @Override
    public void setOnFocusChangeListener(@Nullable View.OnFocusChangeListener onFocusChangeListener) {
        mFocusChangeListener = onFocusChangeListener;
    }

    @Override
    public void setOnTouchListener(@Nullable View.OnTouchListener onTouchListener) {
        mTouchListener = onTouchListener;
    }

    /**
     * {@link View.OnFocusChangeListener}
     */

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus && getText() != null) {
            setDrawableVisible(getText().length() > 0);
        } else {
            setDrawableVisible(false);
        }
        changeHintText(hasFocus, getText() != null ? getText().toString().trim() : "");
        if (mFocusChangeListener != null) {
            mFocusChangeListener.onFocusChange(view, hasFocus);
        }
    }

    /**
     * {@link View.OnTouchListener}
     */

    /**
     * 使用onTouch来判断是否点击了清除按钮
     */
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int x = (int) event.getX();

        // 是否触摸了 Drawable
        boolean touchDrawable = false;
        // 获取布局方向
        int layoutDirection = getLayoutDirection();
        if (layoutDirection == LAYOUT_DIRECTION_LTR) {
            // 从左往右
            touchDrawable = x > getWidth() - mClearDrawable.getIntrinsicWidth() - getPaddingEnd() &&
                    x < getWidth() - getPaddingEnd();
        } else if (layoutDirection == LAYOUT_DIRECTION_RTL) {
            // 从右往左
            touchDrawable = x > getPaddingStart() &&
                    x < getPaddingStart() + mClearDrawable.getIntrinsicWidth();
        }

        if (mClearDrawable.isVisible() && touchDrawable) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                setText("");
            }
            return true;
        }
        return mTouchListener != null && mTouchListener.onTouch(view, event);
    }

    /**
     * {@link TextWatcher}
     */

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isFocused()) {
            setDrawableVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        changeHintText(true, text);
    }
}
