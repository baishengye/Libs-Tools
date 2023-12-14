package com.baishengye.lib_bluetooth.bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.baishengye.lib_bluetooth.bt.interfaces.BTStateCallback;

public class BTStateReceiver extends BroadcastReceiver {
    private final BTStateCallback mBTStateCallback;
    private final BluetoothAdapter mBluetoothAdapter;

    public BTStateReceiver(BTStateCallback callback, BluetoothAdapter adapter) {
        mBTStateCallback = callback;
        mBluetoothAdapter = adapter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
            actionStateChanged(intent);
        }else if(BluetoothDevice.ACTION_FOUND.equals(action)){
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        }
    }

    private void actionStateChanged(Intent intent) {
        int btState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,BluetoothAdapter.ERROR);
        switch (btState){
            case BluetoothAdapter.STATE_ON:{
                mBTStateCallback.onBTEnableChange(false);
                break;
            }
            case BluetoothAdapter.STATE_OFF:{
                mBTStateCallback.onBTEnableChange(true);
                break;
            }
            case BluetoothAdapter.STATE_TURNING_ON:
            case BluetoothAdapter.STATE_TURNING_OFF:
            default:{
                break;
            }
        }
    }
}
