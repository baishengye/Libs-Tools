package com.baishengye.lib_bluetooth_demo

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.baishengye.lib_bluetooth.bt.BTManager
import com.baishengye.lib_bluetooth.bt.interfaces.BTStateCallback
import com.baishengye.lib_bluetooth.bt.interfaces.BTSupportCallback
import com.baishengye.lib_bluetooth_demo.databinding.ActivityMainBinding
import com.baishengye.lib_bluetooth_demo.databinding.ActivitySecondBinding

class SecondActivity : CommonActivity() {
    private lateinit var mBinding: ActivitySecondBinding
    private lateinit var mBTManager: BTManager

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBTManager = BTManager.getInstance(object :
            BTSupportCallback {
            override fun onBTSupport(isSupport: Boolean) {
                super.onBTSupport(isSupport)
                Toast.makeText(this@SecondActivity,if(isSupport) "设备支持蓝牙功能" else "设备不支持蓝牙功能", Toast.LENGTH_SHORT).show()
            }
        })

        mBTManager.setBTStateCallback(object:
            BTStateCallback {
            override fun onBTEnableChange(isEnable: Boolean) {
                super.onBTEnableChange(isEnable)
            }
        })

        lifecycle.addObserver(mBTManager)

        val adapter:ArrayAdapter<String> = ArrayAdapter(this, androidx.appcompat.R.layout.select_dialog_item_material)
        mBinding.lv.adapter = adapter

        mBinding.btn1.setOnClickListener {
            val bondedDevices = mBTManager.bondedDevices
            adapter.clear()
            for (device in bondedDevices){
                adapter.add(device.name)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        lifecycle.removeObserver(mBTManager)
    }
}