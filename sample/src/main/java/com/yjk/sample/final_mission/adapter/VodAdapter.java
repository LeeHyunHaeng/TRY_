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
/**
 * 이 어댑터는 주로 영상읠 띄우고 삭제,좋아요,버튼 기능등을 사용하기위한 용도입니다.
 *
 *
 * dList는 주로 roomdb에 이용하는 list이고
 * mList는 주로 영상정보를 가져와 화면에 띄울수있게 하는 용도로 사용했습니다.
 */
public class VodAdapter extends RecyclerView.Adapter<VodAdapter.mViewHolder> {
    static final String TAG = VodAdapter.class.toString();

    private ArrayList<SearchData> mList;
    private List<DataTableVod> dList;
    private Context context;
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

    public interface OnItemClickListener {
        void onItemClick(View v, SearchData searchData, int number);
        void onItemDelete(View v, DataTableVod dataTableVod);
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
        if (dList != null) {
            SearchData data = new SearchData();

            holder.binding.tvTitle.setText(dList.get(position).title);

            String likeUrl = dList.get(position).uri;
            Glide.with(holder.binding.titleImage)
                    .load(likeUrl)
                    .into(holder.binding.titleImage);

            holder.binding.tvChannel.setText(dList.get(position).channelId);
            holder.bind(data, dList.get(holder.getAbsoluteAdapterPosition()), clickListener);

            holder.binding.heart.setVisibility(View.INVISIBLE);

        } else {  //http 통신으로 가져온 영상 띄우기

            DataTableVod data = new DataTableVod();

            //영상제목 셋팅
            holder.binding.tvTitle.setText(mList.get(position).getTitle());

            //이미지를 넣어주기 위해 이미지url을 가져와서 썸에일 적용.
            String imageUrl = mList.get(position).getImageUrl();
            Glide.with(holder.binding.titleImage)
                    .load(imageUrl)
                    .into(holder.binding.titleImage);

            holder.binding.tvChannel.setText(mList.get(position).getChannelId());
            holder.bind(mList.get(holder.getAbsoluteAdapterPosition()), data, clickListener);
        }
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
        }

        public void bind(SearchData data, DataTableVod dataTableVod, OnItemClickListener callback) {

            if (dList == null) {

//                영상 누르면 플레이어로 재생 = 1번
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SystemClock.elapsedRealtime() - mLastClickTime >= 1000) {
                            int position = getAbsoluteAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                callback.onItemClick(binding.getRoot(), data,1);
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
            } else {

//                좋아요리스트에서 클릭시 플레이어로 재생
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SystemClock.elapsedRealtime() - mLastClickTime >= 1000) {
                            int position = getAbsoluteAdapterPosition();

                            SearchData sData = new SearchData();
                            sData.setTitle(dList.get(position).title);
                            sData.setVideoId(dList.get(position).vodId);

                            if (position != RecyclerView.NO_POSITION) {
                                callback.onItemClick(binding.getRoot(), sData,3);
                            }
                        }
                        mLastClickTime = SystemClock.elapsedRealtime();
                    }
                });

//좋아요 동영상 롱클릭시 삭제
                binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        DataTableVod data = new DataTableVod();
                        int position = getAbsoluteAdapterPosition();
                        Toast.makeText(binding.getRoot().getContext(), getAbsoluteAdapterPosition()
                                + "번째 영상이 삭제되었습니다!", Toast.LENGTH_SHORT).show();

                        callback.onItemDelete(view, dataTableVod);

                        dList.remove(position);
                        notifyDataSetChanged();
                        return true;
                    }
                });

            }
        }
    }
}

























