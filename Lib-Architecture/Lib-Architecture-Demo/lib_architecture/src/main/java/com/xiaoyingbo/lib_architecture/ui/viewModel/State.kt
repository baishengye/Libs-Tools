package com.xiaoyingbo.lib_architecture.ui.viewModel

import androidx.databinding.ObservableField

/**防抖动状态类
 * ObservableField是只有数据发生变化的时候才会触发
 */
class State<T> @JvmOverloads constructor(
    value: T,
    /**是否消除抖动 */
    private val mIsDebouncing: Boolean = false
) : ObservableField<T>(value) {
    override fun set(value: T) {
        val isUnchanged = get() === value
        super.set(value)
        if (!mIsDebouncing && isUnchanged) {
            notifyChange()
        }
    }
}
