package com.xiaoyingbo.lib_widget.normal.view.titleBar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;

import com.xiaoyingbo.lib_widget.R;
import com.xiaoyingbo.lib_widget.normal.view.titleBar.style.BSYLightBarStyle;
import com.xiaoyingbo.lib_widget.normal.view.titleBar.style.BSYNightBarStyle;
import com.xiaoyingbo.lib_widget.normal.view.titleBar.style.BSYRippleBarStyle;
import com.xiaoyingbo.lib_widget.normal.view.titleBar.style.TransparentBarStyle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/TitleBar
 *    time   : 2018/08/17
 *    desc   : 标题栏框架
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class BSYTitleBar extends FrameLayout
        implements View.OnClickListener,
        View.OnLayoutChangeListener {

    private static final String LOG_TAG = "BSYTitleBar";

    /** 默认初始化器 */
    private static BSYITitleBarStyle sGlobalStyle;
    /** 当前初始化器 */
    private final BSYITitleBarStyle mCurrentStyle;

    /** 监听器对象 */
    private OnBSYTitleBarListener mListener;

    /** 标题栏子控件 */
    private final TextView mLeftView, mTitleView, mRightView;
    private final View mLineView;

    /** 控件水平间距 */
    private int mLeftHorizontalPadding, mTitleHorizontalPadding, mRightHorizontalPadding;

    /** 控件垂直间距 */
    private int mVerticalPadding;

    /** 图标显示大小 */
    private int mLeftIconWidth, mLeftIconHeight;
    private int mTitleIconWidth, mTitleIconHeight;
    private int mRightIconWidth, mRightIconHeight;

    /** 图标显示重心 */
    private int mLeftIconGravity, mTitleIconGravity, mRightIconGravity;

    /** 图标着色器 */
    private int mLeftIconTint, mTitleIconTint, mRightIconTint = BSYTitleBarSupport.NO_COLOR;

    public BSYTitleBar(Context context) {
        this(context, null);
    }

    public BSYTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BSYTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (sGlobalStyle == null) {
            sGlobalStyle = new BSYLightBarStyle();
        }

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BSYTitleBar, 0, R.style.BSYTitleBarStyle);

        // 标题栏样式设置
        switch (array.getInt(R.styleable.BSYTitleBar_barStyle, 0)) {
            case BarStyle.LIGHT:
                mCurrentStyle = new BSYLightBarStyle();
                break;
            case BarStyle.NIGHT:
                mCurrentStyle = new BSYNightBarStyle();
                break;
            case BarStyle.TRANSPARENT:
                mCurrentStyle = new TransparentBarStyle();
                break;
            case BarStyle.RIPPLE:
                mCurrentStyle = new BSYRippleBarStyle();
                break;
            default:
                mCurrentStyle = BSYTitleBar.sGlobalStyle;
                break;
        }

        mTitleView = mCurrentStyle.createTitleView(context);
        mLeftView = mCurrentStyle.createLeftView(context);
        mRightView = mCurrentStyle.createRightView(context);
        mLineView = mCurrentStyle.createLineView(context);

        mTitleView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER_HORIZONTAL));
        mLeftView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.START));
        mRightView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.END));
        mLineView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                mCurrentStyle.getLineSize(context), Gravity.BOTTOM));

        // 设置图标显示的重心
        setTitleIconGravity(array.getInt(R.styleable.BSYTitleBar_titleIconGravity, mCurrentStyle.getTitleIconGravity(context)));
        setLeftIconGravity(array.getInt(R.styleable.BSYTitleBar_leftIconGravity, mCurrentStyle.getLeftIconGravity(context)));
        setRightIconGravity(array.getInt(R.styleable.BSYTitleBar_rightIconGravity, mCurrentStyle.getRightIconGravity(context)));

        // 设置图标显示的大小
        setTitleIconSize(array.getDimensionPixelSize(R.styleable.BSYTitleBar_titleIconWidth, mCurrentStyle.getTitleIconWidth(context)),
                array.getDimensionPixelSize(R.styleable.BSYTitleBar_titleIconHeight, mCurrentStyle.getTitleIconHeight(context)));
        setLeftIconSize(array.getDimensionPixelSize(R.styleable.BSYTitleBar_leftIconWidth, mCurrentStyle.getLeftIconWidth(context)),
                array.getDimensionPixelSize(R.styleable.BSYTitleBar_leftIconHeight, mCurrentStyle.getLeftIconHeight(context)));
        setRightIconSize(array.getDimensionPixelSize(R.styleable.BSYTitleBar_rightIconWidth, mCurrentStyle.getRightIconWidth(context)),
                array.getDimensionPixelSize(R.styleable.BSYTitleBar_rightIconHeight, mCurrentStyle.getRightIconHeight(context)));

        // 设置文字和图标之间的间距
        setTitleIconPadding(array.getDimensionPixelSize(R.styleable.BSYTitleBar_titleIconPadding, mCurrentStyle.getTitleIconPadding(context)));
        setLeftIconPadding(array.getDimensionPixelSize(R.styleable.BSYTitleBar_leftIconPadding, mCurrentStyle.getLeftIconPadding(context)));
        setRightIconPadding(array.getDimensionPixelSize(R.styleable.BSYTitleBar_rightIconPadding, mCurrentStyle.getRightIconPadding(context)));

        // 标题设置
        if (array.hasValue(R.styleable.BSYTitleBar_title)) {
            setTitle(array.getResourceId(R.styleable.BSYTitleBar_title, 0) != R.string.bar_string_placeholder ?
                    array.getString(R.styleable.BSYTitleBar_title) : mCurrentStyle.getTitle(context));
        }

        if (array.hasValue(R.styleable.BSYTitleBar_leftTitle)) {
            setLeftTitle(array.getResourceId(R.styleable.BSYTitleBar_leftTitle, 0) != R.string.bar_string_placeholder ?
                    array.getString(R.styleable.BSYTitleBar_leftTitle) : mCurrentStyle.getLeftTitle(context));
        }

        if (array.hasValue(R.styleable.BSYTitleBar_rightTitle)) {
            setRightTitle(array.getResourceId(R.styleable.BSYTitleBar_rightTitle, 0) != R.string.bar_string_placeholder ?
                    array.getString(R.styleable.BSYTitleBar_rightTitle) : mCurrentStyle.getRightTitle(context));
        }

        // 图标着色设置
        if (array.hasValue(R.styleable.BSYTitleBar_titleIconTint)) {
            setTitleIconTint(array.getColor(R.styleable.BSYTitleBar_titleIconTint, 0));
        }

        if (array.hasValue(R.styleable.BSYTitleBar_leftIconTint)) {
            setLeftIconTint(array.getColor(R.styleable.BSYTitleBar_leftIconTint, 0));
        }

        if (array.hasValue(R.styleable.BSYTitleBar_rightIconTint)) {
            setRightIconTint(array.getColor(R.styleable.BSYTitleBar_rightIconTint, 0));
        }

        // 图标设置
        if (array.hasValue(R.styleable.BSYTitleBar_titleIcon)) {
            setTitleIcon(BSYTitleBarSupport.getDrawable(context, array.getResourceId(R.styleable.BSYTitleBar_titleIcon, 0)));
        }

        if (array.hasValue(R.styleable.BSYTitleBar_leftIcon)) {
            setLeftIcon(array.getResourceId(R.styleable.BSYTitleBar_leftIcon, 0) != R.drawable.bar_drawable_placeholder ?
                    BSYTitleBarSupport.getDrawable(context, array.getResourceId(R.styleable.BSYTitleBar_leftIcon, 0)) :
                    mCurrentStyle.getBackButtonDrawable(context));
        }

        if (array.hasValue(R.styleable.BSYTitleBar_rightIcon)) {
            setRightIcon(BSYTitleBarSupport.getDrawable(context, array.getResourceId(R.styleable.BSYTitleBar_rightIcon, 0)));
        }

        // 文字颜色设置
        setTitleColor(array.hasValue(R.styleable.BSYTitleBar_titleColor) ?
                array.getColorStateList(R.styleable.BSYTitleBar_titleColor) :
                mCurrentStyle.getTitleColor(context));
        setLeftTitleColor(array.hasValue(R.styleable.BSYTitleBar_leftTitleColor) ?
                array.getColorStateList(R.styleable.BSYTitleBar_leftTitleColor) :
                mCurrentStyle.getLeftTitleColor(context));
        setRightTitleColor(array.hasValue(R.styleable.BSYTitleBar_rightTitleColor) ?
                array.getColorStateList(R.styleable.BSYTitleBar_rightTitleColor) :
                mCurrentStyle.getRightTitleColor(context));

        // 文字大小设置
        setTitleSize(TypedValue.COMPLEX_UNIT_PX, array.hasValue(R.styleable.BSYTitleBar_titleSize) ?
                array.getDimensionPixelSize(R.styleable.BSYTitleBar_titleSize, 0) :
                mCurrentStyle.getTitleSize(context));
        setLeftTitleSize(TypedValue.COMPLEX_UNIT_PX, array.hasValue(R.styleable.BSYTitleBar_leftTitleSize) ?
                array.getDimensionPixelSize(R.styleable.BSYTitleBar_leftTitleSize, 0) :
                mCurrentStyle.getLeftTitleSize(context));
        setRightTitleSize(TypedValue.COMPLEX_UNIT_PX, array.hasValue(R.styleable.BSYTitleBar_rightTitleSize) ?
                array.getDimensionPixelSize(R.styleable.BSYTitleBar_rightTitleSize, 0) :
                mCurrentStyle.getRightTitleSize(context));

        // 文字样式设置
        int titleStyle = array.hasValue(R.styleable.BSYTitleBar_titleStyle) ?
                array.getInt(R.styleable.BSYTitleBar_titleStyle, Typeface.NORMAL) :
                mCurrentStyle.getTitleStyle(context);
        setTitleStyle(mCurrentStyle.getTitleTypeface(context, titleStyle), titleStyle);

        int leftTitleStyle = array.hasValue(R.styleable.BSYTitleBar_leftTitleStyle) ?
                array.getInt(R.styleable.BSYTitleBar_leftTitleStyle, Typeface.NORMAL) :
                mCurrentStyle.getLeftTitleStyle(context);
        setLeftTitleStyle(mCurrentStyle.getLeftTitleTypeface(context, leftTitleStyle), leftTitleStyle);

        int rightTitleStyle = array.hasValue(R.styleable.BSYTitleBar_rightTitleStyle) ?
                array.getInt(R.styleable.BSYTitleBar_rightTitleStyle, Typeface.NORMAL) :
                mCurrentStyle.getRightTitleStyle(context);
        setRightTitleStyle(mCurrentStyle.getRightTitleTypeface(context, rightTitleStyle), rightTitleStyle);

        // 标题重心设置
        if (array.hasValue(R.styleable.BSYTitleBar_titleGravity)) {
            setTitleGravity(array.getInt(R.styleable.BSYTitleBar_titleGravity, Gravity.NO_GRAVITY));
        }

        // 设置背景
        if (array.hasValue(R.styleable.BSYTitleBar_android_background)) {
            if (array.getResourceId(R.styleable.BSYTitleBar_android_background, 0) == R.drawable.bar_drawable_placeholder) {
                BSYTitleBarSupport.setBackground(this, mCurrentStyle.getTitleBarBackground(context));
            }
        }

        if (array.hasValue(R.styleable.BSYTitleBar_leftBackground)) {
            setLeftBackground(array.getResourceId(R.styleable.BSYTitleBar_leftBackground, 0) != R.drawable.bar_drawable_placeholder ?
                    array.getDrawable(R.styleable.BSYTitleBar_leftBackground) : mCurrentStyle.getLeftTitleBackground(context));
        }

        if (array.hasValue(R.styleable.BSYTitleBar_rightBackground)) {
            setRightBackground(array.getResourceId(R.styleable.BSYTitleBar_rightBackground, 0) != R.drawable.bar_drawable_placeholder ?
                    array.getDrawable(R.styleable.BSYTitleBar_rightBackground) : mCurrentStyle.getRightTitleBackground(context));
        }

        // 设置前景
        if (array.hasValue(R.styleable.BSYTitleBar_leftForeground)) {
            setLeftForeground(array.getResourceId(R.styleable.BSYTitleBar_leftForeground, 0) != R.drawable.bar_drawable_placeholder ?
                    array.getDrawable(R.styleable.BSYTitleBar_leftForeground) : mCurrentStyle.getLeftTitleForeground(context));
        }

        if (array.hasValue(R.styleable.BSYTitleBar_rightForeground)) {
            setRightForeground(array.getResourceId(R.styleable.BSYTitleBar_rightForeground, 0) != R.drawable.bar_drawable_placeholder ?
                    array.getDrawable(R.styleable.BSYTitleBar_rightForeground) : mCurrentStyle.getRightTitleForeground(context));
        }

        // 分割线设置
        setLineVisible(array.getBoolean(R.styleable.BSYTitleBar_lineVisible, mCurrentStyle.isLineVisible(context)));

        if (array.hasValue(R.styleable.BSYTitleBar_lineDrawable)) {
            setLineDrawable(array.getResourceId(R.styleable.BSYTitleBar_lineDrawable, 0) != R.drawable.bar_drawable_placeholder ?
                    array.getDrawable(R.styleable.BSYTitleBar_lineDrawable) : mCurrentStyle.getLineDrawable(context));
        }

        if (array.hasValue(R.styleable.BSYTitleBar_lineSize)) {
            setLineSize(array.getDimensionPixelSize(R.styleable.BSYTitleBar_lineSize, 0));
        }

        // 设置子控件的内间距
        mLeftHorizontalPadding = array.getDimensionPixelSize(R.styleable.BSYTitleBar_leftHorizontalPadding, mCurrentStyle.getLeftHorizontalPadding(context));
        mTitleHorizontalPadding = array.getDimensionPixelSize(R.styleable.BSYTitleBar_titleHorizontalPadding, mCurrentStyle.getTitleHorizontalPadding(context));
        mRightHorizontalPadding = array.getDimensionPixelSize(R.styleable.BSYTitleBar_rightHorizontalPadding, mCurrentStyle.getRightHorizontalPadding(context));
        setChildHorizontalPadding(mLeftHorizontalPadding, mTitleHorizontalPadding, mRightHorizontalPadding);

        mVerticalPadding = array.getDimensionPixelSize(R.styleable.BSYTitleBar_childVerticalPadding, mCurrentStyle.getChildVerticalPadding(context));
        setChildVerticalPadding(mVerticalPadding);

        // 回收 TypedArray 对象
        array.recycle();

        addView(mTitleView, 0);
        addView(mLeftView, 1);
        addView(mRightView, 2);
        addView(mLineView, 3);

        addOnLayoutChangeListener(this);

        // 如果当前是布局预览模式
        if (isInEditMode()) {
            measure(0, 0);
            mTitleView.measure(0, 0);
            mLeftView.measure(0, 0);
            mRightView.measure(0, 0);
            int horizontalMargin = Math.max(
                    mLeftView.getMeasuredWidth() + mLeftHorizontalPadding * 2,
                    mRightView.getMeasuredWidth() + mRightHorizontalPadding * 2);
            MarginLayoutParams layoutParams = (MarginLayoutParams) mTitleView.getLayoutParams();
            layoutParams.setMargins(horizontalMargin, 0, horizontalMargin, 0);
        }
    }

    /**
     * {@link OnLayoutChangeListener}
     */

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        // 先移除当前的监听，因为 TextView.setMaxWidth 方法会重新触发监听
        removeOnLayoutChangeListener(this);

        // 标题栏子 View 最大宽度限制算法
        post(() -> {
            // 这里要延迟执行，否则会导致子 View.getWidth 的时候为零
            int barWidth = right - left;
            int sideWidth = Math.max(mLeftView.getWidth(), mRightView.getWidth());
            int maxWidth = sideWidth * 2 + mTitleView.getWidth();
            // 算出来子 View 的宽大于标题栏的宽度
            if (maxWidth >= barWidth) {
                // 判断是左右项太长还是标题项太长
                if (sideWidth > barWidth / 3) {
                    // 如果是左右项太长，那么按照比例进行划分
                    BSYTitleBarSupport.setMaxWidth(mLeftView, barWidth / 4);
                    BSYTitleBarSupport.setMaxWidth(mTitleView, barWidth / 2);
                    BSYTitleBarSupport.setMaxWidth(mRightView, barWidth / 4);
                } else {
                    // 如果是标题项太长，那么就进行动态计算
                    BSYTitleBarSupport.setMaxWidth(mLeftView, sideWidth);
                    BSYTitleBarSupport.setMaxWidth(mTitleView, barWidth - sideWidth * 2);
                    BSYTitleBarSupport.setMaxWidth(mRightView, sideWidth);
                }
            } else {
                // 不限制子 View 的最大宽度
                BSYTitleBarSupport.setMaxWidth(mLeftView, Integer.MAX_VALUE);
                BSYTitleBarSupport.setMaxWidth(mTitleView, Integer.MAX_VALUE);
                BSYTitleBarSupport.setMaxWidth(mRightView, Integer.MAX_VALUE);
            }

            // 解决在外部触摸时触发点击效果的问题
            mLeftView.setClickable(true);
            mTitleView.setClickable(true);
            mRightView.setClickable(true);
            // TextView 里面必须有东西才能被点击
            mLeftView.setEnabled(BSYTitleBarSupport.isContainContent(mLeftView));
            mTitleView.setEnabled(BSYTitleBarSupport.isContainContent(mTitleView));
            mRightView.setEnabled(BSYTitleBarSupport.isContainContent(mRightView));

            // 这里再次监听需要延迟，否则会导致递归的情况发生
            addOnLayoutChangeListener(BSYTitleBar.this);
        });
    }

    /**
     * {@link OnClickListener}
     */

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        if (view == mLeftView) {
            mListener.onLeftClick(this);
        } else if (view == mRightView) {
            mListener.onRightClick(this);
        } else if (view == mTitleView) {
            mListener.onTitleClick(this);
        }
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        if (params.width == LayoutParams.WRAP_CONTENT) {
            // 如果当前宽度是自适应则转换成占满父布局
            params.width = LayoutParams.MATCH_PARENT;
        }

        int verticalPadding = 0;
        // 如果当前高度是自适应则设置默认的内间距
        if (params.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            verticalPadding = mVerticalPadding;
        }

        setChildVerticalPadding(verticalPadding);
        super.setLayoutParams(params);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置标题栏的点击监听器
     */
    public BSYTitleBar setOnTitleBarListener(OnBSYTitleBarListener listener) {
        mListener = listener;
        // 设置监听
        mTitleView.setOnClickListener(this);
        mLeftView.setOnClickListener(this);
        mRightView.setOnClickListener(this);
        return this;
    }

    /**
     * 设置标题的文本
     */
    public BSYTitleBar setTitle(int id) {
        return setTitle(getResources().getString(id));
    }

    public BSYTitleBar setTitle(CharSequence text) {
        mTitleView.setText(text);
        return this;
    }

    public CharSequence getTitle() {
        return mTitleView.getText();
    }

    /**
     * 设置左标题的文本
     */
    public BSYTitleBar setLeftTitle(int id) {
        return setLeftTitle(getResources().getString(id));
    }

    public BSYTitleBar setLeftTitle(CharSequence text) {
        mLeftView.setText(text);
        return this;
    }

    public CharSequence getLeftTitle() {
        return mLeftView.getText();
    }

    /**
     * 设置右标题的文本
     */
    public BSYTitleBar setRightTitle(int id) {
        return setRightTitle(getResources().getString(id));
    }

    public BSYTitleBar setRightTitle(CharSequence text) {
        mRightView.setText(text);
        return this;
    }

    public CharSequence getRightTitle() {
        return mRightView.getText();
    }

    public BSYTitleBar setTitleStyle(int style) {
        return setTitleStyle(BSYTitleBarSupport.getTextTypeface(style), style);
    }

    /**
     * 设置标题样式
     *
     * @param typeface              字体样式
     * @param style                 文字样式
     */
    public BSYTitleBar setTitleStyle(Typeface typeface, int style) {
        mTitleView.setTypeface(typeface, style);
        return this;
    }

    public BSYTitleBar setLeftTitleStyle(int style) {
        return setLeftTitleStyle(BSYTitleBarSupport.getTextTypeface(style), style);
    }

    /**
     * 设置左标题样式
     *
     * @param typeface              字体样式
     * @param style                 文字样式
     */
    public BSYTitleBar setLeftTitleStyle(Typeface typeface, int style) {
        mLeftView.setTypeface(typeface, style);
        return this;
    }

    public BSYTitleBar setRightTitleStyle(int style) {
        return setRightTitleStyle(BSYTitleBarSupport.getTextTypeface(style), style);
    }

    /**
     * 设置右边标题样式
     *
     * @param typeface              字体样式
     * @param style                 文字样式
     */
    public BSYTitleBar setRightTitleStyle(Typeface typeface, int style) {
        mRightView.setTypeface(typeface, style);
        return this;
    }

    /**
     * 设置标题的字体颜色
     */
    public BSYTitleBar setTitleColor(int color) {
        return setTitleColor(ColorStateList.valueOf(color));
    }

    public BSYTitleBar setTitleColor(ColorStateList color) {
        if (color != null) {
            mTitleView.setTextColor(color);
        }
        return this;
    }

    /**
     * 设置左标题的字体颜色
     */
    public BSYTitleBar setLeftTitleColor(int color) {
        return setLeftTitleColor(ColorStateList.valueOf(color));
    }

    public BSYTitleBar setLeftTitleColor(ColorStateList color) {
        if (color != null) {
            mLeftView.setTextColor(color);
        }
        return this;
    }

    /**
     * 设置右标题的字体颜色
     */
    public BSYTitleBar setRightTitleColor(int color) {
        return setRightTitleColor(ColorStateList.valueOf(color));
    }

    public BSYTitleBar setRightTitleColor(ColorStateList color) {
        if (color != null) {
            mRightView.setTextColor(color);
        }
        return this;
    }

    /**
     * 设置标题的字体大小
     */
    public BSYTitleBar setTitleSize(float size) {
        return setTitleSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public BSYTitleBar setTitleSize(int unit, float size) {
        mTitleView.setTextSize(unit, size);
        return this;
    }

    /**
     * 设置左标题的字体大小
     */
    public BSYTitleBar setLeftTitleSize(float size) {
        return setLeftTitleSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public BSYTitleBar setLeftTitleSize(int unit, float size) {
        mLeftView.setTextSize(unit, size);
        return this;
    }

    /**
     * 设置右标题的字体大小
     */
    public BSYTitleBar setRightTitleSize(float size) {
        return setRightTitleSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public BSYTitleBar setRightTitleSize(int unit, float size) {
        mRightView.setTextSize(unit, size);
        return this;
    }

    /**
     * 设置标题的图标
     */
    public BSYTitleBar setTitleIcon(int id) {
        return setTitleIcon(BSYTitleBarSupport.getDrawable(getContext(), id));
    }

    public BSYTitleBar setTitleIcon(Drawable drawable) {
        BSYTitleBarSupport.setDrawableTint(drawable, mTitleIconTint);
        BSYTitleBarSupport.setDrawableSize(drawable, mTitleIconWidth, mTitleIconHeight);
        BSYTitleBarSupport.setTextCompoundDrawable(mTitleView, drawable, mTitleIconGravity);
        return this;
    }

    public Drawable getTitleIcon() {
        return BSYTitleBarSupport.getTextCompoundDrawable(mTitleView, mTitleIconGravity);
    }

    /**
     * 设置左标题的图标
     */
    public BSYTitleBar setLeftIcon(int id) {
        return setLeftIcon(BSYTitleBarSupport.getDrawable(getContext(), id));
    }

    public BSYTitleBar setLeftIcon(Drawable drawable) {
        BSYTitleBarSupport.setDrawableTint(drawable, mLeftIconTint);
        BSYTitleBarSupport.setDrawableSize(drawable, mLeftIconWidth, mLeftIconHeight);
        BSYTitleBarSupport.setTextCompoundDrawable(mLeftView, drawable, mLeftIconGravity);
        return this;
    }

    public Drawable getLeftIcon() {
        return BSYTitleBarSupport.getTextCompoundDrawable(mLeftView, mLeftIconGravity);
    }

    /**
     * 设置右标题的图标
     */
    public BSYTitleBar setRightIcon(int id) {
        return setRightIcon(BSYTitleBarSupport.getDrawable(getContext(), id));
    }

    public BSYTitleBar setRightIcon(Drawable drawable) {
        BSYTitleBarSupport.setDrawableTint(drawable, mRightIconTint);
        BSYTitleBarSupport.setDrawableSize(drawable, mRightIconWidth, mRightIconHeight);
        BSYTitleBarSupport.setTextCompoundDrawable(mRightView, drawable, mRightIconGravity);
        return this;
    }

    public Drawable getRightIcon() {
        return BSYTitleBarSupport.getTextCompoundDrawable(mRightView, mRightIconGravity);
    }

    /**
     * 设置标题的图标大小
     */
    public BSYTitleBar setTitleIconSize(int width, int height) {
        mTitleIconWidth = width;
        mTitleIconHeight = height;
        BSYTitleBarSupport.setDrawableSize(getTitleIcon(), width, height);
        return this;
    }

    /**
     * 设置左标题的图标大小
     */
    public BSYTitleBar setLeftIconSize(int width, int height) {
        mLeftIconWidth = width;
        mLeftIconHeight = height;
        BSYTitleBarSupport.setDrawableSize(getLeftIcon(), width, height);
        return this;
    }

    /**
     * 设置右标题的图标大小
     */
    public BSYTitleBar setRightIconSize(int width, int height) {
        mRightIconWidth = width;
        mRightIconHeight = height;
        BSYTitleBarSupport.setDrawableSize(getRightIcon(), width, height);
        return this;
    }

    /**
     * 设置标题的文字和图标间距
     */
    public BSYTitleBar setTitleIconPadding(int padding) {
        mTitleView.setCompoundDrawablePadding(padding);
        return this;
    }

    /**
     * 设置左标题的文字和图标间距
     */
    public BSYTitleBar setLeftIconPadding(int padding) {
        mLeftView.setCompoundDrawablePadding(padding);
        return this;
    }

    /**
     * 设置右标题的文字和图标间距
     */
    public BSYTitleBar setRightIconPadding(int padding) {
        mRightView.setCompoundDrawablePadding(padding);
        return this;
    }

    /**
     * 设置标题的图标着色器
     */
    public BSYTitleBar setTitleIconTint(int color) {
        mTitleIconTint = color;
        BSYTitleBarSupport.setDrawableTint(getTitleIcon(), color);
        return this;
    }

    /**
     * 设置左标题的图标着色器
     */
    public BSYTitleBar setLeftIconTint(int color) {
        mLeftIconTint = color;
        BSYTitleBarSupport.setDrawableTint(getLeftIcon(), color);
        return this;
    }

    /**
     * 设置右标题的图标着色器
     */
    public BSYTitleBar setRightIconTint(int color) {
        mRightIconTint = color;
        BSYTitleBarSupport.setDrawableTint(getRightIcon(), color);
        return this;
    }

    /**
     * 清楚标题的图标着色器
     */
    public BSYTitleBar clearTitleIconTint() {
        mTitleIconTint = BSYTitleBarSupport.NO_COLOR;
        BSYTitleBarSupport.clearDrawableTint(getTitleIcon());
        return this;
    }

    /**
     * 清楚左标题的图标着色器
     */
    public BSYTitleBar clearLeftIconTint() {
        mLeftIconTint = BSYTitleBarSupport.NO_COLOR;
        BSYTitleBarSupport.clearDrawableTint(getLeftIcon());
        return this;
    }

    /**
     * 清楚右标题的图标着色器
     */
    public BSYTitleBar clearRightIconTint() {
        mRightIconTint = BSYTitleBarSupport.NO_COLOR;
        BSYTitleBarSupport.clearDrawableTint(getRightIcon());
        return this;
    }

    /**
     * 设置标题的图标显示重心
     */
    public BSYTitleBar setTitleIconGravity(int gravity) {
        Drawable drawable = getTitleIcon();
        mTitleIconGravity = gravity;
        if (drawable != null) {
            BSYTitleBarSupport.setTextCompoundDrawable(mTitleView, drawable, gravity);
        }
        return this;
    }

    /**
     * 设置左标题的图标显示重心
     */
    public BSYTitleBar setLeftIconGravity(int gravity) {
        Drawable drawable = getLeftIcon();
        mLeftIconGravity = gravity;
        if (drawable != null) {
            BSYTitleBarSupport.setTextCompoundDrawable(mLeftView, drawable, gravity);
        }
        return this;
    }

    /**
     * 设置右标题的图标显示重心
     */
    public BSYTitleBar setRightIconGravity(int gravity) {
        Drawable drawable = getRightIcon();
        mRightIconGravity = gravity;
        if (drawable != null) {
            BSYTitleBarSupport.setTextCompoundDrawable(mRightView, drawable, gravity);
        }
        return this;
    }

    /**
     * 设置左标题的背景状态选择器
     */
    public BSYTitleBar setLeftBackground(int id) {
        return setLeftBackground(BSYTitleBarSupport.getDrawable(getContext(), id));
    }

    public BSYTitleBar setLeftBackground(Drawable drawable) {
        BSYTitleBarSupport.setBackground(mLeftView, drawable);
        return this;
    }

    /**
     * 设置右标题的背景状态选择器
     */
    public BSYTitleBar setRightBackground(int id) {
        return setRightBackground(BSYTitleBarSupport.getDrawable(getContext(), id));
    }

    public BSYTitleBar setRightBackground(Drawable drawable) {
        BSYTitleBarSupport.setBackground(mRightView, drawable);
        return this;
    }

    /**
     * 设置左标题的前景状态选择器
     */
    public BSYTitleBar setLeftForeground(int id) {
        return setLeftForeground(BSYTitleBarSupport.getDrawable(getContext(), id));
    }

    public BSYTitleBar setLeftForeground(Drawable drawable) {
        BSYTitleBarSupport.setForeground(mLeftView, drawable);
        return this;
    }

    /**
     * 设置右标题的前景状态选择器
     */
    public BSYTitleBar setRightForeground(int id) {
        return setRightForeground(BSYTitleBarSupport.getDrawable(getContext(), id));
    }

    public BSYTitleBar setRightForeground(Drawable drawable) {
        BSYTitleBarSupport.setForeground(mRightView, drawable);
        return this;
    }

    /**
     * 设置分割线是否显示
     */
    public BSYTitleBar setLineVisible(boolean visible) {
        mLineView.setVisibility(visible ? VISIBLE : INVISIBLE);
        return this;
    }

    /**
     * 设置分割线的颜色
     */
    public BSYTitleBar setLineColor(int color) {
        return setLineDrawable(new ColorDrawable(color));
    }

    public BSYTitleBar setLineDrawable(Drawable drawable) {
        BSYTitleBarSupport.setBackground(mLineView, drawable);
        return this;
    }

    /**
     * 设置分割线的大小
     */
    public BSYTitleBar setLineSize(int px) {
        ViewGroup.LayoutParams layoutParams = mLineView.getLayoutParams();
        layoutParams.height = px;
        mLineView.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置标题重心
     */
    @SuppressLint("RtlHardcoded")
    public BSYTitleBar setTitleGravity(int gravity) {
        gravity = BSYTitleBarSupport.getAbsoluteGravity(this, gravity);

        // 如果标题的重心为左，那么左边就不能有内容
        if (gravity == Gravity.LEFT &&
                BSYTitleBarSupport.isContainContent(BSYTitleBarSupport.isLayoutRtl(getContext()) ? mRightView : mLeftView)) {
            Log.e(LOG_TAG, "Title center of gravity for the left, the left title can not have content");
            return this;
        }

        // 如果标题的重心为右，那么右边就不能有内容
        if (gravity == Gravity.RIGHT &&
                BSYTitleBarSupport.isContainContent(BSYTitleBarSupport.isLayoutRtl(getContext()) ? mLeftView : mRightView)) {
            Log.e(LOG_TAG, "Title center of gravity for the right, the right title can not have content");
            return this;
        }

        LayoutParams params = (LayoutParams) mTitleView.getLayoutParams();
        params.gravity = gravity;
        mTitleView.setLayoutParams(params);
        return this;
    }

    /**
     * 设置子 View 内间距
     */
    public BSYTitleBar setChildVerticalPadding(int verticalPadding) {
        mVerticalPadding = verticalPadding;
        mLeftView.setPadding(mLeftHorizontalPadding, mVerticalPadding, mLeftHorizontalPadding, mVerticalPadding);
        mTitleView.setPadding(mTitleHorizontalPadding, mVerticalPadding, mTitleHorizontalPadding, mVerticalPadding);
        mRightView.setPadding(mRightHorizontalPadding, mVerticalPadding, mRightHorizontalPadding, mVerticalPadding);
        return this;
    }

    public BSYTitleBar setChildHorizontalPadding(int leftHorizontalPadding, int titleHorizontalPadding, int rightHorizontalPadding) {
        mLeftHorizontalPadding = leftHorizontalPadding;
        mTitleHorizontalPadding = titleHorizontalPadding;
        mRightHorizontalPadding = rightHorizontalPadding;
        mLeftView.setPadding(mLeftHorizontalPadding, mVerticalPadding, mLeftHorizontalPadding, mVerticalPadding);
        mTitleView.setPadding(mTitleHorizontalPadding, mVerticalPadding, mTitleHorizontalPadding, mVerticalPadding);
        mRightView.setPadding(mRightHorizontalPadding, mVerticalPadding, mRightHorizontalPadding, mVerticalPadding);
        return this;
    }

    /**
     * 获取左标题View对象
     */
    public TextView getLeftView() {
        return mLeftView;
    }

    /**
     * 获取标题View对象
     */
    public TextView getTitleView() {
        return mTitleView;
    }

    /**
     * 获取右标题View对象
     */
    public TextView getRightView() {
        return mRightView;
    }

    /**
     * 获取分割线View对象
     */
    public View getLineView() {
        return mLineView;
    }

    /**
     * 获取当前的初始化器
     */
    public BSYITitleBarStyle getCurrentStyle() {
        return mCurrentStyle;
    }

    /**
     * 设置默认初始化器
     */
    public static void setDefaultStyle(BSYITitleBarStyle style) {
        sGlobalStyle = style;
    }

    /**actionBar风格模式*/
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({BarStyle.LIGHT,BarStyle.NIGHT,BarStyle.TRANSPARENT,BarStyle.RIPPLE})
    public @interface BarStyle{
        /**白天模式*/
        int LIGHT=0x10;
        /**暗夜模式*/
        int NIGHT=0x20;
        /**透明模式*/
        int TRANSPARENT=0x30;
        /**水波纹*/
        int RIPPLE=0x40;
    }

    /**标题文字重心*/
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TitleGravity.LIFT,TitleGravity.RIGHT,TitleGravity.CENTER,TitleGravity.START,TitleGravity.END})
    public @interface TitleGravity{
        /**居左*/
        int LIFT=0x03;
        /**居右*/
        int RIGHT=0x05;
        /**居中*/
        int CENTER=0x11;
        /**居开始*/
        int START=0x00800003;
        /**居结束*/
        int END=0x00800005;
    }

    /**标题文字风格*/
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TitleStyle.NORMAL,TitleStyle.BOLD,TitleStyle.ITALIC})
    public @interface TitleStyle{
        /**普通*/
        int NORMAL =0;
        /**加粗*/
        int BOLD=1;
        /**斜体*/
        int ITALIC=2;
    }

    /**标题icon重心*/
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TitleIconGravity.TOP,TitleIconGravity.BOTTOM,TitleIconGravity.LIFT,TitleIconGravity.RIGHT,TitleIconGravity.START,TitleIconGravity.END})
    public @interface TitleIconGravity{
        /**局顶部*/
        int TOP=0x30;
        /**局底部*/
        int BOTTOM=0x50;
        /**居左*/
        int LIFT=0x03;
        /**居右*/
        int RIGHT=0x05;
        /**居中*/
        int CENTER=0x11;
        /**居开始*/
        int START=0x00800003;
        /**居结束*/
        int END=0x00800005;
    }
}