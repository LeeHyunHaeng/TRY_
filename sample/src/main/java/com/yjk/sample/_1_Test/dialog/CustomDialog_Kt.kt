package com.yjk.sample._1_Test.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import com.yjk.sample.R

class CustomDialog_Kt(context : Context) {
    private val dlg = Dialog(context)
    private lateinit var callback : DialogCallback
    private lateinit var et_title: EditText
    private lateinit var et_contents : EditText
    private lateinit var okButton : Button
    private lateinit var cancelButton: Button
    private lateinit var title : String
    private lateinit var content : String

    fun show(){
        //다이얼로그 설정
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout._1_activity_dialog)
        dlg.setCancelable(false)                                    // 다이얼로그 영역 밖에 터치시 dialog close 방지

        //위젯 초기화
        et_title = dlg.findViewById(R.id.et_title)
        et_contents = dlg.findViewById(R.id.et_contents)
        okButton = dlg.findViewById(R.id.bt_positive)
        cancelButton = dlg.findViewById(R.id.bt_negative)

        //입력 값 전달
        title = et_title.text.toString()
        content = et_contents.text.toString()

        //콜백으로 저장           (여기서 check,cancel 버튼으로 when 문을 통해 작성할 수 있는지 확인해볼 것)
        okButton.setOnClickListener{
            callback.onOKClicked(title,content)
            dlg.dismiss()
        }

        //'취소' 클릭시 다이얼로그 종료
        cancelButton.setOnClickListener{
            dlg.dismiss()
        }

        dlg.show()
    }

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