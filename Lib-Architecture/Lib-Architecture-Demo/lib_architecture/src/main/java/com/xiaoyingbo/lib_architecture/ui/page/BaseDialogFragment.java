package com.xiaoyingbo.lib_architecture.ui.page;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateCallback;
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateManager;
import com.xiaoyingbo.lib_architecture.ui.scope.ViewModelScope;

public abstract class BaseDialogFragment<B extends ViewDataBinding> extends DataBindingDialogFragment<B>{
    private final ViewModelScope mViewModelScope = new ViewModelScope();

    private NetworkStateManager mNetworkStateManager;

    /**获取网络变化的时候的回调*/
    protected abstract NetworkStateCallback getNetworkStateCallback();

    @Override
    protected void initViewModel() {
        mNetworkStateManager = NetworkStateManager.getInstance(getNetworkStateCallback());
        getViewLifecycleOwner().getLifecycle().addObserver(mNetworkStateManager);
    }

    protected void removeObserver() {
        getViewLifecycleOwner().getLifecycle().removeObserver(mNetworkStateManager);
    }

    /**获取Fragment作用域下的ViewModel*/
    protected <T extends ViewModel> T getFragmentScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getFragmentScopeViewModel(this, modelClass);
    }
    /**获取Activity作用域下的ViewModel*/
    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getActivityScopeViewModel(mActivity, modelClass);
    }
    /**获取Application作用域下的ViewModel*/
    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getApplicationScopeViewModel(modelClass);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeObserver();
    }

    protected NavController nav() {
        return NavHostFragment.findNavController(this);
    }

    protected ViewGroup getContentView(){
        return (ViewGroup) getBinding().getRoot();
    }
}
