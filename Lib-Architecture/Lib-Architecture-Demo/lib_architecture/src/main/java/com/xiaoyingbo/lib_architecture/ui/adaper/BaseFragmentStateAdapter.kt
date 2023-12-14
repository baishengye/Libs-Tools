package com.xiaoyingbo.lib_architecture.ui.adaper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**ViewPager2状态适配器的基类 */
abstract class BaseFragmentStateAdapter<T> : FragmentStateAdapter {
    protected var datum: List<T>

    constructor(fragmentActivity: FragmentActivity, datum: List<T>) : super(fragmentActivity) {
        this.datum = datum
    }

    constructor(fragment: Fragment, datum: List<T>) : super(fragment) {
        this.datum = datum
    }

    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle, datum: List<T>) : super(
        fragmentManager,
        lifecycle
    ) {
        this.datum = datum
    }

    override fun getItemCount(): Int {
        return datum.size
    }
}
