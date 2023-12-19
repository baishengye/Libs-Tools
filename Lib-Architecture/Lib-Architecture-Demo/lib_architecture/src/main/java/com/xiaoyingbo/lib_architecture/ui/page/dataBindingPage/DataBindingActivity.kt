package com.xiaoyingbo.lib_architecture.ui.page.dataBindingPage

import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * dataBinding基类 */
abstract class DataBindingActivity<VB : ViewDataBinding> : AppCompatActivity() {
    private var _binding : VB? = null
    protected val binding:VB by lazy { _binding!! }

    /**
     * 初始化要用到的viewModel */
    protected abstract fun initViewModel()
    protected abstract val dataBindingConfig: DataBindingConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        val dataBindingConfig = dataBindingConfig
        _binding = DataBindingUtil.setContentView<VB>(this, dataBindingConfig.layout)
        binding.lifecycleOwner = this
        binding.setVariable(dataBindingConfig.vmVariableId, dataBindingConfig.stateViewModel)
        val bindingParams = dataBindingConfig.bindingParams
        var i = 0
        val length = bindingParams.size()
        while (i < length) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i))
            i++
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding?.unbind()
        _binding = null
    }
}
