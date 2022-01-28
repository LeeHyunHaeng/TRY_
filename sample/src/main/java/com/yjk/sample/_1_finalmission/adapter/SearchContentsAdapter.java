package com.yjk.sample._1_finalmission.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yjk.sample._1_finalmission.roomdb.DataTable;
import com.yjk.sample.databinding.Activity1SearchItemBinding;

import java.util.List;

public class SearchContentsAdapter extends RecyclerView.Adapter<SearchContentsAdapter.SearchViewHolder> {
    private Context mContext;
    private List<DataTable> mList;

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
            binding = b;
        }
    }
}
