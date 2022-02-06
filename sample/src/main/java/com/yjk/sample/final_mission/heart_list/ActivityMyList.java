package com.yjk.sample.final_mission.heart_list;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.yjk.sample.final_mission.roomdb.DataDao;
import com.yjk.sample.final_mission.roomdb.DataTable;
import com.yjk.sample.final_mission.roomdb.DataTableVod;
import com.yjk.sample.final_mission.search.ActivitySearch;

import java.util.List;

public class ActivityMyList extends AppCompatActivity {
    private static final String TAG = "HaengSu";
    private Activity1LikeListBinding binding;
    private String vodid, title, uri, channelid;
    private VodAdapter adapter;
    private ActivityDataBase db;
    private Intent intent;
    private DataTableVod dataVod;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1LikeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityDataBase db = ActivityDataBase.getAppDatabase(this);
        new fetchVod(db.dataDao()).execute();
    }


    private class fetchVod extends AsyncTask<Void, Void, Void> {
        private DataDao dao;

        public fetchVod(DataDao dataDao) {
            this.dao = dataDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<DataTableVod> dList = dao.getVodAll();
//            Log.d(TAG, "doInBackground: like = " + dList.get(0).like);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityMyList.this);
            binding.recyclerviewMain.setLayoutManager(linearLayoutManager);

            adapter = new VodAdapter(dList);
            binding.recyclerviewMain.setAdapter(adapter);
            return null;
        }
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


































