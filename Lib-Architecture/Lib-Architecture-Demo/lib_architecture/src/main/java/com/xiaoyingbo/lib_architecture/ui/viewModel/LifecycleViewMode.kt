package com.xiaoyingbo.lib_architecture.ui.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Lifecycling
import androidx.lifecycle.ViewModel
import java.io.Closeable

open class LifecycleViewModel() : ViewModel(),LifecycleOwner{

    @SuppressLint("StaticFieldLeak")//可能会内存泄露
    private  val mLifecycleRegistry = LifecycleRegistry(this)//此处警告是防止父类在子类还没初始化就调用子类方法，不调用就不会有问题

    init {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onCleared() {
        super.onCleared()

        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }
}