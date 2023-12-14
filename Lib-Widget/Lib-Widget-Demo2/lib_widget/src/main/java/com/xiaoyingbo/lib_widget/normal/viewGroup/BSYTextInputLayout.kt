package com.xiaoyingbo.lib_widget.normal.viewGroup

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout
import com.xiaoyingbo.lib_widget.R

class BSYTextInputLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    TextInputLayout(context, attrs, defStyleAttr) {

    private var textHint:String?=null
    private var textHintColor:ColorStateList?=null

    private var textError:String?=null
    private var textErrorColor:ColorStateList?=null

    private var textSuccess:String?=null
    private var textSuccessColor:ColorStateList?=null

    constructor(context: Context,attrs: AttributeSet?):this(context,attrs,0){}

    constructor(context: Context):this(context,null){}

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.BSYTextInputLayout)

        textHint = hint.toString()
        textHintColor = hintTextColor

        textError = array.getString(R.styleable.BSYTextInputLayout_error_text)
        textErrorColor = ColorStateList.valueOf(array.getColor(R.styleable.BSYTextInputLayout_error_color,Color.RED))

        textSuccess = array.getString(R.styleable.BSYTextInputLayout_success_text)
        textSuccessColor = ColorStateList.valueOf(array.getColor(R.styleable.BSYTextInputLayout_success_color,Color.GREEN))

        array.recycle()
    }

    public fun showError(){
        hint=textError
        hintTextColor=textErrorColor
        invalidate()
    }

    public fun showSuccess(){
        hint=textSuccess
        hintTextColor=textSuccessColor
        invalidate()
    }

    public fun showHint(){
        hint=textHint
        hintTextColor=textHintColor
        invalidate()
    }
}