package com.yjk.atry._102_http

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yjk.atry.databinding.ActivityStage102MainBinding
import com.yjk.common.view.base.BaseActivity

class ActivityHttp : BaseActivity() {

    lateinit var binding : ActivityStage102MainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setEvent()

        loadList()
    }

    override fun initView() {

        binding = ActivityStage102MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewUserList.layoutManager = LinearLayoutManager(mContext)
    }

    override fun setEvent() {
        TODO("Not yet implemented")
    }

    fun loadList(){

    }

}