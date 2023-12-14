package com.xiaoyingbo.lib_architecture.ui.page.dataBindingPage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class DataBindingDialogFragment<VB : ViewDataBinding> : DialogFragment() {
    /**此Fragment所依附的Activity */
    private var _activity: AppCompatActivity? = null
    protected val activity:AppCompatActivity = _activity!!

    private var _binding: VB? = null
    protected val binding:VB = _binding!!

    /**当activity和fragment建立联系时调用 */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        _activity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * 初始化要用到的viewModel */
    protected abstract fun initViewModel()
    protected abstract val dataBindingConfig: DataBindingConfig
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        val dataBindingConfig = dataBindingConfig
        _binding = DataBindingUtil.inflate<VB>(inflater, dataBindingConfig.layout, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(dataBindingConfig.vmVariableId, dataBindingConfig.stateViewModel)
        val bindingParams = dataBindingConfig.bindingParams
        var i = 0
        val length = bindingParams.size()
        while (i < length) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i))
            i++
        }
        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.unbind()
        _binding = null
    }
}
