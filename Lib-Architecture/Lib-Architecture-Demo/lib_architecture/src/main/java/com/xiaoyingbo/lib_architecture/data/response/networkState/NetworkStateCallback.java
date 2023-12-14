package com.xiaoyingbo.lib_architecture.data.response.networkState;

/**网络状态变化回调*/
public interface NetworkStateCallback {
    default void networkStateChange(boolean isConnected){}
}
