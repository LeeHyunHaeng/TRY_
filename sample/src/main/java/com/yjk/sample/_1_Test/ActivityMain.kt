package com.yjk.sample._1_Test

import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.yjk.sample.R
import com.yjk.sample._1_Test.datamodule.Data_Kt
import com.yjk.sample._1_Test.dialog.CustomDialog_Kt

class ActivityMain : AppCompatActivity(){
    val TAG = "Hg"
    private lateinit var bt_dialog : RelativeLayout
    private var mList  = ArrayList<Data_Kt>()
    private lateinit var adapter


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setContentView(R.layout._1_activity_main)

        initView()
        setEvent()
    }


    private fun initView() {
        bt_dialog = findViewById<RelativeLayout>(R.id.relayout_main)
    }

    private fun setEvent() {
        bt_dialog.setOnClickListener {
            val dlg = CustomDialog_Kt(this)

            dlg.show()

           dlg.setDialogCallbackListener { title : String, context : String ->
           val data = Data_Kt(title,context)
               Log.d(TAG, "setEvent: ${data}")
               mList.add(data)
           }
        }
    }
}