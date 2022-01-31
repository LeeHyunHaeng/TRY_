package com.yjk.sample._1_final_mission.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yjk.sample._1_final_mission.roomdb.DataTable;
import com.yjk.sample.databinding.Activity1SearchItemBinding;

import java.util.List;

public class SearchContentsAdapter extends RecyclerView.Adapter<SearchContentsAdapter.SearchViewHolder> {
    static final String TAG = SearchViewHolder.class.toString();
    private Context mContext;
    private List<DataTable> dList;
    private OnItemClickCallback mCallback;

    public interface OnItemClickCallback {
        public void onItem(String str);
    }

    public SearchContentsAdapter(Context context, List<DataTable> list,OnItemClickCallback call){
        this.mContext = context;
        this.dList = list;
        this.mCallback = call;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Activity1SearchItemBinding binding = Activity1SearchItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.binding.titleContents.setText(dList.get(position).Contents);
    }

    @Override
    public int getItemCount() {
        return dList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private Activity1SearchItemBinding binding;
        private String str;

        public SearchViewHolder(@NonNull Activity1SearchItemBinding b) {
            super(b.getRoot());
            this.binding = b;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAbsoluteAdapterPosition();
                    DataTable data = dList.get(pos);
                    str = data.Contents;
                    mCallback.onItem(str);
                }
            });
        }
    }
}























