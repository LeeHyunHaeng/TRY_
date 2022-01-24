package com.yjk.sample.bind_kotlin

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.ContentView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yjk.common.view.base.BaseActivity
import com.yjk.sample.R
import com.yjk.sample.databinding.ActivitybindingmainKotlinBinding

class ActivityBindMain : AppCompatActivity() {
    private lateinit var binding : ActivitybindingmainKotlinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activitybindingmain_kotlin)
        binding.dataProfile = DataProfile("이행수","2살", 0)
    }

}




