package com.xiaoyingbo.lib_architecture.ui.page.dataBindingPage

import android.util.SparseArray
import androidx.lifecycle.ViewModel

class DataBindingConfig(
    @JvmField val layout: Int,
    @JvmField val vmVariableId: Int,
    @JvmField val stateViewModel: ViewModel
) {
    @JvmField
    val bindingParams = SparseArray<Any>()

    fun addBindingParam(
        variableId: Int,
        any: Any
    ): DataBindingConfig {
        if (bindingParams[variableId] == null) {
            bindingParams.put(variableId, any)
        }
        return this
    }
}
