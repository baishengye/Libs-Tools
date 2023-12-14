package com.baishengye.lib_bluetooth.bt;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.baishengye.lib_bluetooth.bt.interfaces.BTStateCallback;
import com.baishengye.lib_bluetooth.bt.interfaces.BTSupportCallback;
import com.baishengye.lib_bluetooth.utils.BSYUtils;

import java.util.Set;

public class BTManager implements DefaultLifecycleObserver {

    private BTStateReceiver mBTStateReceiver;
    private final BTSupportCallback mBTSupportCallback;
    private BTStateCallback mBTStateCallback;

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;

    /**构造函数不让外界直接调用*/
    private BTManager(BTSupportCallback callback) {
        mBTSupportCallback = callback;
    }

    /**虽然式getInstance()但是不用单例，因为单例是static的,也就是一个进程只会有一个实例
     * <br></br>但是我们需要确保我们在不同的界面时可以有不同的回调,所以不能使用单例获取*/
    public static BTManager getInstance(BTSupportCallback callback) {
        return new BTManager(callback);
    }

    public void setBTStateCallback(BTStateCallback callback) {
        mBTStateCallback = callback;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        mBluetoothManager = BSYUtils.getApp().getApplicationContext().getSystemService(BluetoothManager.class);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if (isSupportBt()) {
            mBTStateReceiver = new BTStateReceiver(mBTStateCallback, mBluetoothAdapter);
            mBTSupportCallback.onBTSupport(true);
        } else {
            mBTSupportCallback.onBTSupport(false);
        }
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        if (isSupportBt()) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            BSYUtils.getApp().registerReceiver(mBTStateReceiver, filter);
            mBTStateCallback.onBTEnableChange(isBTEnable());
        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        if (isSupportBt()) {
            BSYUtils.getApp().unregisterReceiver(mBTStateReceiver);
        }
    }

    /**
     * 设备是否支持蓝牙*/
    public boolean isSupportBt() {
        return mBluetoothAdapter != null;
    }

    /**控制蓝牙开关*/
    public void controlBt(boolean toEnable) {
        if (!isSupportBt()) {
            mBTSupportCallback.onBTSupport(false);
            return;
        }
        if (mBluetoothAdapter.isEnabled() != toEnable) {
            if (ContextCompat.checkSelfPermission(BSYUtils.getApp(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (toEnable) {
                mBluetoothAdapter.enable();
            } else {
                mBluetoothAdapter.disable();
            }
        }
    }

    /**
     * 蓝牙是否打开*/
    public boolean isBTEnable() {
        if (!isSupportBt()) {
            mBTSupportCallback.onBTSupport(false);
            return false;
        }
        return mBluetoothAdapter.isEnabled();
    }

    /**
     * 获取配对过的设备*/
    public Set<BluetoothDevice> getBondedDevices() {
        if (!isSupportBt()) {
            mBTSupportCallback.onBTSupport(false);
            return null;
        }
        if (ActivityCompat.checkSelfPermission(BSYUtils.getApp(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        return mBluetoothAdapter.getBondedDevices();
    }


}
