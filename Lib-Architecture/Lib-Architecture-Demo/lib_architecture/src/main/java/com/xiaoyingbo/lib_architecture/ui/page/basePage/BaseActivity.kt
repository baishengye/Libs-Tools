package com.xiaoyingbo.lib_architecture.ui.page;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import com.xiaoyingbo.lib_architecture.R;
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateCallback;
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateManager;
import com.xiaoyingbo.lib_architecture.ui.scope.ViewModelScope;

/**
 * 技术基类*/
public abstract class BaseActivity<B extends ViewDataBinding> extends DataBindingActivity<B> {

    private final ViewModelScope mViewModelScope = new ViewModelScope();
    private NetworkStateManager mNetworkStateManager;

    /**获取网络变化的时候的回调*/
    protected abstract NetworkStateCallback getNetworkStateCallback();

    @Override
    protected void initViewModel() {
        mNetworkStateManager = NetworkStateManager.getInstance(getNetworkStateCallback());
        getLifecycle().addObserver(mNetworkStateManager);
    }

    protected void removeObserver(){
        getLifecycle().removeObserver(mNetworkStateManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        removeObserver();
    }

    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getActivityScopeViewModel(this, modelClass);
    }

    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getApplicationScopeViewModel(modelClass);
    }

    protected ViewGroup getContentView(){
        return (ViewGroup) getBinding().getRoot();
    }
}
