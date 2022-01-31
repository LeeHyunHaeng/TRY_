package com.yjk.sample.final_mission.heart_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.yjk.sample.databinding.Activity1MainBinding;
import com.yjk.sample.final_mission.ActivityYouTubeMain;
import com.yjk.sample.final_mission.adapter.VodAdapter;
import com.yjk.sample.final_mission.roomdb.ActivityDataBase;
import com.yjk.sample.final_mission.roomdb.DataTable;

import java.util.List;

public class ActivityMyList extends AppCompatActivity {
    private static final String TAG = ActivityMyList.class.toString();
    private Activity1MainBinding binding;
    private String vodid,title,uri,channelid;
    private VodAdapter adapter;
    private ActivityDataBase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.list.setVisibility(View.GONE);

        db = Room.databaseBuilder(this, ActivityDataBase.class,"contents").allowMainThreadQueries().build();
        sendProfile();

        initView();
    }

    private void initView() {
        Intent i = getIntent();
        vodid = i.getStringExtra("id");
        uri = i.getStringExtra("uri");
        title = i.getStringExtra("title");
        channelid = i.getStringExtra("channelId");

        addVodProfile();
    }


    public void addVodProfile() {
        DataTable data = new DataTable();
        data.vodId = vodid;
        data.title = title;
        data.uri = uri;
        data.channelId = channelid;

        db.dataDao().insert(data);

        sendProfile();
    }

    public void deleteProfile(){

    }


    public void sendProfile() {
        List<DataTable> dList = db.dataDao().getAll();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityMyList.this);
        binding.recyclerviewMain.setLayoutManager(linearLayoutManager);

        adapter = new VodAdapter(dList);
        binding.recyclerviewMain.setAdapter(adapter);
    }

    public void moveMain(View view) {
        Intent intent = new Intent(this, ActivityYouTubeMain.class);
        startActivity(intent);

    }

}


































