package com.yjk.sample._2_header.customdialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yjk.sample.R;

import java.util.ArrayList;
import java.util.Calendar;

public class Activity_Header_CustomDialog {
    private Context mContext;
    private RelativeLayout relativeLayout;
    private TextView tv_date;
    private EditText ed_contents;
    private Button cancel,check;
    private Calendar calendar;
    private dateCallback mCallback;


    public Activity_Header_CustomDialog(Context context){
        this.mContext = context;
        this.calendar = Calendar.getInstance();
    }

    //다이얼로그 호출 함수
    public void showDialog(){
        int mYear= calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);


        //커스텀 다이얼로그를 정의하기 위해 Dialog 생성
        Dialog dlg = new Dialog(mContext);

        //액티비티의 타이틀바를 숨긴다.(선택사항)
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //커스텀다이얼로그 레이아웃 설정
        dlg.setContentView(R.layout.activity_header_dialog);

        //커스텀다이얼로그 노출
        dlg.show();


        //커스텀 다이얼로그 각 위젯들 정의
        relativeLayout = dlg.findViewById(R.id.relative_calendar);
        ed_contents = dlg.findViewById(R.id.et_contents);
        cancel = dlg.findViewById(R.id.bt_negative);
        check = dlg.findViewById(R.id.bt_positive);
        tv_date = dlg.findViewById(R.id.tv_date);

        Calendar c = Calendar.getInstance();

        DatePickerDialog dDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            tv_date.setText(year + "." + (month+1) + "." + day);

            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String date = tv_date.getText().toString();
                    String context = ed_contents.getText().toString();
                    mCallback.getDate(date,context);

                    dlg.dismiss();
                }
            });
            }
        },mYear,mMonth,mDay);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dDialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }

    public interface dateCallback{
        void getDate(String date,String context);
    }

    public void setCallbackListener(dateCallback callback){
        this.mCallback = callback;
    }
}
