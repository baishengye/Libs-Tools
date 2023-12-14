package com.xiaoyingbo.lib_widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.core.widget.TextViewCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class BSYInputView extends FrameLayout implements View.OnFocusChangeListener, TextWatcher, BSYSubmitView.EditTextWrapper {
    private static final String TAG="BSYInputView";

    private EditText mEditText;
    private View mBottomLine;
    private boolean isEmpty = true;


    protected int mViewHeightFocus;
    protected int mViewColorFocus;
    protected int mViewHeightNormal;
    protected int mViewColorNormal;
    protected Typeface mIconFont;
    protected String mIconFontPath;
    protected int mIcIconSize;
    protected int mIcIconMargin;
    protected int mEtMargin;
    protected String mHintText;
    protected int mEtTextColor;
    protected int mEtHintTextColor;
    protected int mEtTextSize;
    protected int mEtType;
    protected String mInputText;
    protected int mInputViewType;
    protected int mInputColorWrong;
    protected String mInputRemindWrong;
    protected TextView mRemindText;

    public OnInputTextListener getInputTextListener() {
        return mInputTextListener;
    }

    public void setInputTextListener(OnInputTextListener mInputTextListener) {
        this.mInputTextListener = mInputTextListener;
    }

    protected OnInputTextListener mInputTextListener;

    public BSYInputView(Context context) {
        this(context, null);
    }

    public BSYInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BSYInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(attrs);
    }

    @Override
    public EditText getEditText() {
        return mEditText;
    }

    @Override
    public int getInputViewType() {
        return mInputViewType;
    }

    public View getBottomLine() {
        return mBottomLine;
    }

    public String getText() {
        return mEditText.getText().toString().trim();
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    /**初始化Views*/
    protected void initViews(AttributeSet attrs) {

        initAttrs(attrs);

        //region Font
        if(mIconFontPath !=null&& mIconFontPath.length()>0){
            mIconFont = Typeface.createFromAsset(getContext().getAssets(), mIconFontPath);
        }
        //endregion

        addViews();
    }

    /**添加Views*/
    private void addViews() {

        int topMargin =Math.max(mIcIconSize/2, 2);
        mRemindText =new TextView(getContext());
        LayoutParams tvParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, topMargin);
        tvParams.gravity=Gravity.TOP|Gravity.START;
        mRemindText.setLayoutParams(tvParams);
        mRemindText.setText(mInputRemindWrong);
        mRemindText.setTextColor(mInputColorWrong);
        mRemindText.setVisibility(VISIBLE);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(mRemindText,1, mIcIconSize,1, TypedValue.COMPLEX_UNIT_DIP);
        addView(mRemindText);

        //region 图标
        BSYIconView[] ivIconLefts = getLeftIcons();
        int ivIconLeftCount = ivIconLefts != null ? ivIconLefts.length : 0;
        for (int i = 0; i < ivIconLeftCount; i++) {
            BSYIconView ivIconLeft = ivIconLefts[i];
            ivIconLeft.setTypeface(mIconFont);
            ivIconLeft.setTextSize(mIcIconSize);
            LayoutParams ivIconLeftParams = new LayoutParams(mIcIconSize, mIcIconSize);
            ivIconLeftParams.setMarginStart((mIcIconSize + mIcIconMargin) * i);
            ivIconLeftParams.topMargin=topMargin;
            ivIconLeftParams.bottomMargin=topMargin/2;
            ivIconLeftParams.gravity = Gravity.CENTER_VERTICAL|Gravity.START;
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(ivIconLeft,10, mIcIconSize,1, TypedValue.COMPLEX_UNIT_DIP);
            addView(ivIconLeft, ivIconLeftParams);
        }
        BSYIconView[] ivIconRights = getRightIcons();
        int mIvIconRightCount = ivIconRights != null ? ivIconRights.length : 0;
        for (int i = 0; i < mIvIconRightCount; i++) {
            BSYIconView ivIconRight = ivIconRights[i];
            ivIconRight.setTypeface(mIconFont);
            ivIconRight.setTextSize(mIcIconSize);
            LayoutParams ivIconRightParams = new LayoutParams(mIcIconSize, mIcIconSize);
            ivIconRightParams.setMarginEnd((mIcIconSize + mIcIconMargin) * i);
            ivIconRightParams.topMargin=topMargin;
            ivIconRightParams.bottomMargin=topMargin/2;
            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(ivIconRight,10, mIcIconSize,1, TypedValue.COMPLEX_UNIT_DIP);
            ivIconRightParams.gravity = Gravity.CENTER_VERTICAL|Gravity.END;
            addView(ivIconRight, ivIconRightParams);
        }
        //endregion

        //region editText
        mEditText = new EditText(getContext());
        mEditText.setHint(mHintText);
        LayoutParams etParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        etParams.leftMargin = mIcIconSize * ivIconLeftCount + mIcIconMargin * (ivIconLeftCount - 1) + mEtMargin;
        etParams.rightMargin = mIcIconSize * mIvIconRightCount + mIcIconMargin * (mIvIconRightCount - 1) + mEtMargin;
        mEditText.setPadding(0,topMargin,0,0);
        mEditText.setLayoutParams(etParams);
        mEditText.setBackgroundColor(Color.TRANSPARENT);
        mEditText.setTextColor(mEtTextColor);
        mEditText.setHintTextColor(mEtHintTextColor);
        mEditText.setTextSize(mEtTextSize);
        mEditText.setInputType(mEtType);
        mEditText.setSingleLine();
        mEditText.setOnFocusChangeListener(this);
        mEditText.addTextChangedListener(this);
        mEditText.setText(mInputText);
        addView(mEditText);
        //endregion

        mBottomLine = new View(getContext());
        LayoutParams vParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mViewHeightNormal);
        vParams.gravity = Gravity.BOTTOM;
        mBottomLine.setLayoutParams(vParams);
        mBottomLine.setBackgroundColor(mViewColorNormal);
        addView(mBottomLine);
    }

    /**初始化自定义属性*/
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BSYInputView);

        mViewColorNormal = typedArray.getColor(R.styleable.BSYInputView_iv_icon_color_normal,getResources().getColor(R.color.color_default,getContext().getTheme()));
        mViewHeightNormal = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getContext().getResources().getDisplayMetrics());
        mViewColorFocus = typedArray.getColor(R.styleable.BSYInputView_iv_icon_color_focus,getResources().getColor(R.color.color_default, getContext().getTheme()));
        mViewHeightFocus = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getContext().getResources().getDisplayMetrics());

        mIconFontPath =typedArray.getString(R.styleable.BSYInputView_iv_alibaba_icon_font_path);
        mIcIconSize = (int) typedArray.getDimension(R.styleable.BSYInputView_iv_icon_size,18);
        mIcIconMargin = (int) typedArray.getDimension(R.styleable.BSYInputView_iv_icon_margin,10);

        mEtMargin = (int) typedArray.getDimension(R.styleable.BSYInputView_iv_et_margin_horizontal,10);
        mHintText = typedArray.getString(R.styleable.BSYInputView_iv_et_hint_text);
        mEtTextColor =typedArray.getColor(R.styleable.BSYInputView_iv_et_text_color, getResources().getColor(R.color.color_default, getContext().getTheme()));
        mEtHintTextColor =typedArray.getColor(R.styleable.BSYInputView_iv_et_hint_text_color, getResources().getColor(R.color.color_default, getContext().getTheme()));
        mEtTextSize = (int) typedArray.getDimension(R.styleable.BSYInputView_iv_et_text_size,10);
        mEtType =typedArray.getInteger(R.styleable.BSYInputView_iv_et_input_type,InputType.TYPE_TEXT_VARIATION_NORMAL);
        mInputText =typedArray.getString(R.styleable.BSYInputView_iv_et_text);


        mInputViewType = typedArray.getInteger(R.styleable.BSYInputView_iv_type, BSYNormalInputView.InputViewType.PHONE);
        mInputColorWrong = typedArray.getColor(R.styleable.BSYInputView_iv_color_wrong, getResources().getColor(R.color.red, null));
        mInputRemindWrong = typedArray.getString(R.styleable.BSYInputView_iv_remind_wrong);

        typedArray.recycle();
    }

    protected BSYIconView[] getLeftIcons() {
        return null;
    }

    protected BSYIconView[] getRightIcons() {
        return null;
    }

    private void changeBottomStyle(final boolean hasFocus) {
        final int height;
        final int color;
        if (hasFocus) {
            color = mViewColorFocus;
            height = mViewHeightFocus;
        } else {
            color = mViewColorNormal;
            height = mViewHeightNormal;
        }
        mBottomLine.setBackgroundColor(color);
        mBottomLine.getLayoutParams().height = height;
        mBottomLine.requestLayout();
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        changeBottomStyle(hasFocus);

        if(hasFocus){
            showSoftInput(v);
        }else {
            toggleSoftInput();
        }
    }

    /**隐藏键盘*/
    protected void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**弹出键盘*/
    protected void showSoftInput(View v) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }


    @Override
    public void afterTextChanged(android.text.Editable s) {
        isEmpty = s.toString().trim().length() == 0;
        if(mInputTextListener!=null) mInputTextListener.inputTextChange();
    }

    public interface OnInputTextListener{
        void inputTextChange();
    }

    public static class BSYInputViewEditTextBindingAdapter{
        @BindingAdapter(value = {"inputText"},requireAll = true)
        public static void setInputText(BSYInputView inputView,String text){
            if(text==null) return;
            if(text.equals(inputView.getEditText().getText().toString())) return;
            inputView.getEditText().setText(text);
        }

        @InverseBindingAdapter(attribute = "inputText",event = "inputTextChange")
        public static String getInputText(BSYInputView inputView){
            return inputView.getEditText().getText().toString().trim();
        }

        @BindingAdapter("inputTextChange")
        public static void setInputTextChangeListener(BSYInputView inputView,InverseBindingListener changeListener){
            if(changeListener!=null){
                inputView.setInputTextListener(new OnInputTextListener() {
                    @Override
                    public void inputTextChange() {
                        changeListener.onChange();
                    }
                });
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({InputViewType.EMAIL,InputViewType.PHONE,InputViewType.CAPTURE,InputViewType.PASSWORD})
    public @interface InputViewType {
        int EMAIL=0x1;
        int PHONE=0x2;
        int CAPTURE=0x3;
        int PASSWORD=0x4;
    }
}
