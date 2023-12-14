package com.xiaoyingbo.lib_widget.normal.viewGroup;

import android.content.Context;
import android.content.res.Resources;
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
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.xiaoyingbo.lib_widget.R;
import com.xiaoyingbo.lib_widget.normal.view.button.BSYSwitchButton;

/**
 *    desc   : 设置条自定义控件
 */
public final class BSYSettingSwitchBar extends FrameLayout implements View.OnClickListener {

    /** 无色值 */
    public static final int NO_COLOR = Color.TRANSPARENT;

    private final LinearLayout mMainLayout;
    private final TextView mLeftView;
    private final BSYSwitchButton mRightView;
    private final View mLineView;

    /** 图标着色器 */
    private int mLeftDrawableTint;

    /** 图标显示大小 */
    private int mLeftDrawableSize;

    public BSYSettingSwitchBar(Context context) {
        this(context, null);
    }

    public BSYSettingSwitchBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BSYSettingSwitchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BSYSettingSwitchBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mMainLayout = new LinearLayout(getContext());
        mLeftView = new TextView(getContext());
        mRightView = new BSYSwitchButton(getContext());
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

        mLeftView.setSingleLine(true);

        mLeftView.setEllipsize(TextUtils.TruncateAt.END);

        mLeftView.setLineSpacing(getResources().getDimension(R.dimen.dp_5), mLeftView.getLineSpacingMultiplier());

        mLeftView.setPaddingRelative((int) getResources().getDimension(R.dimen.dp_15),
                (int) getResources().getDimension(R.dimen.dp_12),
                (int) getResources().getDimension(R.dimen.dp_15),
                (int) getResources().getDimension(R.dimen.dp_12));

        mRightView.setPaddingRelative((int) getResources().getDimension(R.dimen.dp_15),
                (int) getResources().getDimension(R.dimen.dp_12),
                (int) getResources().getDimension(R.dimen.dp_15),
                (int) getResources().getDimension(R.dimen.dp_12));

