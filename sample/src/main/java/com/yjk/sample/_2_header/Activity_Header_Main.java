package com.yjk.sample._2_header;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yjk.common.view.base.BaseActivity;
import com.yjk.sample.R;
import com.yjk.sample._2_header.adapter.Header_Adapter;
import com.yjk.sample._2_header.customdialog.Activity_Header_CustomDialog;
import com.yjk.sample._2_header.datamodule.Header_Data;

import java.util.ArrayList;

public class Activity_Header_Main extends BaseActivity {
    static final String TAG = Activity_Header_Main.class.toString();

    private RelativeLayout showdialog;
    private Activity_Header_CustomDialog dialog;
    private Activity_Header_CustomDialog.dateCallback mCallback;
    private ArrayList<Header_Data> mList;
    private RecyclerView recyclerView;
    private Header_Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_main);
        initView();
        setEvent();
    }

    @Override
    protected void initView() {
        showdialog = findViewById(R.id.relativelayout_calendar);
        mList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview_calender);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new Header_Adapter(mContext,mList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setEvent() {
        showdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Activity_Header_CustomDialog(mContext);
                dialog.showDialog();
                dialog.setCallbackListener(new Activity_Header_CustomDialog.dateCallback() {
                    @Override
                    public void getDate(String date, String context) {
                        Header_Data data = new Header_Data(date,context);
                        if (data != null){
                            Log.d(TAG, "getDate: data : " + data);
                            mList.add(data);
                        }
                    }
                });



            }
        });
    }



}
