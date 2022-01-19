package com.yjk.sample._2_header.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yjk.sample.R;
import com.yjk.sample._2_header.datamodule.Header_Data;

import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Header_Adapter extends RecyclerView.Adapter<Header_Adapter.MyViewHolder> {
    static final String TAG = Header_Adapter.class.toString();
    private Context mContex;
    private ArrayList<Header_Data> mList;

    public Header_Adapter(Context context, ArrayList<Header_Data> alist){
        this.mContex =context;
        this.mList = alist;
        Collections.sort(mList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_header_items,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Header_Data data = mList.get(position);

        //mList에 저장되어 있는날짜데이터를 파씽으로 특정서식(SimpleDateFormat)으로 만들고 다시 포맷팅으로 내가 원하는 서식(String)으로 바꾼다.
        try {
            Date date = (new SimpleDateFormat("yyyy.MM.dd")).parse(data.getDate());
            String dateResult = (new SimpleDateFormat("yyyy.MM")).format(date);
            String dayResult = (new SimpleDateFormat("dd")).format(date);

            holder.tv_date.setText(dateResult);
            holder.tv_day.setText(dayResult);

            boolean isTop;
            //position = 1 이라고 가정하고 적용할때, 두번째 아이템이고 첫번째와 비교해야 하니깐 0번째 데이터를 불러와서 파싱과 포맷싱을 똑같이 해주고 그걸 date 와 비교해서 같으면 true 와 다르면 false 를 isTop 에 입힌다.
            if (position != 0){
                Date bDate = (new SimpleDateFormat("yyyy.MM.dd")).parse((mList.get(position-1).getDate()));
                String beforeDate = (new SimpleDateFormat("yyyy.MM")).format(bDate);
                Log.d(TAG, "onBindViewHolder:  dateResult = " + dateResult);
                Log.d(TAG, "onBindViewHolder: beforeDate = "+beforeDate);

                isTop = dateResult.equals(beforeDate);
            } else{
                isTop = false;
            }

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.root.getLayoutParams();
            if (isTop){
                holder.layout_title.setVisibility(View.GONE);
                params.topMargin = 0;
            } else {
                holder.layout_title.setVisibility(View.VISIBLE);
                params.topMargin = 30;
            }

            holder.tv_context.setText(data.getContents());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date,tv_context,tv_day;
        private LinearLayout layout_title,root;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            root = (LinearLayout) itemView.findViewById(R.id.Header_item_root);
            layout_title = (LinearLayout) itemView.findViewById(R.id.linear_title);
            tv_date = itemView.findViewById(R.id.tv_title);
            tv_context = itemView.findViewById(R.id.tv_contents);
            tv_day = itemView.findViewById(R.id.tv_day);
        }
    }


}
