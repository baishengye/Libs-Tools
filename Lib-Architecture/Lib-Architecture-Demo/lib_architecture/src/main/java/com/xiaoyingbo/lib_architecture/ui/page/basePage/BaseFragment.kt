package com.xiaoyingbo.lib_architecture.ui.page;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateCallback;
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateManager;
import com.xiaoyingbo.lib_architecture.ui.scope.ViewModelScope;

public abstract class BaseFragment<B extends ViewDataBinding> extends DataBindingFragment<B>{
    private final ViewModelScope mViewModelScope = new ViewModelScope();

    private NetworkStateManager mNetworkStateManager;

    /**获取网络变化的时候的回调*/
    protected abstract NetworkStateCallback getNetworkStateCallback();

    @Override
    protected void initViewModel() {
        mNetworkStateManager = NetworkStateManager.getInstance(getNetworkStateCallback());
        getViewLifecycleOwner().getLifecycle().addObserver(mNetworkStateManager);
    }

    @Override
    public void onDestroyView() {
        removeObserver();

        super.onDestroyView();
    }

    protected void removeObserver(){
        getViewLifecycleOwner().getLifecycle().removeObserver(mNetworkStateManager);
    }

    protected <T extends ViewModel> T getFragmentScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getFragmentScopeViewModel(this, modelClass);
    }

    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getActivityScopeViewModel(mActivity, modelClass);
    }

    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getApplicationScopeViewModel(modelClass);
    }

    protected ViewGroup getContentView(){
        return (ViewGroup) getBinding().getRoot();
    }

    protected NavController nav() {
        return NavHostFragment.findNavController(this);
    }
}
