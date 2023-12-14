package com.xiaoyingbo.lib_architecture.ui.scope

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**app单例，单例是依附于进程的，也就是说ApplicationInstance产生的ViewModelStore是对于整个进程的,<br></br>
 * 也就是说app不死，这个产生的viewModel就持续有效 */
class ApplicationInstance private constructor() : ViewModelStoreOwner {
    private var mAppViewModelStore: ViewModelStore? = null
    override fun getViewModelStore(): ViewModelStore {
        if (mAppViewModelStore == null) mAppViewModelStore = ViewModelStore()
        return mAppViewModelStore!!
    }

    companion object {
        val instance = ApplicationInstance()
    }
}