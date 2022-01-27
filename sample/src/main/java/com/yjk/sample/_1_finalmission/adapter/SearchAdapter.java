package com.yjk.sample._1_finalmission.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yjk.sample.R;
import com.yjk.sample._1_finalmission.datamodule.SearchData;
import com.yjk.sample.databinding.Activity1RecyclerviewItemBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.mViewHolder> {
    static final String TAG ="HAENG";

    ArrayList<SearchData> mList;
    Context context;
    Activity1RecyclerviewItemBinding binding;

    public SearchAdapter(Context context, ArrayList<SearchData> list) {
        this.context = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = Activity1RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new mViewHolder(binding);
    }
        //item_utube xml파일을 객체화 시킨다.
//        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.activity_1_recyclerview_item, parent, false);
//        return new mViewHolder(view);
//    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {

        //영상제목 셋팅
        holder.title.setText(mList.get(position).getTitle());

        //이미지를 넣어주기 위해 이미지url을 가져와서 썸에일 적용.
        String imageUrl = mList.get(position).getImageUrl();
        Glide.with(holder.imageView)
                .load(imageUrl)
                .into(holder.imageView);

//        //채널명 셋팅
//        binding.channel.setText(mList.get(position).getChannelId());
//
//        //조회수 셋팅
//        binding.views.setText(mList.get(position).getViewCount());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: mList.size = " + mList.size());
        return mList.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder{
        private TextView title,channel,views;
        private ImageView imageView;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            channel = itemView.findViewById(R.id.channel);
            views = itemView.findViewById(R.id.views);
            imageView = itemView.findViewById(R.id.titleImage);

//            //유튜브 영상을 클릭하면 재생이 되는 액티비티로 이동
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position=getAdapterPosition();
//                    Intent intent = new Intent(context, UtubePlay.class);
//                    intent.putExtra("id", mList.get(position).getVideoId());
//                    context.startActivity(intent);
//                }
//            });

        }
    }
}


