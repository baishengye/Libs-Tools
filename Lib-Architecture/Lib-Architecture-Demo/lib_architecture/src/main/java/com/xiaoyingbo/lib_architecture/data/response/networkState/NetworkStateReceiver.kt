package com.xiaoyingbo.lib_architecture.data.response.networkState

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.xiaoyingbo.lib_architecture.Utils.NetworkUtils

class NetworkStateReceiver(private val callback: NetworkStateCallback) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            callback.networkStateChange(NetworkUtils.isConnected())
        }
    }
}
