package com.xiaoyingbo.lib_framework_demo

import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateCallback
import com.xiaoyingbo.lib_architecture.ui.page.basePage.BaseActivity
import com.xiaoyingbo.lib_architecture.ui.page.dataBindingPage.DataBindingConfig
import com.xiaoyingbo.lib_architecture.ui.viewModel.dataBinding.StateHolder
import com.xiaoyingbo.lib_architecture.ui.viewModel.mvi.FlowExtensions.observeWithLifecycle
import com.xiaoyingbo.lib_framework_demo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val networkStateCallback: NetworkStateCallback
        get() = object : NetworkStateCallback{
            override fun networkStateChange(isConnected: Boolean) {
                super.networkStateChange(isConnected)
            }
        }
    override val dataBindingConfig: DataBindingConfig
        get() = DataBindingConfig(R.layout.activity_main,BR.vm,mMainUIStates)

    private lateinit var mMainUIStates: MainUIStates
    private lateinit var mMainRequester:MainRequester

    override fun initViewModel() {
        super.initViewModel()

        mMainUIStates = getActivityScopeViewModel(MainUIStates::class.java)
        mMainRequester = getActivityScopeViewModel(MainRequester::class.java)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        mMainRequester.state.observeWithLifecycle(this){
            when(it){
                is MainRequester.MainMState.LoadingMState->{
                    binding.IVFemale.setImageResource(R.drawable.ic_launcher_foreground)
                }
                is MainRequester.MainMState.FemaleMState->{
                    Glide
                        .with(this)
                        .load(it.female?.data?.first()?.imageUrl ?:"")
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.IVFemale)
                }
            }
        }

        mMainRequester.effect.observeWithLifecycle(this){
            when(it){
                is MainRequester.MainMEffect.ToastMState->{
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.IVFemale.setOnClickListener {
            mMainRequester.sendAction(MainRequester.MainIAction.GetFemaleIAction)
        }
    }

    class MainUIStates:StateHolder(){}
}