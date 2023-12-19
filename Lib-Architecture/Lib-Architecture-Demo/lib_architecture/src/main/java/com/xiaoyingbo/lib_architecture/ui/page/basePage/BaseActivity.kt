package com.xiaoyingbo.lib_architecture.ui.page.basePage

import android.content.pm.ApplicationInfo
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateCallback
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateManager
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateManager.Companion.getInstance
import com.xiaoyingbo.lib_architecture.ui.page.dataBindingPage.DataBindingActivity
import com.xiaoyingbo.lib_architecture.ui.scope.ViewModelScope

/**
 * 技术基类 */
abstract class BaseActivity<VB : ViewDataBinding> : DataBindingActivity<VB>() {
    private val mViewModelScope = ViewModelScope()
    private var mNetworkStateManager: NetworkStateManager? = null
    protected abstract val networkStateCallback: NetworkStateCallback?

    private val contentView: ViewGroup
        get() = binding.root as ViewGroup

    private val isDebug: Boolean
        get() = applicationContext.applicationInfo != null &&
                applicationContext.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0

    override fun initViewModel() {
        networkStateCallback?.let {
            mNetworkStateManager = NetworkStateManager.getInstance(it).apply {
                lifecycle.addObserver(this@apply)
            }
        }
    }

    protected fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        return mViewModelScope.getActivityScopeViewModel(this, modelClass)
    }

    protected fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        return mViewModelScope.getApplicationScopeViewModel(modelClass)
    }
}
