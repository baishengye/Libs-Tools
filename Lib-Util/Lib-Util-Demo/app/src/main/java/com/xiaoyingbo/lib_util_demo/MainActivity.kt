package com.xiaoyingbo.lib_util_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xiaoyingbo.lib_util_demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val tag = this::class.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}