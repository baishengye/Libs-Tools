package com.baishengye.lib_bluetooth_demo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.baishengye.lib_bluetooth.bt.BTManager
import com.baishengye.lib_bluetooth.bt.interfaces.BTStateCallback
import com.baishengye.lib_bluetooth.bt.interfaces.BTSupportCallback
import com.baishengye.lib_bluetooth_demo.databinding.ActivityFirstBinding
import com.baishengye.lib_bluetooth_demo.databinding.ActivityMainBinding

class FirstActivity : CommonActivity() {
    private lateinit var mBinding: ActivityFirstBinding
    private lateinit var mBTManager:BTManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBTManager = BTManager.getInstance(object :
            BTSupportCallback {
            override fun onBTSupport(isSupport: Boolean) {
                super.onBTSupport(isSupport)
                Toast.makeText(this@FirstActivity,if(isSupport) "设备支持蓝牙功能" else "设备不支持蓝牙功能", Toast.LENGTH_SHORT).show()
            }
        })
        mBTManager.setBTStateCallback(object:
            BTStateCallback {
            override fun onBTEnableChange(isEnable: Boolean) {
                super.onBTEnableChange(isEnable)

                mBinding.btnStateBt.text = if(isEnable)"开" else "关"
                mBinding.btnControlBt.text = if(isEnable) "关闭蓝牙" else "打开蓝牙"
            }
        })

        lifecycle.addObserver(mBTManager)

        initListeners()
    }

    override fun onDestroy() {
        super.onDestroy()

        lifecycle.removeObserver(mBTManager)
    }

    private fun initListeners() {
        mBinding.btnControlBt.setOnClickListener {
            mBTManager.controlBt(!mBTManager.isBTEnable)
        }
    }
}