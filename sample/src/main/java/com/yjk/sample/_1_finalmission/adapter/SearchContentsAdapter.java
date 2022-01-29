package com.yjk.sample._1_finalmission.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yjk.sample._1_finalmission.roomdb.DataTable;
import com.yjk.sample.databinding.Activity1SearchItemBinding;

import java.util.List;

public class SearchContentsAdapter extends RecyclerView.Adapter<SearchContentsAdapter.SearchViewHolder> {
    static final String TAG = SearchViewHolder.class.toString();
    private Context mContext;
    private List<DataTable> mList;
    private OnItemClickCallback mCallback;

    public SearchContentsAdapter(Context context, List<DataTable> list){
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Activity1SearchItemBinding binding = Activity1SearchItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.binding.titleContents.setText(mList.get(position).Contents);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private Activity1SearchItemBinding binding;

        public SearchViewHolder(@NonNull Activity1SearchItemBinding b) {
            super(b.getRoot());
            this.binding = b;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    DataTable data = mList.get(position);
                    String str = data.Contents;
                }
            });
        }


    }
    public void setMyItemClickListener(OnItemClickCallback onItemClickCallback){
        this.mCallback = onItemClickCallback;
    }


    public interface OnItemClickCallback {
        public void onItem(String str);
    }
}























