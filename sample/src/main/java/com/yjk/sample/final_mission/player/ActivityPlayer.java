package com.yjk.sample.final_mission.player;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.yjk.sample.R;
import com.yjk.sample.databinding.Activity1PlayerBinding;

public class ActivityPlayer  extends AppCompatActivity {
    private static final String TAG = ActivityPlayer.class.toString();

    private Activity1PlayerBinding binding;
    private String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1PlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent i = getIntent();
        id = i.getStringExtra("id");
        Log.d(TAG, "init: id = " + id);

        startPlayer();
    }

    //유튜브 플레이어 실행
    public void startPlayer() {
        getLifecycle().addObserver(binding.uPlayer);

        binding.uPlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                youTubePlayer.loadVideo(id,0);
            }
        });
    }

}



































