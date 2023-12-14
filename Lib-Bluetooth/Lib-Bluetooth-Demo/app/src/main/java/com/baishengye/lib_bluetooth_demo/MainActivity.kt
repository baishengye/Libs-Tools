package com.baishengye.lib_bluetooth_demo

import android.Manifest
import android.content.Intent
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
import com.baishengye.lib_bluetooth_demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btn1.setOnClickListener {
            startActivity(Intent(this,FirstActivity::class.java));
        }

        mBinding.btn2.setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java));
        }

    }
}