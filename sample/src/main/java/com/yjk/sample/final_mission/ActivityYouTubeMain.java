package com.yjk.sample.final_mission;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.internal.v;
import com.yjk.sample.R;
import com.yjk.sample.final_mission.adapter.VodAdapter;
import com.yjk.sample.final_mission.datamodule.SearchData;
import com.yjk.sample.final_mission.heart_list.ActivityMyList;
import com.yjk.sample.final_mission.player.ActivityPlayer;
import com.yjk.sample.final_mission.roomdb.ActivityDataBase;
import com.yjk.sample.final_mission.roomdb.DataDao;
import com.yjk.sample.final_mission.roomdb.DataTable;
import com.yjk.sample.final_mission.roomdb.DataTableVod;
import com.yjk.sample.final_mission.search.ActivitySearch;
import com.yjk.sample.databinding.Activity1MainBinding;

import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActivityYouTubeMain extends YouTubeBaseActivity {
    private static final String TAG = "HAENG";
    private final String API_KEY = "AIzaSyAXV8MZt-Vn15KgIonqEzlx9KIs_AteSxs";

    private Activity1MainBinding binding;
    private Context mContext;

    private String originUrl;
    private String oldTitle;
    private String vodId = "";

    private ArrayList<SearchData> mList;
    private VodAdapter adapter;
    private List<DataTableVod> dList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mContext = this;
        mList = new ArrayList<>();
        ActivityDataBase db = ActivityDataBase.getAppDatabase(ActivityYouTubeMain.this);

        searchVod searchVod = new searchVod();
        searchVod.execute();

        adapter = new VodAdapter(mContext, mList);

        adapter.setItemClickListener(new VodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, SearchData data, int n) {

                switch (n) {
                    case 1:
                        ArrayList<SearchData> sList = new ArrayList<>();
                        sList.add(data);

                        Intent i = new Intent(mContext, ActivityPlayer.class);
                        i.putExtra("data", sList);
                        startActivity(i);
                        overridePendingTransition(R.anim.vertical_enter, R.anim.none);
                        break;

                    case 2:
                        DataTableVod table = new DataTableVod();
                        table.vodId = data.getVideoId();
                        table.title = data.getTitle();
                        table.uri = data.getImageUrl();
                        table.channelId = data.getChannelId();
                        table.like = data.isLike();
                        Log.d(TAG, "onItemClick: like =" + data.isLike());
                        Log.d(TAG, "onItemClick: table.like = " + table.like);
                        Log.d(TAG, "onItemClick: title = " + data.getTitle());

                        new saveVod(db.dataDao()).execute(table);
                        break;
                }
            }
        });
    }
//=================================아이탬 저장 비동기 ===========================================

    private class saveVod extends AsyncTask<DataTableVod, Void, Void> {
        private DataDao dao;

        public saveVod(DataDao dataDao) {
            this.dao = dataDao;

        }

        @Override
        protected Void doInBackground(DataTableVod... dataTableVods) {
            dao.insertVod(dataTableVods[0]);

            dList = dao.getVodAll();

            return null;
        }
    }

//===============================첫 화면 영상 검색 비동기 =======================================

    private class searchVod extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                JSONObject jsonObject = getUtube();
                parsingJsonData(jsonObject);
            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityYouTubeMain.this);
            binding.recyclerviewMain.setLayoutManager(linearLayoutManager);
            binding.recyclerviewMain.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    public JSONObject getUtube() throws IOException {

        originUrl = "https://www.googleapis.com/youtube/v3/search?"
                + "part=snippet&q= 팝송"
                + "&key=" + API_KEY + "&maxResults=" + 50;

        URL url = new URL(originUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.connect();

        String line;
        String result = "";
        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer response = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        result = response.toString();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void parsingJsonData(JSONObject jsonObject) throws JSONException {
        JSONArray contacts = jsonObject.getJSONArray("items");

        for (int i = 0; i < contacts.length(); i++) {
            JSONObject o = contacts.getJSONObject(i);
            String kind = o.getJSONObject("id").getString("kind");

            if (kind.equals("youtube#video")) {
                vodId = o.getJSONObject("id").getString("videoId");
            } else {
                vodId = o.getJSONObject("id").getString("playlistId");
            }
            String title = o.getJSONObject("snippet").getString("title");
            String changeT = stringToHtmlSign(title);

            String channelId = o.getJSONObject("snippet").getString("channelTitle");

            String imageUrl = o.getJSONObject("snippet").getJSONObject("thumbnails")
                    .getJSONObject("high").getString("url");

//            //조회수
//
//            String parentKey = jsonObject.getString("items");
//            parentKey = parentKey.substring(1,parentKey.length() - 1);
//            JSONObject parentKeyJson = new JSONObject(parentKey);
//            String statistics = parentKeyJson.getString("statistics");
//            JSONObject statisticsJson = new JSONObject(statistics);
//            Iterator iterator = statisticsJson.keys();
//
//            while (iterator.hasNext()) {
//                String key = iterator.next().toString();
//                if (key.contains("viewCount")) {
//                    viewCount = statisticsJson.getLong(key);
//                    break;
//                }
//            }


            mList.add(new SearchData(vodId, changeT, imageUrl, channelId));
        }
    }

    private String stringToHtmlSign(String str) {
        return str.replaceAll("&amp;", "&")
                .replaceAll("[<]", "&lt;")
                .replaceAll("[>]", "&gt;")
                .replaceAll("&quot;", "'")
                .replaceAll("&#39;", "'");
    }

    public void moveSearch(View view) {
        Intent intent = new Intent(this, ActivitySearch.class);
        startActivity(intent);
        finish();
    }

    public void moveList(View view) {
        Intent intent = new Intent(this, ActivityMyList.class);
        startActivity(intent);
        finish();
    }
}

