package com.yjk.sample._1_final_mission.player;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yjk.sample._1_final_mission.collect_data.ActivityVodId;

public class ActivityPlayer  extends AppCompatActivity {
    private static final String TAG = ActivityPlayer.class.toString();
    private Intent i;
    private String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }
    public void init() {
        i = getIntent();
        id = i.getStringExtra("id");
        Log.d(TAG, "init: id = " + id);


    }
}




































