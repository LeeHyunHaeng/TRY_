package com.yjk.sample._1_Test

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yjk.sample.R
import com.yjk.sample._1_Test.adapter.Adapter_kt
import com.yjk.sample._1_Test.datamodule.Data_Kt
import com.yjk.sample._1_Test.dialog.CustomDialog_Kt

class ActivityMain : AppCompatActivity(){
    val TAG = "Hg"
    private lateinit var bt_dialog : RelativeLayout
    private var mList  = ArrayList<Data_Kt>()
    private lateinit var recyclerview : RecyclerView
    private lateinit var adapter_kt : Adapter_kt
    private lateinit var context : Context


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setContentView(R.layout._1_activity_main)

        initView()
        setEvent()
    }


    private fun initView() {
        bt_dialog = findViewById<RelativeLayout>(R.id.relayout_main)
        recyclerview = findViewById(R.id.kotlin_recyclerview)
        adapter_kt = Adapter_kt(this,mList)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter_kt


    }

    private fun setEvent() {
        // 다이얼로그 실행
        bt_dialog.setOnClickListener {
            val dlg = CustomDialog_Kt(this)

            dlg.show()

           dlg.setDialogCallbackListener { title : String, context : String ->
           val data = Data_Kt(title,context)
               mList.add(data)
               adapter_kt.notifyItemChanged(mList.size)
           }
        }

        //아이템 삭제
        adapter_kt.setOnDeleteListener(object : Adapter_kt.OnDeleteCallback{
            override fun DeleteItem(v: View, position: Int) {
                mList.removeAt(position)
                adapter_kt.notifyDataSetChanged()
            }
        })
    }
}