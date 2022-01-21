package com.yjk.sample._1_Test.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.yjk.sample.R

class CustomDialog_Kt(var context : Context) {
    val TAG = "HAENG"
    private val dlg = Dialog(context)
    private lateinit var callback : DialogCallback
    private lateinit var et_title: EditText
    private lateinit var et_contents : EditText
    private lateinit var okButton : Button
    private lateinit var cancelButton: Button

    fun show(){
        //다이얼로그 설정
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout._1_activity_dialog)

        // 다이얼로그 영역 밖에 터치시 dialog close 방지
        dlg.setCancelable(false)

        //위젯 초기화
        et_title = dlg.findViewById(R.id.et_title_kt)
        et_contents = dlg.findViewById(R.id.et_contents_kt)
        okButton = dlg.findViewById(R.id.bt_positive)
        cancelButton = dlg.findViewById(R.id.bt_negative)


        //콜백으로 저장 및 공백 체크
        okButton.setOnClickListener{
            if ((et_title.text.toString()).trim().isEmpty() || (et_contents.text.toString()).trim().isEmpty()){
                Toast.makeText(context,"title과 contents를 모두 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                callback.onOKClicked(et_title.text.toString(),et_contents.text.toString())
            }
            dlg.dismiss()
        }

        //'취소' 클릭시 다이얼로그 종료
        cancelButton.setOnClickListener{
            dlg.dismiss()
        }

        dlg.show()
    }


//    fun setDialogListener(callback: DialogCallback){
//        this.callback = callback
//    }

    fun setDialogCallbackListener(callback : (String,String) -> Unit){
        this.callback = object : DialogCallback {

            override fun onOKClicked(title: String, context: String) {
                callback(title,context)

            }
        }
    }

    interface DialogCallback {
        fun onOKClicked(title : String, context : String)
    }


}