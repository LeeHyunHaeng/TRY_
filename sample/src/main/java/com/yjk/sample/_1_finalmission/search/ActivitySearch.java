package com.yjk.sample._1_finalmission.search;

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
import com.yjk.sample._1_finalmission.ActivityYouTubeMain;
import com.yjk.sample._1_finalmission.ClearEditText.ClearEditText;
import com.yjk.sample._1_finalmission.adapter.SearchContentsAdapter;
import com.yjk.sample._1_finalmission.adapter.SearchVodAdapter;
import com.yjk.sample._1_finalmission.datamodule.SearchData;
import com.yjk.sample._1_finalmission.roomdb.ActivityDataBase;
import com.yjk.sample._1_finalmission.roomdb.DataTable;
import com.yjk.sample.databinding.Activity1SerchMainBinding;
import com.yjk.sample.databinding.Activity1SerchMainBinding;

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
import java.util.List;

public class ActivitySearch extends YouTubeBaseActivity {
    static final String TAG = ActivitySearch.class.toString();

    private Activity1SerchMainBinding binding;
    private String originUrl;
    private String oldTitle;
    public String vodId = "";
    private SearchVodAdapter vAdapter;
    private SearchContentsAdapter cAdapter;
    private ArrayList<SearchData> mList;
    private Context mContext;
    private ActivityDataBase db;


    private final String API_KEY = "AIzaSyAXV8MZt-Vn15KgIonqEzlx9KIs_AteSxs";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1SerchMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = Room.databaseBuilder(this,ActivityDataBase.class,"contents").allowMainThreadQueries().build();
        sendTitle();

        mList = new ArrayList<>();

//        검색창에서 엔터 누르면 바로 search클래스 실행
        binding.search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN)&& (i == KeyEvent.KEYCODE_ENTER)) {

                    ActivitySearch.searchTask searchTask = new ActivitySearch.searchTask();
                    searchTask.execute();
                    addTitle();

                    binding.layoutMain.setVisibility(View.GONE);
                    binding.layoutVod.setVisibility(View.VISIBLE);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.search.getWindowToken(),0);
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

//        아이템 클릭시 데이터 받아와서 저장
        cAdapter.setMyItemClickListener(new SearchContentsAdapter.OnItemClickCallback() {
            @Override
            public void onItem(String str) {
                oldTitle = str;

                ActivitySearch.searchTask searchTask = new ActivitySearch.searchTask();
                searchTask.execute();
            }
        });

    }

    public void addTitle() {
        DataTable data = new DataTable();
        data.Contents = binding.search.getText().toString();
        db.dataDao().insert(data);

        sendTitle();
    }

    public void sendTitle() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerviewSearchTitle.setLayoutManager(linearLayoutManager);

        List<DataTable> dList = db.dataDao().getAll();
        cAdapter = new SearchContentsAdapter(mContext,dList);
        binding.recyclerviewSearchTitle.setAdapter(cAdapter);
    }



//===============================검색 쓰레드 =======================================

    private class searchTask extends AsyncTask<Void,Void,Void> {

        /**onPreExecute() : 작업이 실행되기 직전에 UI 스레드에 의해 호출됩니다.
         * 일반적으로 UI 초기화와 같이, "비동기(Asynchronous) 실행" 작업에 대한 초기화 과정을 수행하는 메서드입니다.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /** 실질직인 작업 수행
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

            vAdapter = new SearchVodAdapter(mContext,mList);
            binding.recyclerviewVod.setAdapter(vAdapter);
            vAdapter.notifyDataSetChanged();
        }
    }

    public JSONObject getUtube() throws IOException {

        if (oldTitle == null) {

            originUrl = "https://www.googleapis.com/youtube/v3/search?"
                    + "part=snippet&q=" + binding.search.getText().toString()
                    + "&key="+ API_KEY+"&maxResults=" + 50;
        } else {
            originUrl = "https://www.googleapis.com/youtube/v3/search?"
                    + "part=snippet&q=" + oldTitle
                    + "&key="+ API_KEY+"&maxResults=" + 50;
        }


        URL url = new URL(originUrl);

        HttpURLConnection connection =(HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.connect();

        String line;
        String result="";
        InputStream inputStream=connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer response = new StringBuffer();

        while ((line = reader.readLine())!=null){
            response.append(line);
        }
        result=response.toString();

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

        for (int i =0; i < contacts.length(); i++){
            JSONObject o = contacts.getJSONObject(i);
            String kind = o.getJSONObject("id").getString("kind");

            if(kind.equals("youtube#video")){
                vodId = o.getJSONObject("id").getString("videoId");
            }else {
                vodId = o.getJSONObject("id").getString("playlistId");
            }
            String title = o.getJSONObject("snippet").getString("title");
            String changeT = stringToHtmlSign(title);

            String channelId = o.getJSONObject("snippet").getString("channelId");

//            long viewCount = o.getJSONObject("statistics").getLong("viewCount");
//            Log.d(TAG, "parsingJsonData: viewCount = " + viewCount);

            String imageUrl = o.getJSONObject("snippet").getJSONObject("thumbnails")
                    .getJSONObject("high").getString("url");


            mList.add(new SearchData(vodId,changeT,imageUrl,channelId));
            Log.d(TAG, "parsingJsonData: mList = " + mList);
        }
    }

    private String stringToHtmlSign(String str) {
        return str.replaceAll("&amp;", "&")
                .replaceAll("[<]","&lt;")
                .replaceAll("[>]","&gt;")
                .replaceAll("&quot;","'")
                .replaceAll("&#39;","'");
    }

    public void backActivity(View view) {
        Intent intent = new Intent(this, ActivityYouTubeMain.class);
        startActivity(intent);
        finish();
    }
}















































