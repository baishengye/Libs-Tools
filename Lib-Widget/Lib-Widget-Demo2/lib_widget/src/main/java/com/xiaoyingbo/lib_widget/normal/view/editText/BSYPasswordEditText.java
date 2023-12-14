package com.xiaoyingbo.lib_widget.normal.view.editText;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.xiaoyingbo.lib_util.BSY.util.BSYTextUtils;
import com.xiaoyingbo.lib_widget.R;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/08/25
 *    desc   : 密码隐藏显示 EditText
 */
public final class BSYPasswordEditText extends BSYRegexEditText
        implements View.OnTouchListener,
        View.OnFocusChangeListener, TextWatcher {

    private Drawable mCurrentDrawable;
    private final Drawable mVisibleDrawable;
    private final Drawable mInvisibleDrawable;

    @Nullable
    private View.OnTouchListener mTouchListener;
    @Nullable
    private View.OnFocusChangeListener mFocusChangeListener;

    /**是否是初始化*/
    private boolean isInit=true;
    /**另一个password控件的id*/
    private @IdRes int mAnotherPasswordEditTextId;
    /**另一password控件*/
    private EditText mAnotherPasswordEditText;

    public BSYPasswordEditText(Context context) {
        this(context, null);
    }

    public BSYPasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    @SuppressWarnings("all")
    public BSYPasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //使不可见
        mVisibleDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.password_off_ic));
        mVisibleDrawable.setBounds(0, 0, mVisibleDrawable.getIntrinsicWidth(), mVisibleDrawable.getIntrinsicHeight());

        //使可见
        mInvisibleDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.password_on_ic));
        mInvisibleDrawable.setBounds(0, 0, mInvisibleDrawable.getIntrinsicWidth(), mInvisibleDrawable.getIntrinsicHeight());

        //使用的图标
        mCurrentDrawable = mVisibleDrawable;

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BSYPasswordEditText);
        mEditTextRole = array.getInt(R.styleable.BSYPasswordEditText_passwordEditTextRole, EditTextRole.PASSWORD);

        mAnotherPasswordEditTextId =array.getResourceId(R.styleable.BSYPasswordEditText_another_password_view_id,NO_ID);
        if(mEditTextRole ==EditTextRole.PASSWORD_COMPARE&& mAnotherPasswordEditTextId ==NO_ID){
            throw new IllegalArgumentException("fuck you! password must not null");
        }
        array.recycle();

        // 密码不可见
        addInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        if (getInputRegex() == null) {
            // 密码输入规则
            setInputRegex(REGEX_NONNULL);
        }

        setDrawableVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        super.addTextChangedListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(isInit){
            isInit=false;
            //如果没有设置另一个passwordTextEdit的id的话就不需要去findViewById
            if(mAnotherPasswordEditTextId !=NO_ID){
                mAnotherPasswordEditText = (EditText)getRootView().findViewById(mAnotherPasswordEditTextId);
            }
        }
    }

    private void setDrawableVisible(boolean visible) {
        if (mCurrentDrawable.isVisible() == visible) {
            return;
        }

        mCurrentDrawable.setVisible(visible, false);
        Drawable[] drawables = getCompoundDrawablesRelative();
        setCompoundDrawablesRelative(
                drawables[0],
                drawables[1],
                visible ? mCurrentDrawable : null,
                drawables[3]);
    }

    /**刷新图标状态*/
    private void refreshDrawableStatus() {
        Drawable[] drawables = getCompoundDrawablesRelative();
        setCompoundDrawablesRelative(
                drawables[0],
                drawables[1],
                mCurrentDrawable,
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
        changeHintText(hasFocus,getText()!=null?getText().toString().trim():"");
        if (mFocusChangeListener != null) {
            mFocusChangeListener.onFocusChange(view, hasFocus);
        }
    }

    /**
     * {@link View.OnTouchListener}
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
            touchDrawable = x > getWidth() - mCurrentDrawable.getIntrinsicWidth() - getPaddingEnd() &&
                    x < getWidth() - getPaddingEnd();
        } else if (layoutDirection == LAYOUT_DIRECTION_RTL) {
            // 从右往左
            touchDrawable = x > getPaddingStart() &&
                    x < getPaddingStart() + mCurrentDrawable.getIntrinsicWidth();
        }

        if (mCurrentDrawable.isVisible() && touchDrawable) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (mCurrentDrawable == mVisibleDrawable) {
                    mCurrentDrawable = mInvisibleDrawable;
                    // 密码可见
                    setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    refreshDrawableStatus();
                } else if (mCurrentDrawable == mInvisibleDrawable) {
                    mCurrentDrawable = mVisibleDrawable;
                    // 密码不可见
                    setTransformationMethod(PasswordTransformationMethod.getInstance());
                    refreshDrawableStatus();
                }
                Editable editable = getText();
                if (editable != null) {
                    setSelection(editable.toString().length());
                }
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

            //当密码变化的时候，如果需要确认密码的话就要把确认密码的编辑框置为空
            if(mEditTextRole ==EditTextRole.PASSWORD&& mAnotherPasswordEditTextId !=NO_ID&& mAnotherPasswordEditText !=null){
                mAnotherPasswordEditText.setText("");
                mAnotherPasswordEditText.setTag(false);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @SuppressLint("SwitchIntDef")
    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        changeHintText(true,text);
    }

    @SuppressLint("SwitchIntDef")
    protected void changeHintText(boolean hasFocus, String text) {
        if (mTextInputLayout == null) return;

        if (BSYTextUtils.isEmpty(text)) {
            mTextInputLayout.showHint();
            return;
        }

        switch (mEditTextRole) {
            case EditTextRole.PASSWORD:
                mIsRule = BSYTextUtils.isNMPassword(text);
                break;
            case EditTextRole.PASSWORD_COMPARE:
                mIsRule = BSYTextUtils.equals(text,mAnotherPasswordEditText!=null?mAnotherPasswordEditText.getText().toString():"");
                break;
        }
        if (!mIsRule) {
            mTextInputLayout.showError();
        } else {
            mTextInputLayout.showSuccess();
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }
}