package com.xiaoyingbo.lib_architecture.ui.page.basePage

import android.content.pm.ApplicationInfo
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateCallback
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateManager
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateManager.Companion.getInstance
import com.xiaoyingbo.lib_architecture.ui.page.dataBindingPage.DataBindingFragment
import com.xiaoyingbo.lib_architecture.ui.scope.ViewModelScope

abstract class BaseFragment<B : ViewDataBinding> : DataBindingFragment<B>() {
    private val mViewModelScope = ViewModelScope()
    private var mNetworkStateManager: NetworkStateManager? = null
    protected abstract val networkStateCallback: NetworkStateCallback?

    private val contentView: ViewGroup
        get() = binding.root as ViewGroup

    private val isDebug: Boolean
        get() = activity.applicationContext.applicationInfo != null &&
                activity.applicationContext.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0

    override fun initViewModel() {
        mNetworkStateManager = getInstance(networkStateCallback!!)
        viewLifecycleOwner.lifecycle.addObserver(mNetworkStateManager!!)
    }

    override fun onDestroyView() {
        removeObserver()
        super.onDestroyView()
    }

    protected fun removeObserver() {
        viewLifecycleOwner.lifecycle.removeObserver(mNetworkStateManager!!)
    }

    protected fun nav(): NavController {
        return NavHostFragment.findNavController(this)
    }

    protected fun <T : ViewModel> getFragmentScopeViewModel(modelClass: Class<T>): T {
        return mViewModelScope.getFragmentScopeViewModel(this, modelClass)
    }

    protected fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        return mViewModelScope.getActivityScopeViewModel(activity, modelClass)
    }

    protected fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        return mViewModelScope.getApplicationScopeViewModel(modelClass)
    }
}
