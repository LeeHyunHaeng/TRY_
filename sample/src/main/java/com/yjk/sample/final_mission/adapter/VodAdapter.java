package com.yjk.sample.final_mission.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yjk.sample.final_mission.datamodule.SearchData;
import com.yjk.sample.final_mission.heart_list.ActivityMyList;
import com.yjk.sample.final_mission.player.ActivityPlayer;
import com.yjk.sample.databinding.Activity1RecyclerviewItemBinding;
import com.yjk.sample.final_mission.roomdb.DataTable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class VodAdapter extends RecyclerView.Adapter<VodAdapter.mViewHolder> {
    static final String TAG ="HAENG";

    private ArrayList<SearchData> mList;
    private List<DataTable> dList;
    private Context context;
    private OnItemLongCallback mCallback;

    public VodAdapter(Context context, ArrayList<SearchData> list) {
        this.context = context;
        this.mList = list;
    }

    public VodAdapter(List<DataTable> dlist){
        this.dList = dlist;
    }

    public interface OnItemLongCallback {
        void onItemDelete(View v, int position);
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Activity1RecyclerviewItemBinding binding = Activity1RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new mViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {

        //좋아요 영상 띄우기
        if (dList !=null) {
            holder.binding.title.setText(dList.get(position).title);

            String likeUrl = dList.get(position).uri;
            Glide.with(holder.binding.titleImage)
                    .load(likeUrl)
                    .into(holder.binding.titleImage);

            holder.binding.channel.setText(dList.get(position).channelId);
            holder.binding.heart.setVisibility(View.GONE);

        } else {

        //영상제목 셋팅
        holder.binding.title.setText(mList.get(position).getTitle());

        //이미지를 넣어주기 위해 이미지url을 가져와서 썸에일 적용.
        String imageUrl = mList.get(position).getImageUrl();
        Glide.with(holder.binding.titleImage)
                .load(imageUrl)
                .into(holder.binding.titleImage);

        holder.binding.channel.setText(mList.get(position).getVideoId());

        //ChannelId
        holder.binding.views.setText(mList.get(position).getChannelId());
        }

    }

    @Override
    public int getItemCount() {

        if (dList != null){
            return dList.size();
        } else {
            return mList.size();
        }
    }

    public class mViewHolder extends RecyclerView.ViewHolder{
        private Activity1RecyclerviewItemBinding binding;

        public mViewHolder(@NonNull Activity1RecyclerviewItemBinding b) {
            super(b.getRoot());
            this.binding = b;

            //유튜브 영상을 클릭하면 재생이 되는 액티비티로 이동
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    Intent i = new Intent(binding.getRoot().getContext(), ActivityPlayer.class);
                    i.putExtra("id", mList.get(position).getVideoId());
                    binding.getRoot().getContext().startActivity(i);
                }
            });

            //영상 좋아요 누를때 데이터 전달
            binding.heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    Intent i = new Intent(binding.getRoot().getContext(), ActivityMyList.class);
                    i.putExtra("id", mList.get(position).getVideoId());
                    i.putExtra("title",mList.get(position).getTitle());
                    i.putExtra("uri", mList.get(position).getImageUrl());
                    i.putExtra("channelId",mList.get(position).getChannelId());

                    binding.getRoot().getContext().startActivity(i);
                }
            });

            //좋아요 동영상 롱클릭시 삭제
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    dList.remove(position);
                    return true;
                }
            });

        }
    }
}


























