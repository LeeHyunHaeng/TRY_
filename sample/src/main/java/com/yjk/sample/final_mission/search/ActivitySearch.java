package com.yjk.sample.final_mission.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.yjk.sample.R;
import com.yjk.sample.final_mission.ActivityYouTubeMain;
import com.yjk.sample.final_mission.adapter.SearchContentsAdapter;
import com.yjk.sample.final_mission.adapter.VodAdapter;
import com.yjk.sample.final_mission.datamodule.SearchData;
import com.yjk.sample.final_mission.player.ActivityPlayer;
import com.yjk.sample.final_mission.roomdb.ActivityDataBase;
import com.yjk.sample.final_mission.roomdb.DataDao;
import com.yjk.sample.final_mission.roomdb.DataTable;
import com.yjk.sample.databinding.Activity1SerchMainBinding;
import com.yjk.sample.final_mission.roomdb.DataTableVod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 검색화면을 위한 activity이며, 검색기록을 room에 저장하고 roomdb에 있는 검색기록을 가져옵니다.
 *
 */

public class ActivitySearch extends YouTubeBaseActivity {
    private static final String TAG = ActivitySearch.class.toString();
    private final String API_KEY = "AIzaSyAXV8MZt-Vn15KgIonqEzlx9KIs_AteSxs";

    private Activity1SerchMainBinding binding;
    private Context mContext;

    private String originUrl;
    private String oldTitle;
    private String vodId = "";

    private VodAdapter vAdapter;
    private SearchContentsAdapter cAdapter;
    private ArrayList<SearchData> mList;
    private List<DataTable> dList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1SerchMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mList = new ArrayList<>();

        fetch();

//        검색창에서 엔터 누르면 바로 search클래스 실행
        binding.search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {

                    startSearch();
                    addTitle();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.search.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });


//        검색화면 전환
        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.layoutMain.setVisibility(View.VISIBLE);
                binding.layoutVod.setVisibility(View.GONE);
            }
        });
    }

    //검색 시작
    public void startSearch() {
        ActivitySearch.SearchTask searchTask = new ActivitySearch.SearchTask();
        searchTask.execute();
        binding.layoutMain.setVisibility(View.GONE);
        binding.layoutVod.setVisibility(View.VISIBLE);
    }

    public void addTitle() {

        oldTitle = binding.search.getText().toString();
        Log.d(TAG, "addTitle: oldTitle = " + oldTitle);

        DataTable dataTable = new DataTable();
        dataTable.Contents = oldTitle;
        Log.d(TAG, "addTitle: dataTable.Contents = " + dataTable.Contents);

        ActivityDataBase db = ActivityDataBase.getAppDatabase(this);
        new SaveTitle(db.dataDao()).execute(dataTable);
    }

    public void fetch() {
        ActivityDataBase db = ActivityDataBase.getAppDatabase(this);
        new FetchTitle(db.dataDao()).execute();
    }

//=========================검색기록 데이터를 가져옵니다.==============================
    private class FetchTitle extends AsyncTask<Void,Void,Void>{
        private DataDao dao;

        public FetchTitle(DataDao dataDao) {
        this.dao = dataDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dList = dao.getAll();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            binding.recyclerviewSearchTitle.setLayoutManager(linearLayoutManager);

            cAdapter = new SearchContentsAdapter(mContext, dList);
            cAdapter.setOnItemClickListener(new SearchContentsAdapter.OnItemClickCallback() {
                @Override
                public void onItem(View v,String str) {
                    oldTitle = str;
                    startSearch();
                    Log.d(TAG, "onItem: oldTitle = " + oldTitle);
                }
            });
            binding.recyclerviewSearchTitle.setAdapter(cAdapter);
            return null;
        }
    }


    //============================검색어 저장==========================================
    private class SaveTitle extends AsyncTask<DataTable, Void, Void> {
        private DataDao dao;

        public SaveTitle(DataDao dataDao) {
            this.dao = dataDao;
        }

        @Override
        protected Void doInBackground(DataTable... dataTables) {
            dao.insert(dataTables[0]);
            dList = dao.getAll();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            binding.recyclerviewSearchTitle.setLayoutManager(linearLayoutManager);

            cAdapter = new SearchContentsAdapter(mContext, dList);
            binding.recyclerviewSearchTitle.setAdapter(cAdapter);
            return null;
        }
    }

//=================================아이탬 저장 비동기 ===========================================
    private class SaveVod extends AsyncTask<DataTableVod, Void, Void> {
        private DataDao dao;

        public SaveVod(DataDao dataDao) {
            this.dao = dataDao;
        }

        @Override
        protected Void doInBackground(DataTableVod... dataTableVods) {
            dao.insertVod(dataTableVods[0]);
            return null;
        }
    }

//===============================검색 쓰레드 =======================================

    private class SearchTask extends AsyncTask<Void, Void, Void> {

        /**
         * onPreExecute() : 작업이 실행되기 직전에 UI 스레드에 의해 호출됩니다.
         * 일반적으로 UI 초기화와 같이, "비동기(Asynchronous) 실행" 작업에 대한 초기화 과정을 수행하는 메서드입니다.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 실질직인 작업 수행
         */
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
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivitySearch.this);
            binding.recyclerviewVod.setLayoutManager(linearLayoutManager);

            vAdapter = new VodAdapter(mContext, mList);
            vAdapter.setItemClickListener(new VodAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, SearchData data, int n) {
                    switch (n) {
                        case 1:
                            ArrayList<SearchData> sList = new ArrayList<>();
                            sList.add(data);

                            Intent i = new Intent(ActivitySearch.this, ActivityPlayer.class);
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

                            ActivityDataBase db = ActivityDataBase.getAppDatabase(ActivitySearch.this);
                            new SaveVod(db.dataDao()).execute(table);
                            break;
                    }
                }

                @Override
                public void onItemDelete(View v, DataTableVod dataTableVod) {
                }
            });
            binding.recyclerviewVod.setAdapter(vAdapter);
            vAdapter.notifyDataSetChanged();
        }
    }

    public JSONObject getUtube() throws IOException {

        if (oldTitle == null) {
            originUrl = "https://www.googleapis.com/youtube/v3/search?"
                    + "part=snippet&q=" + binding.search.getText().toString()
                    + "&key=" + API_KEY + "&maxResults=" + 50;
        } else {
            originUrl = "https://www.googleapis.com/youtube/v3/search?"
                    + "part=snippet&q=" + oldTitle
                    + "&key=" + API_KEY + "&maxResults=" + 50;
        }

        URL url = new URL(originUrl);

        /**
         * 안드로이드 어플리케이션이 서버와 통신하기 위한 방법에는 HTTP통신과 Socket 통신 2가지가 있는데,
         * 그 중 HTTP통신은 URL 접속을 통해 데이터를 읽어오는 방법입니다.
         */
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
        //아이템 누적 방지
        mList.clear();

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
//                    Log.d(TAG, "parsingJsonData: viewCount = " + viewCount);
//                    break;
//                }
//            }

            mList.add(new SearchData(vodId, changeT, imageUrl, channelId));

            oldTitle = "";
        }
    }

    private String stringToHtmlSign(String str) {
        return str.replaceAll("&amp;", "&")
                .replaceAll("[<]", "&lt;")
                .replaceAll("[>]", "&gt;")
                .replaceAll("&quot;", "'")
                .replaceAll("&#39;", "'");
    }

    public void backActivity(View view) {
        Intent intent = new Intent(this, ActivityYouTubeMain.class);
        startActivity(intent);
        finish();
    }
}















































