package com.xiaoyingbo.lib_widget.normal.viewGroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.xiaoyingbo.lib_widget.R;

/**
 *    desc   : 设置条自定义控件
 */
public final class BSYSettingTextBar extends FrameLayout {

    /** 无色值 */
    public static final int NO_COLOR = Color.TRANSPARENT;

    private final LinearLayout mMainLayout;
    private final TextView mLeftView;
    private final TextView mRightView;
    private final View mLineView;

    /** 图标着色器 */
    private int mLeftDrawableTint, mRightDrawableTint;

    /** 图标显示大小 */
    private int mLeftDrawableSize, mRightDrawableSize;

    public BSYSettingTextBar(Context context) {
        this(context, null);
    }

    public BSYSettingTextBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BSYSettingTextBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BSYSettingTextBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mMainLayout = new LinearLayout(getContext());
        mLeftView = new TextView(getContext());
        mRightView = new TextView(getContext());
        mLineView  = new View(getContext());

        mMainLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL));

        LinearLayout.LayoutParams leftParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
        leftParams.gravity = Gravity.CENTER_VERTICAL;
        leftParams.weight = 1;
        mLeftView.setLayoutParams(leftParams);

        LinearLayout.LayoutParams rightParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightParams.gravity = Gravity.CENTER_VERTICAL;
        mRightView.setLayoutParams(rightParams);

        mLineView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1, Gravity.BOTTOM));

        mLeftView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        mRightView.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);

        mLeftView.setSingleLine(true);
        mRightView.setSingleLine(true);

        mLeftView.setEllipsize(TextUtils.TruncateAt.END);
        mRightView.setEllipsize(TextUtils.TruncateAt.END);

        mLeftView.setLineSpacing(getResources().getDimension(R.dimen.dp_5), mLeftView.getLineSpacingMultiplier());
        mRightView.setLineSpacing(getResources().getDimension(R.dimen.dp_5), mRightView.getLineSpacingMultiplier());

        mLeftView.setPaddingRelative((int) getResources().getDimension(R.dimen.dp_15),
                (int) getResources().getDimension(R.dimen.dp_12),
                (int) getResources().getDimension(R.dimen.dp_15),
                (int) getResources().getDimension(R.dimen.dp_12));
        mRightView.setPaddingRelative((int) getResources().getDimension(R.dimen.dp_15),
                (int) getResources().getDimension(R.dimen.dp_12),
                (int) getResources().getDimension(R.dimen.dp_15),
                (int) getResources().getDimension(R.dimen.dp_12));

        final TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BSYSettingTextBar);

        // 文本设置
        if (array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_leftText)) {
            setLeftText(array.getString(R.styleable.BSYSettingTextBar_settingTextBar_leftText));
        }

        if (array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_rightText)) {
            setRightText(array.getString(R.styleable.BSYSettingTextBar_settingTextBar_rightText));
        }

        // 提示设置
        if (array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_leftTextHint)) {
            setLeftTextHint(array.getString(R.styleable.BSYSettingTextBar_settingTextBar_leftTextHint));
        }

        if (array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_rightTextHint)) {
            setRightTextHint(array.getString(R.styleable.BSYSettingTextBar_settingTextBar_rightTextHint));
        }

        // 图标显示的大小
        if (array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_leftDrawableSize)) {
            setLeftDrawableSize(array.getDimensionPixelSize(R.styleable.BSYSettingTextBar_settingTextBar_leftDrawableSize, 0));
        }

        if (array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_rightDrawableSize)) {
            setRightDrawableSize(array.getDimensionPixelSize(R.styleable.BSYSettingTextBar_settingTextBar_rightDrawableSize, 0));
        }

        // 图标着色器
        if (array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_leftDrawableTint)) {
            setLeftDraawbleTint(array.getColor(R.styleable.BSYSettingTextBar_settingTextBar_leftDrawableTint, NO_COLOR));
        }

        if (array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_rightDrawableTint)) {
            setRightDrawableTint(array.getColor(R.styleable.BSYSettingTextBar_settingTextBar_rightDrawableTint, NO_COLOR));
        }

        // 图标和文字之间的间距
        setLeftDrawablePadding(array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_leftDrawablePadding) ?
                array.getDimensionPixelSize(R.styleable.BSYSettingTextBar_settingTextBar_leftDrawablePadding, 0) :
                (int) getResources().getDimension(R.dimen.dp_10));
        setRightDrawablePadding(array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_rightDrawablePadding) ?
                array.getDimensionPixelSize(R.styleable.BSYSettingTextBar_settingTextBar_rightDrawablePadding, 0) :
                (int) getResources().getDimension(R.dimen.dp_10));

        // 图标设置
        if (array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_leftDrawable)) {
            setLeftDrawable(array.getDrawable(R.styleable.BSYSettingTextBar_settingTextBar_leftDrawable));
        }

        if (array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_rightDrawable)) {
            setRightDrawable(array.getDrawable(R.styleable.BSYSettingTextBar_settingTextBar_rightDrawable));
        }

        // 文字颜色设置
        setLeftTextColor(array.getColor(R.styleable.BSYSettingTextBar_settingTextBar_leftTextColor, ContextCompat.getColor(getContext(), R.color.black80)));
        setRightTextColor(array.getColor(R.styleable.BSYSettingTextBar_settingTextBar_rightTextColor, ContextCompat.getColor(getContext(), R.color.black60)));

        // 文字大小设置
        setLeftTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.BSYSettingTextBar_settingTextBar_leftTextSize, (int) getResources().getDimension(R.dimen.sp_15)));
        setRightTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.BSYSettingTextBar_settingTextBar_rightTextSize, (int) getResources().getDimension(R.dimen.sp_14)));

        // 分割线设置
        if (array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_lineDrawable)) {
            setLineDrawable(array.getDrawable(R.styleable.BSYSettingTextBar_settingTextBar_lineDrawable));
        } else {
            setLineDrawable(new ColorDrawable(0xFFECECEC));
        }

        if (array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_lineVisible)) {
            setLineVisible(array.getBoolean(R.styleable.BSYSettingTextBar_settingTextBar_lineVisible, true));
        }

        if (array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_lineSize)) {
            setLineSize(array.getDimensionPixelSize(R.styleable.BSYSettingTextBar_settingTextBar_lineSize, 0));
        }

        if (array.hasValue(R.styleable.BSYSettingTextBar_settingTextBar_lineMargin)) {
            setLineMargin(array.getDimensionPixelSize(R.styleable.BSYSettingTextBar_settingTextBar_lineMargin, 0));
        }

        if (getBackground() == null) {
            StateListDrawable drawable = new StateListDrawable();
            drawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(ContextCompat.getColor(getContext(), R.color.black5)));
            drawable.addState(new int[]{android.R.attr.state_selected}, new ColorDrawable(ContextCompat.getColor(getContext(), R.color.black5)));
            drawable.addState(new int[]{android.R.attr.state_focused}, new ColorDrawable(ContextCompat.getColor(getContext(), R.color.black5)));
            drawable.addState(new int[]{}, new ColorDrawable(ContextCompat.getColor(getContext(), R.color.white)));
            setBackground(drawable);

            // 必须要设置可点击，否则点击屏幕任何角落都会触发按压事件
            setFocusable(true);
            setClickable(true);
        }

        array.recycle();

        mMainLayout.addView(mLeftView);
        mMainLayout.addView(mRightView);

        addView(mMainLayout, 0);
        addView(mLineView, 1);
    }

    /**
     * 设置左边的文本
     */
    public BSYSettingTextBar setLeftText(@StringRes int id) {
        return setLeftText(getResources().getString(id));
    }

    public BSYSettingTextBar setLeftText(CharSequence text) {
        mLeftView.setText(text);
        return this;
    }

    public CharSequence getLeftText() {
        return mLeftView.getText();
    }

    /**
     * 设置左边的提示
     */
    public BSYSettingTextBar setLeftTextHint(@StringRes int id) {
        return setLeftTextHint(getResources().getString(id));
    }

    public BSYSettingTextBar setLeftTextHint(CharSequence hint) {
        mLeftView.setHint(hint);
        return this;
    }

    /**
     * 设置右边的标题
     */
    public BSYSettingTextBar setRightText(@StringRes int id) {
        setRightText(getResources().getString(id));
        return this;
    }

    public BSYSettingTextBar setRightText(CharSequence text) {
        mRightView.setText(text);
        return this;
    }

    public CharSequence getRightText() {
        return mRightView.getText();
    }

    /**
     * 设置右边的提示
     */
    public BSYSettingTextBar setRightTextHint(@StringRes int id) {
        return setRightTextHint(getResources().getString(id));
    }

    public BSYSettingTextBar setRightTextHint(CharSequence hint) {
        mRightView.setHint(hint);
        return this;
    }

    /**
     * 设置左边的图标
     */
    public BSYSettingTextBar setLeftDrawable(@DrawableRes int id) {
        setLeftDrawable(ContextCompat.getDrawable(getContext(), id));
        return this;
    }

    public BSYSettingTextBar setLeftDrawable(Drawable drawable) {
        mLeftView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        setLeftDrawableSize(mLeftDrawableSize);
        setLeftDraawbleTint(mLeftDrawableTint);
        return this;
    }

    public Drawable getLeftDrawable() {
        return mLeftView.getCompoundDrawables()[0];
    }

    /**
     * 设置右边的图标
     */
    public BSYSettingTextBar setRightDrawable(@DrawableRes int id) {
        setRightDrawable(ContextCompat.getDrawable(getContext(), id));
        return this;
    }

    public BSYSettingTextBar setRightDrawable(Drawable drawable) {
        mRightView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        setRightDrawableSize(mRightDrawableSize);
        setRightDrawableTint(mRightDrawableTint);
        return this;
    }

    public Drawable getRightDrawable() {
        return mRightView.getCompoundDrawables()[2];
    }

    /**
     * 设置左边的图标间距
     */
    public BSYSettingTextBar setLeftDrawablePadding(int padding) {
        mLeftView.setCompoundDrawablePadding(padding);
        return this;
    }

    /**
     * 设置右边的图标间距
     */
    public BSYSettingTextBar setRightDrawablePadding(int padding) {
        mRightView.setCompoundDrawablePadding(padding);
        return this;
    }

    /**
     * 设置左边的图标大小
     */
    public BSYSettingTextBar setLeftDrawableSize(int size) {
        mLeftDrawableSize = size;
        Drawable drawable = getLeftDrawable();
        if (drawable != null) {
            if (size > 0) {
                drawable.setBounds(0 ,0, size, size);
            } else {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            }
            mLeftView.setCompoundDrawables(drawable, null, null, null);
        }
        return this;
    }

    /**
     * 设置右边的图标大小
     */
    public BSYSettingTextBar setRightDrawableSize(int size) {
        mRightDrawableSize = size;
        Drawable drawable = getRightDrawable();
        if (drawable != null) {
            if (size > 0) {
                drawable.setBounds(0 ,0, size, size);
            } else {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            }
            mRightView.setCompoundDrawables(null, null, drawable, null);
        }
        return this;
    }

    /**
     * 设置左边的图标着色器
     */
    public BSYSettingTextBar setLeftDraawbleTint(int color) {
        mLeftDrawableTint = color;
        Drawable drawable = getLeftDrawable();
        if (drawable != null && color != NO_COLOR) {
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        return this;
    }

    /**
     * 设置右边的图标着色器
     */
    public BSYSettingTextBar setRightDrawableTint(int color) {
        mRightDrawableTint = color;
        Drawable drawable = getRightDrawable();
        if (drawable != null && color != NO_COLOR) {
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        return this;
    }

    /**
     * 设置左边的文本颜色
     */
    public BSYSettingTextBar setLeftTextColor(@ColorInt int color) {
        mLeftView.setTextColor(color);
        return this;
    }

    /**
     * 设置右边的文本颜色
     */
    public BSYSettingTextBar setRightTextColor(@ColorInt int color) {
        mRightView.setTextColor(color);
        return this;
    }

    /**
     * 设置左边的文字大小
     */
    public BSYSettingTextBar setLeftTextSize(int unit, float size) {
        mLeftView.setTextSize(unit, size);
        return this;
    }

    /**
     * 设置右边的文字大小
     */
    public BSYSettingTextBar setRightTextSize(int unit, float size) {
        mRightView.setTextSize(unit, size);
        return this;
    }

    /**
     * 设置分割线是否显示
     */
    public BSYSettingTextBar setLineVisible(boolean visible) {
        mLineView.setVisibility(visible ? VISIBLE : GONE);
        return this;
    }

    /**
     * 设置分割线的颜色
     */
    public BSYSettingTextBar setLineColor(@ColorInt int color) {
        return setLineDrawable(new ColorDrawable(color));
    }
    public BSYSettingTextBar setLineDrawable(Drawable drawable) {
        mLineView.setBackground(drawable);
        return this;
    }

    /**
     * 设置分割线的大小
     */
    public BSYSettingTextBar setLineSize(int size) {
        LayoutParams params = (LayoutParams) mLineView.getLayoutParams();
        if (params == null) {
            params = generateDefaultLayoutParams();
        }
        params.height = size;
        mLineView.setLayoutParams(params);
        return this;
    }

    /**
     * 设置分割线边界
     */
    public BSYSettingTextBar setLineMargin(int margin) {
        LayoutParams params = (LayoutParams) mLineView.getLayoutParams();
        if (params == null) {
            params = generateDefaultLayoutParams();
        }
        params.leftMargin = margin;
        params.rightMargin = margin;
        mLineView.setLayoutParams(params);
        return this;
    }

    /**
     * 获取主布局
     */
    public LinearLayout getMainLayout() {
        return mMainLayout;
    }

    /**
     * 获取左 TextView
     */
    public TextView getLeftView() {
        return mLeftView;
    }

    /**
     * 获取右 TextView
     */
    public TextView getRightView() {
        return mRightView;
    }

    /**
     * 获取分割线
     */
    public View getLineView() {
        return mLineView;
    }
}