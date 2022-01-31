package com.yjk.sample.final_mission.heart_list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.yjk.sample.databinding.Activity1LikeListBinding;
import com.yjk.sample.databinding.Activity1MainBinding;
import com.yjk.sample.final_mission.ActivityYouTubeMain;
import com.yjk.sample.final_mission.adapter.VodAdapter;
import com.yjk.sample.final_mission.datamodule.SearchData;
import com.yjk.sample.final_mission.roomdb.ActivityDataBase;
import com.yjk.sample.final_mission.roomdb.DataTable;
import com.yjk.sample.final_mission.roomdb.DataTableVod;
import com.yjk.sample.final_mission.search.ActivitySearch;

import java.util.List;

public class ActivityMyList extends AppCompatActivity {
    private static final String TAG = ActivityMyList.class.toString();
    private Activity1LikeListBinding binding;
    private String vodid,title,uri,channelid;
    private VodAdapter adapter;
    private ActivityDataBase db;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1LikeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = Room.databaseBuilder(this, ActivityDataBase.class,"contents").allowMainThreadQueries().build();

        intent = getIntent();
        vodid = intent.getStringExtra("id");
        if (vodid != null){
            initView();
        }
        sendProfile();
    }

    private void initView() {
        uri = intent.getStringExtra("uri");
        title = intent.getStringExtra("title");
        channelid = intent.getStringExtra("channelId");

        addVodProfile();
    }

    public void addVodProfile() {
        DataTableVod data = new DataTableVod();
        data.vodId = vodid;
        data.title = title;
        data.uri = uri;
        data.channelId = channelid;

        db.dataDao().insertVod(data);

        sendProfile();
    }

    public void deleteProfile(){


    }


    public void sendProfile() {
        List<DataTableVod> dList = db.dataDao().getVodAll();
        SearchData mData = new SearchData();

        mData.setLike(!mData.isLike());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityMyList.this);
        binding.recyclerviewMain.setLayoutManager(linearLayoutManager);

        adapter = new VodAdapter(dList,mData);
        binding.recyclerviewMain.setAdapter(adapter);
    }



    public void moveMain(View view) {
        Intent intent = new Intent(this, ActivityYouTubeMain.class);
        startActivity(intent);
    }
    public void moveSearch(View view) {
        Intent intent = new Intent(this, ActivitySearch.class);
        startActivity(intent);
        finish();
    }

}


































