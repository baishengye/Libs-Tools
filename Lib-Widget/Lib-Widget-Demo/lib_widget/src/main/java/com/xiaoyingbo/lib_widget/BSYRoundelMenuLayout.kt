package com.xiaoyingbo.lib_widget

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.animation.OvershootInterpolator
import androidx.core.content.res.ResourcesCompat
import kotlin.math.sqrt


/**
 * @author xiaoyingbo
 * @since 2022/9/14
 * 炫酷圆盘菜单View
 */
class BSYRoundelMenuLayout  constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int,
) :ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context,attrs: AttributeSet?,defStyleAttr: Int):this(context, attrs, defStyleAttr,0)
    constructor(context: Context,attrs: AttributeSet?):this(context, attrs, 0)
    constructor(context: Context):this(context, null)

    /**收缩时的半径*/
    private var collapsedRadius = 0
    /**展开时的半径*/
    private var expandedRadius = 0
    /**收缩状态时的颜色 / 展开时外圈的颜色*/
    private var mRoundColor = 0
    /**展开时中心圆圈的颜色*/
    private var mCenterColor = 0
    /**中心图标*/
    private var mCenterDrawable: Drawable? = null
    /**子项的宽高*/
    private var mItemWidth = 0
    /**当前展开的进度（0-1）*/
    private var expandProgress = 0f
    /**当前状态 （展开 / 收缩）*/
    private var state = 0
    /**展开或收缩的动画时长*/
    private var mDuration = 0
    /**子View之间的动画间隔*/
    private var mItemAnimIntervalTime = 0

    private lateinit var center: Point
    private lateinit var mRoundPaint: Paint
    private lateinit var mCenterPaint: Paint
    private lateinit var outlineProvider: OvalOutline
    private lateinit var mExpandAnimator: ValueAnimator
    private lateinit var mColorAnimator: ValueAnimator

    init {
        init(context, attrs)
    }


    private fun handleStyleable(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.BSYRoundelMenuLayout)
        collapsedRadius =ta.getDimensionPixelSize(R.styleable.BSYRoundelMenuLayout_rml_collapsedRadius,dp2px(22f))
        expandedRadius =ta.getDimensionPixelSize(R.styleable.BSYRoundelMenuLayout_rml_expandedRadius, dp2px(84f))
        mRoundColor = ta.getColor(R.styleable.BSYRoundelMenuLayout_rml_roundColor,Color.parseColor("#ffffbb33"))
        mCenterColor = ta.getColor(R.styleable.BSYRoundelMenuLayout_rml_centerColor,Color.parseColor("#ffff8800"))
        mDuration = ta.getInteger(R.styleable.BSYRoundelMenuLayout_rml_duration, 400)
        mItemAnimIntervalTime =ta.getInteger(R.styleable.BSYRoundelMenuLayout_rml_item_anim_delay, 50)
        mItemWidth =ta.getDimensionPixelSize(R.styleable.BSYRoundelMenuLayout_rml_item_width, dp2px(22f))
        mCenterDrawable=ta.getDrawable(R.styleable.BSYRoundelMenuLayout_rml_center_drawable)
        ta.recycle()
        require(collapsedRadius <= expandedRadius) { "expandedRadius must bigger than collapsedRadius" }
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        handleStyleable(context, attrs)
        initPaint()

        setWillNotDraw(false)/*确保每次onDraw()都会执行*/

        outlineProvider = OvalOutline()
        if(mCenterDrawable==null) {
            mCenterDrawable=ResourcesCompat.getDrawable(resources, androidx.appcompat.R.drawable.abc_ab_share_pack_mtrl_alpha,null)
        }
        state = STATE_COLLAPSE
        initAnim()
    }

    /*初始化用到的Paint*/
    private fun initPaint(){
        mRoundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mRoundPaint.color = mRoundColor
        mRoundPaint.style = Paint.Style.FILL

        mCenterPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mCenterPaint.color = mRoundColor
        mCenterPaint.style = Paint.Style.FILL

        center = Point()
    }

    private fun initAnim() {
        mExpandAnimator = ValueAnimator.ofFloat(0f, 0f)
        mExpandAnimator.interpolator = OvershootInterpolator()
        mExpandAnimator.duration = mDuration.toLong()
        mExpandAnimator.addUpdateListener(AnimatorUpdateListener { animation ->
            expandProgress = animation.animatedValue as Float
            mRoundPaint.alpha = Math.min(255, (expandProgress * 255).toInt())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                invalidateOutline()
            }
            invalidate()
        })
        mColorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), mRoundColor, mCenterColor)
        mColorAnimator.duration = mDuration.toLong()
        mColorAnimator.addUpdateListener(AnimatorUpdateListener { animation ->
            mCenterPaint.color = (animation.animatedValue as Int)
        })
    }

    private fun collapse(animate: Boolean) {
        state = STATE_COLLAPSE
        for (i in 0 until childCount) {
            getChildAt(i).visibility = GONE
        }
        invalidate()
        if (animate) {
            startCollapseAnimation()
        }
    }

    private fun expand(animate: Boolean) {
        state = STATE_EXPAND
        for (i in 0 until childCount) {
            getChildAt(i).visibility = VISIBLE
        }
        invalidate()
        if (animate) {
            startExpandAnimation()
        } else {
            for (i in 0 until childCount) {
                getChildAt(i).alpha = 1f
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount == 0) {
            return
        }
        calculateMenuItemPosition()
        for (i in 0 until childCount) {
            val item = getChildAt(i)
            item.layout(l + item.x.toInt(),
                t + item.y.toInt(),
                l + item.x.toInt() + item.measuredWidth,
                t + item.y.toInt() + item.measuredHeight)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (i in 0 until childCount) {
            val item = getChildAt(i)
            item.visibility = GONE
            item.alpha = 0f
            item.scaleX = 1f
            item.scaleY = 1f
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchPoint = Point()
        touchPoint[event.x.toInt()] = event.y.toInt()
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {

                //计算触摸点与中心点的距离
                val distance = getPointsDistance(touchPoint, center)
                return if (state == STATE_EXPAND) {
                    //展开状态下，如果点击区域与中心点的距离不处于子菜单区域，就收起菜单
                    if (distance > collapsedRadius + (expandedRadius - collapsedRadius) * expandProgress
                        || distance < collapsedRadius
                    ) {
                        collapse(true)
                        return true
                    }
                    //展开状态下，如果点击区域处于子菜单区域，则不消费事件
                    false
                } else {
                    //收缩状态下，如果点击区域处于中心圆圈范围内，则展开菜单
                    if (distance < collapsedRadius) {
                        expand(true)
                        return true
                    }
                    //收缩状态下，如果点击区域不在中心圆圈范围内，则不消费事件
                    false
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setOutlineProvider(outlineProvider)
        val x: Int = w / 2
        val y: Int = h / 2
        center[x] = y
        //中心图标padding设为10dp
        mCenterDrawable!!.setBounds(center.x - (collapsedRadius - dp2px(10f)),
            center.y - (collapsedRadius - dp2px(10f)),
            center.x + (collapsedRadius - dp2px(10f)),
            center.y + (collapsedRadius - dp2px(10f))
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制放大的圆
        if (expandProgress > 0f) {
            canvas.drawCircle(center.x.toFloat(),
                center.y.toFloat(),
                collapsedRadius + (expandedRadius - collapsedRadius) * expandProgress,
                mRoundPaint)
        }
        //绘制中间圆
        canvas.drawCircle(center.x.toFloat(),
            center.y.toFloat(),
            collapsedRadius + collapsedRadius * .2f * expandProgress,
            mCenterPaint)
        val count =
            canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        //绘制中间的图标
        canvas.rotate(45 * expandProgress, center.x.toFloat(), center.y.toFloat())
        mCenterDrawable!!.draw(canvas)
        canvas.restoreToCount(count)
    }

    /**
     * 展开动画
     */
    private fun startExpandAnimation() {
        mExpandAnimator.setFloatValues(expandProgress, 1f)
        mExpandAnimator.start()
        mColorAnimator.setObjectValues(if (mColorAnimator.animatedValue == null) mRoundColor else mColorAnimator.animatedValue, mCenterColor)
        mColorAnimator.start()
        var delay = mItemAnimIntervalTime
        for (i in 0 until childCount) {
            getChildAt(i).animate()
                .setStartDelay(delay.toLong())
                .setDuration(mDuration.toLong())
                .alphaBy(0f)
                .scaleXBy(0f)
                .scaleYBy(0f)
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .start()
            delay += mItemAnimIntervalTime
        }
    }

    /**
     * 收缩动画
     */
    private fun startCollapseAnimation() {
        mExpandAnimator.setFloatValues(expandProgress, 0f)
        mExpandAnimator.start()
        mColorAnimator.setObjectValues(if (mColorAnimator.animatedValue == null) mCenterColor else mColorAnimator.animatedValue,
            mRoundColor)
        mColorAnimator.start()
        var delay = mItemAnimIntervalTime
        for (i in childCount - 1 downTo 0) {
            getChildAt(i).animate()
                .setStartDelay(delay.toLong())
                .setDuration(mDuration.toLong())
                .alpha(0f)
                .scaleX(0f)
                .scaleY(0f)
                .start()
            delay += mItemAnimIntervalTime
        }
    }

    /**
     * 计算每个子菜单的坐标
     */
    private fun calculateMenuItemPosition() {
        val itemRadius = (expandedRadius + collapsedRadius) / 2f
        val area = RectF(
            center.x - itemRadius,
            center.y - itemRadius,
            center.x + itemRadius,
            center.y + itemRadius)
        val path = Path()
        path.addArc(area, 0f, 360f)
        val measure = PathMeasure(path, false)
        val len = measure.length
        val divisor = childCount
        val divider = len / divisor
        for (i in 0 until childCount) {
            val itemPoints = FloatArray(2)
            measure.getPosTan(i * divider + divider * 0.5f, itemPoints, null)
            val item = getChildAt(i)
            item.x = (itemPoints[0].toInt() - mItemWidth / 2).toFloat()
            item.y = (itemPoints[1].toInt() - mItemWidth / 2).toFloat()
        }
    }

    fun setExpandedRadius(expandedRadius: Int) {
        this.expandedRadius = expandedRadius
        requestLayout()
    }

    fun setCollapsedRadius(collapsedRadius: Int) {
        this.collapsedRadius = collapsedRadius
        requestLayout()
    }

    fun setRoundColor(color: Int) {
        mRoundColor = color
        mRoundPaint.color = mRoundColor
        invalidate()
    }

    fun setCenterColor(color: Int) {
        mCenterColor = color
        mCenterPaint.color = color
        invalidate()
    }

    /**内部圆的边框*/
    inner class OvalOutline : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val radius = (collapsedRadius + (expandedRadius - collapsedRadius) * expandProgress).toInt()
            val area = Rect(
                center.x - radius,
                center.y - radius,
                center.x + radius,
                center.y + radius)
            outline.setRoundRect(area, radius.toFloat())
        }
    }

    /**
     * dp转px
     * @param dpVal   dp value
     * @return px value
     */
    private fun dp2px(dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
            context.resources.displayMetrics).toInt()
    }

    companion object {
        /**
         * 展开状态
         */
        const val STATE_COLLAPSE = 0

        /**
         * 收缩状态
         */
        const val STATE_EXPAND = 1

        /**获取两个点之间的直线距离*/
        fun getPointsDistance(a: Point, b: Point): Double {
            val dx = b.x - a.x
            val dy = b.y - a.y
            return sqrt((dx * dx + dy * dy).toDouble())
        }
    }
}

