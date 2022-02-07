package com.yjk.sample.final_mission.heart_list;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yjk.sample.R;
import com.yjk.sample.databinding.Activity1LikeListBinding;
import com.yjk.sample.final_mission.ActivityYouTubeMain;
import com.yjk.sample.final_mission.adapter.VodAdapter;
import com.yjk.sample.final_mission.datamodule.SearchData;
import com.yjk.sample.final_mission.player.ActivityPlayer;
import com.yjk.sample.final_mission.roomdb.ActivityDataBase;
import com.yjk.sample.final_mission.roomdb.DataDao;
import com.yjk.sample.final_mission.roomdb.DataTableVod;
import com.yjk.sample.final_mission.search.ActivitySearch;

import java.util.ArrayList;
import java.util.List;

/**
 * 좋아요 버튼을 통해 roomdb에 저장된 데이터를 가져와 화면에 띄웁니다.
 * 롱클릭시 아이템이 삭제되는 기능을 추가하였습니다.
 *
 */

public class ActivityMyList extends AppCompatActivity {
    private static final String TAG = "HaengSu";
    private Activity1LikeListBinding binding;
    private VodAdapter adapter;
    private List<DataTableVod> dList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1LikeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityDataBase db = ActivityDataBase.getAppDatabase(this);
        new FetchVod(db.dataDao()).execute();

    }


//=================== 데이터 가져와서 화면에 띄우기 ======================

    private class FetchVod extends AsyncTask<Void, Void, Void> {
        private DataDao dao;

        public FetchVod(DataDao dataDao) {
            this.dao = dataDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            dList = dao.getVodAll();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityMyList.this);
            binding.recyclerviewMain.setLayoutManager(linearLayoutManager);

            adapter = new VodAdapter(dList);
            adapter.setItemClickListener(new VodAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, SearchData data, int number) {

                    ArrayList<SearchData> sList = new ArrayList<>();
                    sList.add(data);

                    Intent i = new Intent(ActivityMyList.this, ActivityPlayer.class);
                    i.putExtra("data", sList);
                    startActivity(i);
                    overridePendingTransition(R.anim.vertical_enter, R.anim.none);
                }

                @Override
                public void onItemDelete(View v, DataTableVod dataTableVod) {

                    ActivityDataBase mDb = ActivityDataBase.getAppDatabase(ActivityMyList.this);
                    new DeleteVod(mDb.dataDao()).execute(dataTableVod);

                }
            });
            binding.recyclerviewMain.setAdapter(adapter);
            return null;
        }
    }

    public class DeleteVod extends AsyncTask<DataTableVod, Void, Void> {
        private DataDao dao;

        public DeleteVod(DataDao dataDao) {
            this.dao = dataDao;
        }

        @Override
        protected Void doInBackground(DataTableVod... dataTableVods) {
            dao.deleteVod(dataTableVods[0]);
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


































