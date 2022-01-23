package com.yjk.sample._0_root.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yjk.sample.R;
import com.yjk.sample._0_root.datamodel.DataActivity;
import com.yjk.sample.databinding.RecycelerviewItemsBinding;

import java.util.ArrayList;

public class AdapterActivity extends RecyclerView.Adapter<AdapterActivity.ViewHolder> {
    private ArrayList<DataActivity> mList;
    private Context mContext;
    private OnItemDeleteListener deleteListener;


    public AdapterActivity(Context context, ArrayList<DataActivity> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycelerview_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DataActivity data = mList.get(position);

        try {
            holder.binding.tvTitle.setText(data.getTitle());
            holder.binding.tvContents.setText(data.getContents());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

//        private TextView tv_title, tv_contents;
        private RecycelerviewItemsBinding binding;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            this.tv_title = itemView.findViewById(R.id.tv_title);
//            this.tv_contents = itemView.findViewById(R.id.tv_contents);

            //여기서 bind 함수는 내부적으로 findViewById 를 수행한다.
            binding = RecycelerviewItemsBinding.bind(itemView);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        if(deleteListener !=null){
                            deleteListener.onItemDelete(view,position);
                        }
                    }
                    return false;
                }
            });
        }
    }

    public interface OnItemDeleteListener {
        void onItemDelete(View view, int position);
    }

    public void setOnItemDeleteListener(OnItemDeleteListener listener){
        this.deleteListener = listener;
    }
}


