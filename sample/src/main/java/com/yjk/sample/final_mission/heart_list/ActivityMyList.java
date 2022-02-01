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
    private static final String TAG = "HaengSu";
    private Activity1LikeListBinding binding;
    private String vodid,title,uri,channelid;
    private VodAdapter adapter;
    private ActivityDataBase db;
    private Intent intent;
    private DataTableVod dataVod;

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
        dataVod = new DataTableVod();
        dataVod.vodId = vodid;
        dataVod.title = title;
        dataVod.uri = uri;
        dataVod.channelId = channelid;

        db.dataDao().insertVod(dataVod);

        sendProfile();
    }


    public void sendProfile() {
        List<DataTableVod> dList = db.dataDao().getVodAll();
        SearchData mData = new SearchData();

        mData.setLike(!mData.isLike());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityMyList.this);
        binding.recyclerviewMain.setLayoutManager(linearLayoutManager);

        adapter = new VodAdapter(dList, mData, new VodAdapter.OnItemLongCallback() {
            @Override
            public void onItemDelete(View v, int position) {
               dataVod = dList.get(position);
               db.dataDao().deleteVod(dataVod);
            }
        });
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


































