package com.xiaoyingbo.lib_architecture.data.response.networkState;

import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.xiaoyingbo.lib_architecture.Utils.AppUtils;


public class NetworkStateManager implements DefaultLifecycleObserver{

    private final NetworkStateReceiver mNetworkStateReceive;

    /**构造函数不让外界直接调用*/
    private NetworkStateManager(NetworkStateCallback callback) {
        mNetworkStateReceive=new NetworkStateReceiver(callback);
    }

    /**虽然式getInstance()但是不用单例，因为单例是static的,也就是一个进程只会有一个实例
     * <br></br>但是我们需要确保我们在不同的界面时可以有不同的回调,所以不能使用单例获取*/
    public static NetworkStateManager getInstance(NetworkStateCallback callback) {
        return new NetworkStateManager(callback);
    }

    //tip：让 NetworkStateManager 可观察页面生命周期，
    // 从而在页面失去焦点时，
    // 及时断开本页面对网络状态的监测，以避免重复回调和一系列不可预期的问题。

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        AppUtils.getApp().getApplicationContext().registerReceiver(mNetworkStateReceive, filter);
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        AppUtils.getApp().getApplicationContext().unregisterReceiver(mNetworkStateReceive);
    }
}
