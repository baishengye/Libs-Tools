package com.xiaoyingbo.lib_architecture.ui.viewModel.mvi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

object FlowExtensions {
    @JvmStatic
    fun <T> Flow<T>.observeWithLifecycle(
        activity: FragmentActivity,
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        collector: FlowCollector<T>
    ): Job = activity.lifecycleScope.launch {
        flowWithLifecycle(activity.lifecycle, minActiveState).collect(collector)
    }

    @JvmStatic
    fun <T> Flow<T>.observeWithLifecycle(
        fragment: Fragment,
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        collector: FlowCollector<T>
    ): Job = fragment.lifecycleScope.launch {
        flowWithLifecycle(fragment.viewLifecycleOwner.lifecycle, minActiveState).collect(collector)
    }
}