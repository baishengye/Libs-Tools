package com.xiaoyingbo.lib_architecture.data.response.networkState

/**网络状态变化回调 */
interface NetworkStateCallback {
    fun networkStateChange(isConnected: Boolean) {}
}
