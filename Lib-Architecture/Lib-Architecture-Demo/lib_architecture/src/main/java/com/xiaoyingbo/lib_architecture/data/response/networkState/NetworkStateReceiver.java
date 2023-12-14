package com.xiaoyingbo.lib_architecture.data.response.networkState;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.xiaoyingbo.lib_architecture.Utils.NetworkUtils;

import java.util.Objects;

public class NetworkStateReceiver extends BroadcastReceiver {

    private final NetworkStateCallback callback;

    public NetworkStateReceiver(NetworkStateCallback callback){
        this.callback=callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {
            callback.networkStateChange(NetworkUtils.isConnected());
        }
    }
}
