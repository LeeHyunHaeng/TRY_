package com.yjk.sample.final_mission.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yjk.sample.databinding.Activity1RecyclerviewItemBinding;
import com.yjk.sample.final_mission.ActivityYouTubeMain;
import com.yjk.sample.final_mission.datamodule.SearchData;
import com.yjk.sample.final_mission.player.ActivityPlayer;
import com.yjk.sample.final_mission.roomdb.ActivityDataBase;
import com.yjk.sample.final_mission.roomdb.DataTableVod;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class VodAdapter extends RecyclerView.Adapter<VodAdapter.mViewHolder> {
    static final String TAG = "HAENG";

    private ArrayList<SearchData> mList;
    private List<DataTableVod> dList;
    private Context context;
    private OnItemLongCallback mCallback;
    private OnItemClickListener clickListener;
    private SearchData data;
    private ActivityDataBase db;

    public VodAdapter(Context context, ArrayList<SearchData> list) {
        this.context = context;
        this.mList = list;
        this.data = new SearchData();
    }

    public VodAdapter(List<DataTableVod> dlist) {
        this.dList = dlist;
    }

    public interface OnItemLongCallback {
        void onItemDelete(View v, int position);
    }

    public interface OnItemClickListener {
        void onItemClick(View v, SearchData searchData, int number);
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.clickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Activity1RecyclerviewItemBinding binding = Activity1RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new mViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {

        //좋아요 영상 띄우기
//        if (dList.get(position).like) {
        if (dList != null) {
            holder.binding.tvTitle.setText(dList.get(position).title);

            String likeUrl = dList.get(position).uri;
            Glide.with(holder.binding.titleImage)
                    .load(likeUrl)
                    .into(holder.binding.titleImage);

            holder.binding.tvChannel.setText(dList.get(position).channelId);

        } else {

            //영상제목 셋팅
            holder.binding.tvTitle.setText(mList.get(position).getTitle());

            //이미지를 넣어주기 위해 이미지url을 가져와서 썸에일 적용.
            String imageUrl = mList.get(position).getImageUrl();
            Glide.with(holder.binding.titleImage)
                    .load(imageUrl)
                    .into(holder.binding.titleImage);

            holder.binding.tvChannel.setText(mList.get(position).getChannelId());
        }

        holder.bind(mList.get(holder.getAbsoluteAdapterPosition()), clickListener);

    }

    @Override
    public int getItemCount() {

        if (dList != null) {
            return dList.size();
        } else {
            return mList.size();
        }
    }

    public class mViewHolder extends RecyclerView.ViewHolder {
        private Activity1RecyclerviewItemBinding binding;
        private long mLastClickTime = 0;

        public mViewHolder(@NonNull Activity1RecyclerviewItemBinding b) {
            super(b.getRoot());
            this.binding = b;


            //좋아요 동영상 롱클릭시 삭제
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    DataTableVod data = new DataTableVod();
                    int position = getAbsoluteAdapterPosition();
                    Toast.makeText(binding.getRoot().getContext(), getAbsoluteAdapterPosition() + "번째 영상이 삭제되었습니다!", Toast.LENGTH_SHORT).show();
                    mCallback.onItemDelete(view, position);

                    dList.remove(position);
                    notifyDataSetChanged();
                    return true;
                }
            });
        }

        public void bind(SearchData data, OnItemClickListener callback) {

//            영상 누르면 플레이어로 재생 = 1번
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime >= 1000) {
                        int position = getAbsoluteAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            callback.onItemClick(binding.getRoot(), data, 1);
                        }
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                }
            });

            //좋아요 클릭시 데이터 보냄 = 2번
            binding.heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // false -> true 변경
                    data.setLike(!data.isLike());


                    if (data.isLike()) {
                        binding.heart.setSelected(!binding.heart.isSelected());
                    }

                    callback.onItemClick(binding.getRoot(), data, 2);
                }
            });
        }
    }
}


























