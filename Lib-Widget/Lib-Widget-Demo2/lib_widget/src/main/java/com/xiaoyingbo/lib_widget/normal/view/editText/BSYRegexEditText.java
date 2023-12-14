package com.xiaoyingbo.lib_widget.normal.view.editText;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.IntDef;

import com.xiaoyingbo.lib_widget.normal.manager.BSYInputTextManager;
import com.xiaoyingbo.lib_util.BSY.util.BSYTextUtils;
import com.xiaoyingbo.lib_widget.R;
import com.xiaoyingbo.lib_widget.normal.viewGroup.BSYTextInputLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Pattern;

public class BSYRegexEditText extends BSYInputTextManager.BSYEditText implements InputFilter,View.OnFocusChangeListener, TextWatcher {

    /** 手机号 */
    public static final String REGEX_MOBILE = "[1]\\d{0,10}";
    /** 中文（普通的中文字符） */
    public static final String REGEX_CHINESE = "[\\u4e00-\\u9fa5]*";
    /** 英文（大写和小写的英文） */
    public static final String REGEX_ENGLISH = "[a-zA-Z]*";
    /** 数字（只允许输入纯数字）*/
    public static final String REGEX_NUMBER = "\\d*";
    /** 计数（非 0 开头的数字） */
    public static final String REGEX_COUNT = "[1-9]\\d*";
    /** 用户名（中文、英文、数字） */
    public static final String REGEX_NAME = "[[\\u4e00-\\u9fa5]|[a-zA-Z]|\\d]*";
    /** 非空格的字符（不能输入空格） */
    public static final String REGEX_NONNULL = "\\S+";

    /**EditText是否是初始化*/
    private boolean isRegexInit=true;

    /** 正则表达式规则 */
    private Pattern mPattern;

    /**编辑框的作用*/
    protected @EditTextRole int mEditTextRole;
    /**textInputLayout的id*/
    private @IdRes int mTextInputLayoutId;
    protected BSYTextInputLayout mTextInputLayout;

    public BSYRegexEditText(Context context) {
        this(context, null);
    }

