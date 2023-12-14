package com.xiaoyingbo.lib_widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xiaoyingbo.lib_util.BSY.BSYLogUtils;
import com.xiaoyingbo.lib_util.BSY.BSYTextUtils;
import com.xiaoyingbo.lib_util.BSY.BSYUtils;

public class BSYNormalInputView extends BSYInputView {
    private static final String TAG="BSYNormalInputView";

    private BSYIconView mIvLeftIcon;
    private String mLeftIcon;
    private BSYIconView mIvRightIcon;
    private String mRightIcon;

    public BSYNormalInputView(Context context) {
        super(context);
    }

    public BSYNormalInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BSYNormalInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initViews(AttributeSet attrs) {
        initAttrs(attrs);

        super.initViews(attrs);
        changeFocusMode(false);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BSYNormalInputView);

        mLeftIcon =typedArray.getString(R.styleable.BSYNormalInputView_normal_left_icon);
        mRightIcon =typedArray.getString(R.styleable.BSYNormalInputView_normal_right_icon);

        typedArray.recycle();
    }

    @Override
    protected BSYIconView[] getLeftIcons() {
        mIvLeftIcon = new BSYIconView(getContext());
        mIvLeftIcon.setText(mLeftIcon);
        mIvLeftIcon.setTextColor(mViewColorNormal);
        return new BSYIconView[]{mIvLeftIcon};
    }

    @Override
    protected BSYIconView[] getRightIcons() {
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getContext().getResources().getDisplayMetrics());
        mIvRightIcon = new BSYIconView(getContext());
        mIvRightIcon.setVisibility(VISIBLE);
        mIvRightIcon.setPadding(padding, padding, padding, padding);
        mIvRightIcon.setText(mRightIcon);
        BSYLogUtils.d(BSYUtils.isDebug(),TAG,String.format("getRightIcons  %s", mIvRightIcon.getText()),null);
        mIvRightIcon.setTextColor(mViewColorNormal);
        mIvRightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText().setText("");
            }
        });
        return new BSYIconView[]{mIvRightIcon};
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        super.onFocusChange(v, hasFocus);
        changeFocusMode(hasFocus);
    }

    private void changeFocusMode(boolean focus) {
        if (focus) {
            if (isEmpty()) {
                mIvRightIcon.setVisibility(INVISIBLE);
            } else {
                mIvRightIcon.setVisibility(VISIBLE);
            }
            getEditText().setTextColor(mViewColorFocus);
            mIvLeftIcon.setTextColor(mViewColorFocus);
        } else {
            getEditText().setTextColor(mViewColorNormal);
            mIvRightIcon.setVisibility(INVISIBLE);
            mIvLeftIcon.setTextColor(mViewColorNormal);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        super.afterTextChanged(s);
        if (isEmpty()) {
            mIvRightIcon.setVisibility(INVISIBLE);
            mRemindText.setVisibility(INVISIBLE);
        } else {
            mIvRightIcon.setVisibility(VISIBLE);
            boolean notShow =false;
            switch (mInputViewType){
                case InputViewType.PHONE:{
                    notShow = BSYTextUtils.isPhone(s.toString().trim());
                    break;
                }
                case InputViewType.EMAIL:{
                    notShow = BSYTextUtils.isEmail(s.toString().trim());
                    break;
                }
                case InputViewType.CAPTURE:{
                    notShow = BSYTextUtils.isCapture(s.toString().trim());
                    break;
                }
            }

            mRemindText.setVisibility(notShow?INVISIBLE:VISIBLE);
        }
    }
}
