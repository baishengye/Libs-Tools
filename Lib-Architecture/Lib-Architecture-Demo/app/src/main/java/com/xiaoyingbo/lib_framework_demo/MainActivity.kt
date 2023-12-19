package com.xiaoyingbo.lib_framework_demo

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateCallback
import com.xiaoyingbo.lib_architecture.ui.page.basePage.BaseActivity
import com.xiaoyingbo.lib_architecture.ui.page.dataBindingPage.DataBindingConfig
import com.xiaoyingbo.lib_architecture.ui.viewModel.dataBinding.StateHolder
import com.xiaoyingbo.lib_framework_demo.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

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

    fun <T> Flow<T>.observeWithLifecycle(
        activity: FragmentActivity,
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        collector: FlowCollector<T>
    ): Job = activity.lifecycleScope.launch {
        flowWithLifecycle(activity.lifecycle, minActiveState).collect(collector)
    }

    class MainUIStates:StateHolder(){}
}