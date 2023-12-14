package com.xiaoyingbo.lib_widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BSYCountDownTimerView extends BSYSubmitView{
    public BSYCountDownTimerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context,attrs);
    }

    public BSYCountDownTimerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BSYCountDownTimerView(@NonNull Context context) {
        this(context,null);
    }

    private CountDownTimer mCountDownTimer;

    private String mCaptureDescribeDown;
    private String mCaptureDescribeNormal;
    private int mCaptureColorDown;
    private int mCaptureColorNormal;

    private BSYOnClickListener mBSYOnclickListener;

    private void initViews(Context context, AttributeSet attrs) {
        initAttrs(context,attrs);
        initCountDownTimer();

        setFocusable(true);
        setClickable(true);
        setText(mCaptureDescribeNormal);
        setTextColor(mCaptureColorNormal);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setFocusable(false);
                setClickable(false);
                setText(mCaptureDescribeDown);
                setTextColor(mCaptureColorDown);
                if(mBSYOnclickListener!=null){
                    mBSYOnclickListener.onClick();
                }
                mCountDownTimer.start();
            }
        });
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BSYCountDownTimerButton);

        mCaptureDescribeDown =typedArray.getString(R.styleable.BSYCountDownTimerButton_timer_button_describe_down);
        mCaptureDescribeNormal =typedArray.getString(R.styleable.BSYCountDownTimerButton_timer_button_describe_normal);
        mCaptureColorDown =typedArray.getColor(R.styleable.BSYCountDownTimerButton_timer_button_color_down,getResources().getColor(R.color.color_default,null));
        mCaptureColorNormal =typedArray.getColor(R.styleable.BSYCountDownTimerButton_timer_button_color_normal,getResources().getColor(R.color.color_default,null));

        typedArray.recycle();
    }

    private void initCountDownTimer() {
        if(mCountDownTimer==null){
            mCountDownTimer=new CountDownTimer(60*1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setText(String.format(mCaptureDescribeDown,millisUntilFinished/1000));
                    invalidate();
                }

                @Override
                public void onFinish() {
                    setFocusable(true);
                    setClickable(true);
                    setTextColor(mCaptureColorNormal);
                    setText(mCaptureDescribeNormal);
                    cancel();
                    invalidate();
                }
            };
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if(mCountDownTimer!=null){
            mCountDownTimer.cancel();
            mCountDownTimer=null;
        }
    }

    public void setBSYOnClickListener(BSYOnClickListener listener){
        this.mBSYOnclickListener=listener;
    }

    public interface BSYOnClickListener{
        void onClick();
    }
}
