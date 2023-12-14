package com.xiaoyingbo.lib_widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import com.xiaoyingbo.lib_util.BSY.BSYLogUtils
import com.xiaoyingbo.lib_util.BSY.BSYUtils

/**
 * @author xiaoyingbo
 * @since 2022/9/11
 * todo 使用阿里巴巴矢量图的View
 * */
open class BSYIconView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    constructor(context: Context,attrs: AttributeSet?):this(context, attrs,0)
    constructor(context: Context):this(context,null)

    private val TAG=this::class.simpleName

    init {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        if(attrs==null) return

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BSYIconView)
        var iconFontPath = typedArray.getString(R.styleable.BSYIconView_icv_alibaba_icon_font_path)
        BSYLogUtils.d(BSYUtils.isDebug(),TAG,"iconFontPath:${iconFontPath}",null);
        if(iconFontPath!=null){
            iconFontPath=iconFontPath.trim();
            if(iconFontPath.isNotEmpty()){
                val iconfont = Typeface.createFromAsset(context.assets,iconFontPath)
                BSYLogUtils.d(BSYUtils.isDebug(),TAG,"iconfont.isNull:${iconfont==null}",null);
                typeface = iconfont
            }
        }
        typedArray.recycle()
    }

}