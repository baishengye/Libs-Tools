package com.xiaoyingbo.lib_widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xiaoyingbo.lib_util.BSY.BSYEditTextUtils;
import com.xiaoyingbo.lib_util.BSY.BSYTextUtils;

public class BSYPasswordInputView extends BSYInputView {

    private BSYIconView mIvPasswordIcon;
    private String mPasswordIcon;
    private BSYIconView mIvDeleteIcon;
    private String mDeleteIcon;
    private BSYIconView mIcEyeIcon;
    private String mEyeIcon;

    private boolean isHidePwdMode = true;
    private OnPwdFocusChangedListener mOnPwdFocusChangedListener = null;

    public BSYPasswordInputView(Context context) {
        super(context);
    }

    public BSYPasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BSYPasswordInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnPwdFocusChangedListener(OnPwdFocusChangedListener onPwdFocusChangedListener) {
        mOnPwdFocusChangedListener = onPwdFocusChangedListener;
    }

    @Override
    protected void initViews(AttributeSet attrs) {
        initAttrs(attrs);

        super.initViews(attrs);
        changeFocusMode(false);
        changePwdHideMode(true);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BSYPasswordInputView);

        mPasswordIcon =typedArray.getString(R.styleable.BSYPasswordInputView_password_password_icon);
        mDeleteIcon=typedArray.getString(R.styleable.BSYPasswordInputView_password_delete_icon);
        mEyeIcon=typedArray.getString(R.styleable.BSYPasswordInputView_password_eye_icon);
        typedArray.recycle();
    }

    @Override
    protected BSYIconView[] getLeftIcons() {
        mIvPasswordIcon = new BSYIconView(getContext());
        mIvPasswordIcon.setTextColor(mViewColorNormal);
        mIvPasswordIcon.setText(mPasswordIcon);
        return new BSYIconView[]{mIvPasswordIcon};
    }

    @Override
    protected BSYIconView[] getRightIcons() {
        int paddingDelete = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getContext().getResources().getDisplayMetrics());
        mIvDeleteIcon = new BSYIconView(getContext());
        mIvDeleteIcon.setVisibility(INVISIBLE);
        mIvDeleteIcon.setPadding(paddingDelete, paddingDelete, paddingDelete, paddingDelete);
        mIvDeleteIcon.setTextColor(mViewColorNormal);
        mIvDeleteIcon.setText(mDeleteIcon);
        mIvDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText().setText("");
            }
        });
        int paddingEye = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getContext().getResources().getDisplayMetrics());
        mIcEyeIcon = new BSYIconView(getContext());
        mIcEyeIcon.setVisibility(INVISIBLE);
        mIcEyeIcon.setPadding(paddingEye, paddingEye, paddingEye, paddingEye);
        mIcEyeIcon.setTextColor(mViewColorNormal);
        mIcEyeIcon.setText(mEyeIcon);
        mIcEyeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePwdHideMode(!isHidePwdMode);
            }
        });
        return new BSYIconView[]{mIvDeleteIcon,mIcEyeIcon};
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        super.onFocusChange(v, hasFocus);
        changeFocusMode(hasFocus);
        if (mOnPwdFocusChangedListener != null) {
            mOnPwdFocusChangedListener.onFocusChanged(hasFocus);
        }
    }

    private void changeFocusMode(boolean focus) {
        if (focus) {
            if (isEmpty()) {
                mIvDeleteIcon.setVisibility(INVISIBLE);
            } else {
                mIvDeleteIcon.setVisibility(VISIBLE);

            }
            mIcEyeIcon.setVisibility(VISIBLE);
            mIvPasswordIcon.setTextColor(mViewColorFocus);
            getEditText().setTextColor(mViewColorFocus);
        } else {
            mIvDeleteIcon.setVisibility(INVISIBLE);
            mIcEyeIcon.setVisibility(INVISIBLE);
            mIvPasswordIcon.setTextColor(mViewColorNormal);
            getEditText().setTextColor(mViewColorNormal);
        }
    }

    private void changePwdHideMode(boolean isHidePwdMode) {
        this.isHidePwdMode = isHidePwdMode;
        if (isHidePwdMode) {
            //隐藏密码
            getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
            mIcEyeIcon.setTextColor(mViewColorNormal);
        } else {
            //显示密码
            getEditText().setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mIcEyeIcon.setTextColor(mViewColorFocus);
        }
        BSYEditTextUtils.setTextWithSelection(getEditText(), getEditText().getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        super.afterTextChanged(s);
        if (isEmpty()) {
            mIvDeleteIcon.setVisibility(INVISIBLE);

            mRemindText.setVisibility(INVISIBLE);
        } else {
            mIvDeleteIcon.setVisibility(VISIBLE);

            boolean notShow =BSYTextUtils.isPassword(s.toString().trim());
            mRemindText.setVisibility(notShow?INVISIBLE:VISIBLE);
        }
    }

    public interface OnPwdFocusChangedListener {
        void onFocusChanged(boolean focus);
    }
}
