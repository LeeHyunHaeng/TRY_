package com.yjk.sample.final_mission.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yjk.sample.final_mission.datamodule.SearchData;
import com.yjk.sample.final_mission.roomdb.DataTable;
import com.yjk.sample.databinding.Activity1SearchItemBinding;

import java.util.List;

/**
 * 이 어댑터는 검색기록을 저장하고 보여주기 위한 어댑터입니다.
 *
 */

public class SearchContentsAdapter extends RecyclerView.Adapter<SearchContentsAdapter.SearchViewHolder> {
    static final String TAG = SearchViewHolder.class.toString();
    private Context mContext;
    private List<DataTable> dList;
    private OnItemClickCallback mCallback;

    public interface OnItemClickCallback {
        void onItem(View view,String str);
    }

    public void setOnItemClickListener(OnItemClickCallback onItemClickListener){
        this.mCallback = onItemClickListener;
    }

    public SearchContentsAdapter(Context context, List<DataTable> list){
        this.mContext = context;
        this.dList = list;
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

        SearchData data = new SearchData();
        data.setSearchHistory(dList.get(position).Contents);
        holder.bind(mCallback,data);

    }

    @Override
    public int getItemCount() {
        return dList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private Activity1SearchItemBinding binding;

        public SearchViewHolder(@NonNull Activity1SearchItemBinding b) {
            super(b.getRoot());
            this.binding = b;
        }

        public void bind(OnItemClickCallback callback, SearchData data){
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAbsoluteAdapterPosition();
                    String str = dList.get(pos).Contents;
                    Log.d(TAG, "onClick: str = " + str.toString());
                    Log.d(TAG, "onClick: callback = "+ callback);

                    callback.onItem(binding.getRoot(),str);
                }
            });
        }
    }
}























