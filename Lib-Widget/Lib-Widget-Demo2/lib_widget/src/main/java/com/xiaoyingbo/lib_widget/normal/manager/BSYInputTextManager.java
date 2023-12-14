package com.xiaoyingbo.lib_widget.normal.manager;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * desc   : 文本输入管理类，通过管理多个 EditText 输入是否为空来启用或者禁用按钮的点击事件
 */
public final class BSYInputTextManager implements TextWatcher, DefaultLifecycleObserver {

    /**
     * 操作按钮的View
     */
    private View mView;
    /**
     * 是否禁用后设置半透明度
     */
    private boolean mAlpha;

    /**
     * TextView集合
     */
    private List<BSYEditText> mViewSet;

    /**
     * 输入监听器
     */
    @Nullable
    private BSYOnInputTextListener mListener;

    /**
     * 构造函数
     *
     * @param alpha 是否需要设置透明度
     */
    private BSYInputTextManager(boolean alpha) {
        mAlpha = alpha;
    }

    /**
     * 创建 Builder
     */
    public static BSYInputTextManager makeManager(boolean alpha) {
        return new BSYInputTextManager(alpha);
    }

    /**
     * 添加 BSYEditText
     *
     * @param views 传入单个或者多个 BSYEditText
     */
    public void addViews(List<BSYEditText> views) {
        if (mView == null) {
            throw new IllegalArgumentException("MainView is null,fuck you");
        }
        if (views == null) {
            return;
        }

        if (mViewSet == null) {
            mViewSet = views;
        } else {
            mViewSet.addAll(views);
        }

        for (BSYEditText view : views) {
            view.addTextChangedListener(this);
        }

        // 触发一次监听
        notifyChanged();
    }

    /**
     * 设置MainView
     * 必须设置不然不能调用其他方法
     */
    public void setMain(View view) {
        if (view == null) {
            throw new IllegalArgumentException("MainView is null,fuck you");
        }
        this.mView = view;
    }

    /**
     * 添加 BSYEditText
     *
     * @param views 传入单个或者多个 BSYEditText
     */
    public void addViews(BSYEditText... views) {
        if (mView == null) {
            throw new IllegalArgumentException("MainView is null,fuck you");
        }
        if (views == null) {
            return;
        }

        if (mViewSet == null) {
            mViewSet = new ArrayList<>(views.length);
        }

        for (BSYEditText view : views) {
            // 避免重复添加
           mViewSet.remove(view);
           view.addTextChangedListener(this);
           mViewSet.add(view);
        }
        // 触发一次监听
        notifyChanged();
    }

    /**
     * 移除 BSYEditText 监听，避免内存泄露
     */
    public void removeViews(BSYEditText... views) {
        if (mView == null) {
            throw new IllegalArgumentException("MainView is null,fuck you");
        }
        if (mViewSet == null || mViewSet.isEmpty()) {
            return;
        }

        for (BSYEditText view : views) {
            view.removeTextChangedListener(this);
            mViewSet.remove(view);
        }
        // 触发一次监听
        notifyChanged();
    }

    /**
     * 移除所有 BSYEditText 监听，避免内存泄露
     */
    public void removeAllViews() {
        if (mView == null) {
            throw new IllegalArgumentException("MainView is null,fuck you");
        }
        if (mViewSet == null) {
            return;
        }

        for (TextView view : mViewSet) {
            view.removeTextChangedListener(this);
        }
        mViewSet.clear();
        mViewSet = null;
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onResume(owner);

        notifyChanged();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        removeAllViews();

        DefaultLifecycleObserver.super.onDestroy(owner);
    }

    /**
     * 设置输入监听
     */
    public void setListener(@Nullable BSYOnInputTextListener listener) {
        if (mView == null) {
            throw new IllegalArgumentException("MainView is null,fuck you");
        }
        mListener = listener;
    }

    /**
     * {@link TextWatcher}
     */

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (mView == null) {
            throw new IllegalArgumentException("MainView is null,fuck you");
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mView == null) {
            throw new IllegalArgumentException("MainView is null,fuck you");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mView == null) {
            throw new IllegalArgumentException("MainView is null,fuck you");
        }
        notifyChanged();
    }

    /**
     * 通知更新
     */
    public void notifyChanged() {
        if (mView == null) {
            throw new IllegalArgumentException("MainView is null,fuck you");
        }
        if (mViewSet == null) {
            return;
        }

        // 重新遍历所有的输入
        for (BSYEditText view : mViewSet) {
            if (!view.isRule()) {
                setEnabled(false);
                return;
            }
        }

        if (mListener != null) {
            setEnabled(mListener.onInputChange(this));
            return;
        }

        setEnabled(true);
    }

    /**
     * 设置 View 的事件
     *
     * @param enabled 启用或者禁用 View 的事件
     */
    public void setEnabled(boolean enabled) {
        if (mView == null) {
            throw new IllegalArgumentException("MainView is null,fuck you");
        }
        if (enabled == mView.isEnabled()) {
            return;
        }

        if (enabled) {
            //启用View的事件
            mView.setEnabled(true);
            if (mAlpha) {
                //设置不透明
                mView.setAlpha(1f);
            }
        } else {
            //禁用View的事件
            mView.setEnabled(false);
            if (mAlpha) {
                //设置半透明
                mView.setAlpha(0.5f);
            }
        }
    }

    /**
     * 文本变化监听器
     */
    public interface BSYOnInputTextListener {

        /**
         * 输入发生了变化
         *
         * @return 返回按钮的 Enabled 状态
         */
        default boolean onInputChange(BSYInputTextManager manager) {
            return true;
        }
    }

    public static class BSYEditText extends TextInputEditText {

        protected boolean mIsRule = false;
        protected String KEY_IS_RULE = "KEY_IS_RULE";
        protected String KEY_SUPER_DATA = "KEY_SUPER_DATA";

        public BSYEditText(Context context) {
            super(context);
        }

        public BSYEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public BSYEditText(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public boolean isRule() {
            return mIsRule;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BSYEditText that = (BSYEditText) o;
            return getId() == that.getId();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId());
        }

        @Override
        public Parcelable onSaveInstanceState() {
            Bundle bundle = new Bundle();
            Parcelable superData = super.onSaveInstanceState();
            bundle.putParcelable(KEY_SUPER_DATA, superData);
            bundle.putBoolean(KEY_IS_RULE, mIsRule);
            return bundle;
        }

        @Override
        public void onRestoreInstanceState(Parcelable state) {
            Bundle bundle = (Bundle) state;
            Parcelable superData = bundle.getParcelable(KEY_SUPER_DATA);
            mIsRule = bundle.getBoolean(KEY_IS_RULE);
            super.onRestoreInstanceState(superData);
        }
    }
}