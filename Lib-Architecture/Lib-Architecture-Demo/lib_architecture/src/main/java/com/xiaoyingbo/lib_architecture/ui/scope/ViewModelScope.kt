package com.xiaoyingbo.lib_architecture.ui.scope

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelScope {
    /**Fragment中用的viewModel提供者 */
    private var mFragmentProvider: ViewModelProvider? = null

    /**Activity中用的viewModel提供者 */
    private var mActivityProvider: ViewModelProvider? = null

    /**Application中用的viewModel提供者 */
    private var mApplicationProvider: ViewModelProvider? = null
    fun <T : ViewModel> getFragmentScopeViewModel(fragment: Fragment, modelClass: Class<T>): T {
        if (mFragmentProvider == null) mFragmentProvider = ViewModelProvider(fragment)
        return mFragmentProvider!!.get<T>(modelClass)
    }

    fun <T : ViewModel> getActivityScopeViewModel(
        activity: AppCompatActivity,
        modelClass: Class<T>
    ): T {
        if (mActivityProvider == null) mActivityProvider = ViewModelProvider(activity)
        return mActivityProvider!!.get<T>(modelClass)
    }

    fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (mApplicationProvider == null) mApplicationProvider = ViewModelProvider(
            ApplicationInstance.instance
        )
        return mApplicationProvider!!.get<T>(modelClass)
    }
}
