package com.xiaoyingbo.lib_widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatTextView;

import com.xiaoyingbo.lib_util.BSY.BSYEditTextUtils;
import com.xiaoyingbo.lib_util.BSY.BSYTextUtils;

/**点击提交view*/
public class BSYSubmitView extends AppCompatTextView implements TextWatcher {

    protected int[] mBindIds;
    protected EditText[] mEditTexts;
    protected boolean mHasInit = false;
    protected int[] mInputViewType;
    protected int mInputTextCount=0;

    public BSYSubmitView(Context context) {
        this(context, null);
    }

    public BSYSubmitView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BSYSubmitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BSYSubmitView);
        mBindIds = new int[]{
                typedArray.getResourceId(R.styleable.BSYSubmitView_sv_bindEditText1, NO_ID),
                typedArray.getResourceId(R.styleable.BSYSubmitView_sv_bindEditText2, NO_ID),
                typedArray.getResourceId(R.styleable.BSYSubmitView_sv_bindEditText3, NO_ID),
                typedArray.getResourceId(R.styleable.BSYSubmitView_sv_bindEditText4, NO_ID),
                typedArray.getResourceId(R.styleable.BSYSubmitView_sv_bindEditText5, NO_ID),
                typedArray.getResourceId(R.styleable.BSYSubmitView_sv_bindEditText6, NO_ID)
        };
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initViews();
    }

    protected void initViews() {
        if (mHasInit) {
            return;
        }
        mHasInit = true;
        mEditTexts = new EditText[mBindIds.length];
        mInputViewType = new int[mBindIds.length];
        for (int i = 0; i < mBindIds.length; i++) {
            View bindView = getRootView().findViewById(mBindIds[i]);
            if(bindView==null) continue;
            mInputTextCount++;
            EditText editText = null;
            int inputViewType= BSYInputView.InputViewType.PHONE;
            if (bindView instanceof EditText) {
                editText = (EditText) bindView;
            } else if (bindView instanceof EditTextWrapper) {
                EditTextWrapper editTextWrapper = (EditTextWrapper) bindView;
                editText = editTextWrapper.getEditText();
                inputViewType=editTextWrapper.getInputViewType();
            }
            mEditTexts[i] = editText;
            mInputViewType[i]=inputViewType;
            if (mEditTexts[i] != null) {
                mEditTexts[i].addTextChangedListener(this);
            }
        }
        for (EditText et : mEditTexts) {
            if (et != null) {
                BSYEditTextUtils.setTextWithSelection(et, et.getText().toString());
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        boolean canSubmit = false;
        for (int i=0;i<mInputTextCount;i++) {
            EditText et=mEditTexts[i];
            int type=mInputViewType[i];
            if (et != null) {
                switch (type){
                    case BSYInputView.InputViewType.PHONE:{
                        canSubmit = BSYTextUtils.isPhone(et.getText().toString().trim());
                        break;
                    }
                    case BSYInputView.InputViewType.EMAIL:{
                        canSubmit = BSYTextUtils.isEmail(et.getText().toString().trim());
                        break;
                    }
                    case BSYInputView.InputViewType.CAPTURE:{
                        canSubmit = BSYTextUtils.isCapture(et.getText().toString().trim());
                        break;
                    }
                    case BSYInputView.InputViewType.PASSWORD:{
                        canSubmit = BSYTextUtils.isPassword(et.getText().toString().trim());
                        break;
                    }
                }
                if(!canSubmit){
                    break;
                }
            }
        }
        if (canSubmit) {
            setAlpha(1.0F);
            setEnabled(true);
        } else {
            setAlpha(0.3F);
            setEnabled(false);
        }
    }

    public interface EditTextWrapper {
        EditText getEditText();
        int getInputViewType();
    }
}

