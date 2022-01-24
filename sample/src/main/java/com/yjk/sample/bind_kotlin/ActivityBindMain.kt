package com.yjk.sample.bind_kotlin

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.ContentView
import com.yjk.common.view.base.BaseActivity
import com.yjk.sample.databinding.ActivitybindingmainKotlinBinding

class ActivityBindMain : BaseActivity() {

    private lateinit var mBinding : ActivitybindingmainKotlinBinding


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        mBinding = ActivitybindingmainKotlinBinding.inflate(layoutInflater);


        initView()
        setEvent()
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun setEvent() {
        TODO("Not yet implemented")
    }
}