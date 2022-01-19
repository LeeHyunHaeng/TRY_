package com.yjk.sample._2_header.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yjk.sample.R;
import com.yjk.sample._2_header.datamodule.Header_Data;

import java.util.ArrayList;

public class Header_Adapter extends RecyclerView.Adapter<Header_Adapter.MyViewHolder> {
    private Context mContex;
    private ArrayList<Header_Data> mList;

    public Header_Adapter(Context context, ArrayList<Header_Data> alist){
        this.mContex =context;
        this.mList = alist;
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

        holder.tv_date.setText(data.getDate());
        holder.tv_context.setText(data.getContents());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date,tv_context;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_context = itemView.findViewById(R.id.tv_contents);
        }
    }
}
