package com.yjk.sample.final_mission.player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.yjk.sample.R;
import com.yjk.sample.databinding.Activity1PlayerBinding;
import com.yjk.sample.final_mission.datamodule.SearchData;

import java.util.ArrayList;

public class ActivityPlayer  extends AppCompatActivity {
    private static final String TAG = ActivityPlayer.class.toString();

    private Activity1PlayerBinding binding;
    private String vodid,title,uri,channelid;
    private Context mContext;
    private ArrayList<SearchData> sList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1PlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mContext = this;

        getData();
        startPlayer();
    }

    private void getData() {
        Intent i = getIntent();
        sList = (ArrayList<SearchData>) i.getSerializableExtra("data");
        vodid = sList.get(0).getVideoId();

        //data setting
        binding.title.setText(sList.get(0).getTitle());
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.none,R.anim.vertical_exit);
    }

        //유튜브 플레이어 실행
    public void startPlayer() {
        getLifecycle().addObserver(binding.uPlayer);

        binding.uPlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                youTubePlayer.loadVideo(vodid,0);
            }
        });
    }

}



