        final TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BSYSettingSwitchBar);

        // 文本设置
        if (array.hasValue(R.styleable.BSYSettingSwitchBar_settingSwitchBar_leftText)) {
            setLeftText(array.getString(R.styleable.BSYSettingSwitchBar_settingSwitchBar_leftText));
        }

        // 提示设置
        if (array.hasValue(R.styleable.BSYSettingSwitchBar_settingSwitchBar_leftTextHint)) {
            setLeftTextHint(array.getString(R.styleable.BSYSettingSwitchBar_settingSwitchBar_leftTextHint));
        }

        // 图标显示的大小
        if (array.hasValue(R.styleable.BSYSettingSwitchBar_settingSwitchBar_leftDrawableSize)) {
            setLeftDrawableSize(array.getDimensionPixelSize(R.styleable.BSYSettingSwitchBar_settingSwitchBar_leftDrawableSize, 0));
        }

        // 图标着色器
        if (array.hasValue(R.styleable.BSYSettingSwitchBar_settingSwitchBar_leftDrawableTint)) {
            setLeftDraawbleTint(array.getColor(R.styleable.BSYSettingSwitchBar_settingSwitchBar_leftDrawableTint, NO_COLOR));
        }

        // 图标和文字之间的间距
        setLeftDrawablePadding(array.hasValue(R.styleable.BSYSettingSwitchBar_settingSwitchBar_leftDrawablePadding) ?
                array.getDimensionPixelSize(R.styleable.BSYSettingSwitchBar_settingSwitchBar_leftDrawablePadding, 0) :
                (int) getResources().getDimension(R.dimen.dp_10));

        // 图标设置
        if (array.hasValue(R.styleable.BSYSettingSwitchBar_settingSwitchBar_leftDrawable)) {
            setLeftDrawable(array.getDrawable(R.styleable.BSYSettingSwitchBar_settingSwitchBar_leftDrawable));
        }

        // 分割线设置
        if (array.hasValue(R.styleable.BSYSettingSwitchBar_settingSwitchBar_lineDrawable)) {
            setLineDrawable(array.getDrawable(R.styleable.BSYSettingSwitchBar_settingSwitchBar_lineDrawable));
        } else {
            setLineDrawable(new ColorDrawable(0xFFECECEC));
        }

        if (array.hasValue(R.styleable.BSYSettingSwitchBar_settingSwitchBar_lineVisible)) {
            setLineVisible(array.getBoolean(R.styleable.BSYSettingSwitchBar_settingSwitchBar_lineVisible, true));
        }

        if (array.hasValue(R.styleable.BSYSettingSwitchBar_settingSwitchBar_lineSize)) {
            setLineSize(array.getDimensionPixelSize(R.styleable.BSYSettingSwitchBar_settingSwitchBar_lineSize, 0));
        }

        if (array.hasValue(R.styleable.BSYSettingSwitchBar_settingSwitchBar_lineMargin)) {
            setLineMargin(array.getDimensionPixelSize(R.styleable.BSYSettingSwitchBar_settingSwitchBar_lineMargin, 0));
        }


        // 文字颜色设置
        setLeftTextColor(array.getColor(R.styleable.BSYSettingSwitchBar_settingSwitchBar_leftTextColor, ContextCompat.getColor(getContext(), R.color.black80)));
        // 文字大小设置
        setLeftTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.BSYSettingSwitchBar_settingSwitchBar_leftTextSize, (int) getResources().getDimension(R.dimen.sp_15)));

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

        setSwitchShadowEffect(optBoolean(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_shadow_effect, true));

        setSwitchUncheckCircleColor(optColor(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_uncheckcircle_color, 0XffAAAAAA));//0XffAAAAAA;

        setSwitchUncheckCircleWidth(optPixelSize(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_uncheckcircle_width, dp2pxInt(1.5f)));//dp2pxInt(1.5f);

        setSwitchUncheckCircleRadius(optPixelSize(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_uncheckcircle_radius, dp2px(4)));//dp2px(4);

        setSwitchShadowRadius(optPixelSize(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_shadow_radius, dp2pxInt(2.5f)));//dp2pxInt(2.5f);

        setSwitchShadowOffset(optPixelSize(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_shadow_offset, dp2pxInt(1.5f)));//dp2pxInt(1.5f);

        setSwitchShadowColor(optColor(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_shadow_color, 0X33000000));//0X33000000;

        setSwitchUncheckColor(optColor(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_uncheck_color, 0XffDDDDDD));//0XffDDDDDD;

        setSwitchCheckedColor(optColor(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_checked_color, 0Xff51d367));//0Xff51d367;

        setSwitchBorderWidth(optPixelSize(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_border_width, dp2pxInt(1)));//dp2pxInt(1);
        setSwitchCheckLineColor(optColor(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_checkline_color, Color.WHITE));//Color.WHITE;

        setSwitchCheckLineWidth(optPixelSize(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_checkline_width, dp2pxInt(1f)));//dp2pxInt(1.0f);

        setSwitchUncheckButtonColor(optColor(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_uncheckbutton_color, Color.WHITE));

        setSwitchButtonRadiusScale(optFloat(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_buttonRadiusScale, 1.0F));

        setSwitchCheckedButtonColor(optColor(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_checkedbutton_color, Color.WHITE));

        setSwitchEffectDuration(optInt(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_effect_duration, 300));//300;

        setSwitchChecked(optBoolean(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_checked, false));

        setSwitchShowIndicator(optBoolean(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_show_indicator, true));

        setSwitchBackground(optColor(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_background, Color.WHITE));//Color.WHITE;

        setSwitchEnableEffect(optBoolean(array, R.styleable.BSYSettingSwitchBar_settingSwitchBar_enable_effect, true));

        array.recycle();

        mMainLayout.addView(mLeftView);
        mMainLayout.addView(mRightView);

        addView(mMainLayout, 0);
        addView(mLineView, 1);

        setOnClickListener(this);
    }

    public void setOnCheckedChangeListener(@NonNull BSYSwitchButton.OnCheckedChangeListener listener) {
        mRightView.setOnCheckedChangeListener(listener);
    }

    private BSYSettingSwitchBar setSwitchEnableEffect(boolean optBoolean) {
        mRightView.setEnableEffect(optBoolean);
        return this;
    }

    private BSYSettingSwitchBar setSwitchBackground(int optColor) {
        mRightView.setBackground(optColor);
        return this;
    }

    private BSYSettingSwitchBar setSwitchShowIndicator(boolean optBoolean) {
        mRightView.setShowIndicator(optBoolean);
        return this;
    }

    private BSYSettingSwitchBar setSwitchChecked(boolean optBoolean) {
        mRightView.setChecked(optBoolean);
        return this;
    }

    private BSYSettingSwitchBar setSwitchEffectDuration(int optInt) {
        mRightView.setEffectDuration(optInt);
        return this;
    }

    private BSYSettingSwitchBar setSwitchCheckedButtonColor(int optColor) {
        mRightView.setCheckedButtonColor(optColor);
        return this;
    }

    private BSYSettingSwitchBar setSwitchButtonRadiusScale(float optFloat) {
        mRightView.setButtonRadiusScale(optFloat);
        return this;
    }

    private BSYSettingSwitchBar setSwitchUncheckButtonColor(int optColor) {
        mRightView.setUncheckButtonColor(optColor);
        return this;
    }

    private BSYSettingSwitchBar setSwitchCheckLineWidth(int optPixelSize) {
        mRightView.setCheckLineWidth(optPixelSize);
        return this;
    }

    private BSYSettingSwitchBar setSwitchCheckLineColor(int optColor) {
        mRightView.setCheckLineColor(optColor);
        return this;
    }

    private BSYSettingSwitchBar setSwitchBorderWidth(int optPixelSize) {
        mRightView.setBorderWidth(optPixelSize);
        return this;
    }

    private BSYSettingSwitchBar setSwitchCheckedColor(int optColor) {
        mRightView.setCheckedColor(optColor);
        return this;
    }

    private BSYSettingSwitchBar setSwitchUncheckColor(int optColor) {
        mRightView.setUncheckColor(optColor);
        return this;
    }

    private BSYSettingSwitchBar setSwitchShadowColor(int optColor) {
        mRightView.setShadowColor(optColor);
        return this;
    }

    private BSYSettingSwitchBar setSwitchShadowOffset(int optPixelSize) {
        mRightView.setShadowOffset(optPixelSize);
        return this;
    }

    private BSYSettingSwitchBar setSwitchShadowRadius(int optPixelSize) {
        mRightView.setShadowRadius(optPixelSize);
        return this;
    }

    private BSYSettingSwitchBar setSwitchUncheckCircleRadius(float optPixelSize) {
        mRightView.setUncheckCircleRadius(optPixelSize);
        return this;
    }

    private BSYSettingSwitchBar setSwitchUncheckCircleWidth(int optPixelSize) {
        mRightView.setUncheckCircleWidth(optPixelSize);
        return this;
    }

    private BSYSettingSwitchBar setSwitchUncheckCircleColor(int optColor) {
        mRightView.setUncheckCircleColor(optColor);
        return this;
    }

    private BSYSettingSwitchBar setSwitchShadowEffect(boolean optBoolean) {
        mRightView.setShadowEffect(optBoolean);
        return this;
    }

    /**
     * 设置左边的文本
     */
    public BSYSettingSwitchBar setLeftText(@StringRes int id) {
        return setLeftText(getResources().getString(id));
    }

    public BSYSettingSwitchBar setLeftText(CharSequence text) {
        mLeftView.setText(text);
        return this;
    }

    public CharSequence getLeftText() {
        return mLeftView.getText();
    }

    /**
     * 设置左边的提示
     */
    public BSYSettingSwitchBar setLeftTextHint(@StringRes int id) {
        return setLeftTextHint(getResources().getString(id));
    }

    public BSYSettingSwitchBar setLeftTextHint(CharSequence hint) {
        mLeftView.setHint(hint);
        return this;
    }

    /**
     * 设置左边的图标
     */
    public BSYSettingSwitchBar setLeftDrawable(@DrawableRes int id) {
        setLeftDrawable(ContextCompat.getDrawable(getContext(), id));
        return this;
    }

    public BSYSettingSwitchBar setLeftDrawable(Drawable drawable) {
        mLeftView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        setLeftDrawableSize(mLeftDrawableSize);
        setLeftDraawbleTint(mLeftDrawableTint);
        return this;
    }

    public Drawable getLeftDrawable() {
        return mLeftView.getCompoundDrawables()[0];
    }

    /**
     * 设置左边的图标间距
     */
    public BSYSettingSwitchBar setLeftDrawablePadding(int padding) {
        mLeftView.setCompoundDrawablePadding(padding);
        return this;
    }

    /**
     * 设置左边的图标大小
     */
    public BSYSettingSwitchBar setLeftDrawableSize(int size) {
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
     * 设置左边的图标着色器
     */
    public BSYSettingSwitchBar setLeftDraawbleTint(int color) {
        mLeftDrawableTint = color;
        Drawable drawable = getLeftDrawable();
        if (drawable != null && color != NO_COLOR) {
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        return this;
    }

    /**
     * 设置左边的文本颜色
     */
    public BSYSettingSwitchBar setLeftTextColor(@ColorInt int color) {
        mLeftView.setTextColor(color);
        return this;
    }

    /**
     * 设置左边的文字大小
     */
    public BSYSettingSwitchBar setLeftTextSize(int unit, float size) {
        mLeftView.setTextSize(unit, size);
        return this;
    }

    /**
     * 设置分割线是否显示
     */
    public BSYSettingSwitchBar setLineVisible(boolean visible) {
        mLineView.setVisibility(visible ? VISIBLE : GONE);
        return this;
    }

    /**
     * 设置分割线的颜色
     */
    public BSYSettingSwitchBar setLineColor(@ColorInt int color) {
        return setLineDrawable(new ColorDrawable(color));
    }
    public BSYSettingSwitchBar setLineDrawable(Drawable drawable) {
        mLineView.setBackground(drawable);
        return this;
    }

    /**
     * 设置分割线的大小
     */
    public BSYSettingSwitchBar setLineSize(int size) {
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
    public BSYSettingSwitchBar setLineMargin(int margin) {
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
    public BSYSwitchButton getRightView() {
        return mRightView;
    }

    /**
     * 获取分割线
     */
    public View getLineView() {
        return mLineView;
    }

    private static float dp2px(float dp){
        Resources r = Resources.getSystem();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    private static int dp2pxInt(float dp){
        return (int) dp2px(dp);
    }

    private static int optInt(TypedArray typedArray,
                              int index,
                              int def) {
        if(typedArray == null){return def;}
        return typedArray.getInt(index, def);
    }


    private static float optPixelSize(TypedArray typedArray,
                                      int index,
                                      float def) {
        if(typedArray == null){return def;}
        return typedArray.getDimension(index, def);
    }

    private static int optPixelSize(TypedArray typedArray,
                                    int index,
                                    int def) {
        if(typedArray == null){return def;}
        return typedArray.getDimensionPixelOffset(index, def);
    }

    private static int optColor(TypedArray typedArray,
                                int index,
                                int def) {
        if(typedArray == null){return def;}
        return typedArray.getColor(index, def);
    }

    @FloatRange(from = 0.0, to = 1.0)
    private static float optFloat(TypedArray typedArray,
                                  int index,
                                  @FloatRange(from = 0.0, to = 1.0) float def){
        if(typedArray == null){return def;}
        return typedArray.getFloat(index, def);
    }

    private static boolean optBoolean(TypedArray typedArray,
                                      int index,
                                      boolean def) {
        if(typedArray == null){return def;}
        return typedArray.getBoolean(index, def);
    }

    @Override
    public void onClick(View v) {
        mRightView.setChecked(!mRightView.isChecked());
    }
}