    public BSYRegexEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public BSYRegexEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BSYRegexEditText);

        if (array.hasValue(R.styleable.BSYRegexEditText_inputRegex)) {
            /*用户自定义的正则*/
            setInputRegex(array.getString(R.styleable.BSYRegexEditText_inputRegex));
        } else if (array.hasValue(R.styleable.BSYRegexEditText_regexType)) {
            /*选取的是已有的正则类型*/
            int regexType = array.getInt(R.styleable.BSYRegexEditText_regexType, RegexType.REGEX_NOTNULL);
            switch (regexType) {
                case RegexType.REGEX_MOBILE:
                    setInputRegex(REGEX_MOBILE);
                    break;
                case RegexType.REGEX_CHINESE:
                    setInputRegex(REGEX_CHINESE);
                    break;
                case RegexType.REGEX_ENGLISH:
                    setInputRegex(REGEX_ENGLISH);
                    break;
                case RegexType.REGEX_NUMBER:
                    setInputRegex(REGEX_NUMBER);
                    break;
                case RegexType.REGEX_COUNT:
                    setInputRegex(REGEX_COUNT);
                    break;
                case RegexType.REGEX_NAME:
                    setInputRegex(REGEX_NAME);
                    break;
                case RegexType.REGEX_NOTNULL:
                    setInputRegex(REGEX_NONNULL);
                    break;
                default:
                    break;
            }
        }

        //编辑框的作用
        mEditTextRole = array.getInt(R.styleable.BSYRegexEditText_editTextRole, EditTextRole.NORMAL);

        //textInputLayout的id
        mTextInputLayoutId =array.getResourceId(R.styleable.BSYRegexEditText_text_input_layout_id,NO_ID);

        array.recycle();

        super.setOnFocusChangeListener(this);
        super.addTextChangedListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(isRegexInit){
            isRegexInit=false;
            if(mTextInputLayoutId !=NO_ID){
                mTextInputLayout = getRootView().findViewById(mTextInputLayoutId);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 是否有这个输入标记
     */
    public boolean hasInputType(int type) {
        return (getInputType() & type) != 0;
    }

    /**
     * 添加一个输入标记
     */
    public void addInputType(int type) {
        setInputType(getInputType() | type);
    }

    /**
     * 移除一个输入标记
     */
    public void removeInputType(int type) {
        setInputType(getInputType() & ~type);
    }

    /**
     * 设置输入正则
     */
    public void setInputRegex(String regex) {
        if (TextUtils.isEmpty(regex)) {
            return;
        }

        /*生成正则*/
        mPattern = Pattern.compile(regex);
        addFilters(this);
    }

    /**
     * 获取输入正则
     */
    public String getInputRegex() {
        if (mPattern == null) {
            return null;
        }
        return mPattern.pattern();
    }

    /**
     * 添加筛选规则
     */
    public void addFilters(InputFilter filter) {
        if (filter == null) {
            return;
        }

        final InputFilter[] newFilters;
        final InputFilter[] oldFilters = getFilters();
        if (oldFilters != null && oldFilters.length > 0) {
            newFilters = new InputFilter[oldFilters.length + 1];
            // 复制旧数组的元素到新数组中
            System.arraycopy(oldFilters, 0, newFilters, 0, oldFilters.length);
            newFilters[oldFilters.length] = filter;
        } else {
            newFilters = new InputFilter[1];
            newFilters[0] = filter;
        }
        super.setFilters(newFilters);
    }

    /**
     * 清空筛选规则
     */
    public void clearFilters() {
        super.setFilters(new InputFilter[0]);
    }

    /**
     * {@link InputFilter}
     *
     * @param source        新输入的字符串
     * @param start         新输入的字符串起始下标
     * @param end           新输入的字符串终点下标
     * @param dest          输入之前文本框内容
     * @param destStart     在原内容上的起始坐标
     * @param destEnd       在原内容上的终点坐标
     * @return              返回字符串将会加入到内容中
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int destStart, int destEnd) {
        if (mPattern == null) {
            return source;
        }

        // 拼接出最终的字符串
        String begin = dest.toString().substring(0, destStart);
        String over = dest.toString().substring(destStart + (destEnd - destStart), destStart + (dest.toString().length() - begin.length()));
        String result = begin + source + over;

        // 判断是插入还是删除
        if (destStart > destEnd - 1) {
            // 如果是插入字符
            if (!mPattern.matcher(result).matches()) {
                // 如果不匹配就不让这个字符输入
                return "";
            }
        } else {
            // 如果是删除字符
            if (!mPattern.matcher(result).matches()) {
                // 如果不匹配则不让删除（删空操作除外）
                if (!"".equals(result)) {
                    return dest.toString().substring(destStart, destEnd);
                }
            }
        }

        // 不做任何修改
        return source;
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        changeHintText(true, text);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        changeHintText(hasFocus, getText() != null ? getText().toString().trim() : "");
    }

    @SuppressLint("SwitchIntDef")
    protected void changeHintText(boolean hasFocus, String text) {
        if (BSYTextUtils.isEmpty(text)) {
            mIsRule=false;
            if(mTextInputLayout!=null)
                mTextInputLayout.showHint();
            return;
        }

        switch (mEditTextRole) {
            case EditTextRole.EMAIL:{
                mIsRule = BSYTextUtils.isEmail(text);
                break;
            }
            case EditTextRole.PHONE:{
                mIsRule = BSYTextUtils.isPhone(text);
                break;
            }
            case EditTextRole.USERNAME:{
                mIsRule = BSYTextUtils.isUsername(text);
                break;
            }
            case EditTextRole.CODE:{
                mIsRule = BSYTextUtils.isCode(text);
            }
        }

        if(mTextInputLayout==null) return;

        if (!mIsRule) {
            mTextInputLayout.showError();
        } else {
            mTextInputLayout.showSuccess();
        }
    }

    /**正则表达式输入框的类型*/
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({RegexType.REGEX_MOBILE,
            RegexType.REGEX_CHINESE,
            RegexType.REGEX_ENGLISH,
            RegexType.REGEX_COUNT,
            RegexType.REGEX_NUMBER,
            RegexType.REGEX_NAME,
            RegexType.REGEX_NOTNULL})
    public @interface RegexType {
        /**手机*/
        int REGEX_MOBILE=0x001;
        /**中文*/
        int REGEX_CHINESE=0x002;
        /**英文*/
        int REGEX_ENGLISH=0x003;
        /**数字*/
        int REGEX_NUMBER=0x004;
        /**计数*/
        int REGEX_COUNT=0x005;
        /**用户名*/
        int REGEX_NAME=0x006;
        /**非空字符*/
        int REGEX_NOTNULL=0x007;
    }

    /**密码编辑框的作用*/
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EditTextRole.PHONE,
            EditTextRole.EMAIL,
            EditTextRole.NORMAL,
            EditTextRole.PASSWORD,
            EditTextRole.PASSWORD_COMPARE,
            EditTextRole.USERNAME,
            EditTextRole.CODE})
    public @interface EditTextRole {
        /**密码*/
        int PASSWORD=0x01;
        /**确认密码*/
        int PASSWORD_COMPARE=0x02;
        /**电话*/
        int PHONE=0x03;
        /**邮箱*/
        int EMAIL=0x04;
        /**无规定*/
        int NORMAL=0x05;
        /**用户名*/
        int USERNAME=0x06;
        /**验证码*/
        int CODE=0x07;
    }
}
