package com.xiaoyingbo.lib_widget_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xiaoyingbo.lib_widget_demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bind:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind=ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)


    }
}