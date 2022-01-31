package com.yjk.sample._1_final_mission;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.yjk.sample._1_final_mission.adapter.VodAdapter;
import com.yjk.sample._1_final_mission.collect_data.SearchData;
import com.yjk.sample._1_final_mission.search.ActivitySearch;
import com.yjk.sample.databinding.Activity1MainBinding;

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

public class ActivityYouTubeMain extends YouTubeBaseActivity {
    private final String API_KEY = "AIzaSyBZ8FJ2mt750RJYxfOqcsyZ2_JgByB3wqI";

    private Activity1MainBinding binding;
    private Context mContext;

    private String originUrl;
    private String oldTitle;
    private String vodId = "";

    private ArrayList<SearchData> mList;
    private VodAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mList = new ArrayList<>();

        searchVod searchVod = new searchVod();
        searchVod.execute();
        }

//===============================검색 쓰레드 =======================================

    private class searchVod extends AsyncTask<Void,Void,Void> {
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

            adapter = new VodAdapter(mContext,mList);
            binding.recyclerviewMain.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    public JSONObject getUtube() throws IOException {

        originUrl = "https://www.googleapis.com/youtube/v3/search?"
                + "part=snippet&q= 팝송"
                + "&key="+ API_KEY+"&maxResults=" + 50;

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
        }
    }

    private String stringToHtmlSign(String str) {
        return str.replaceAll("&amp;", "&")
                .replaceAll("[<]","&lt;")
                .replaceAll("[>]","&gt;")
                .replaceAll("&quot;","'")
                .replaceAll("&#39;","'");
    }
    public void videoSearch(View view) {
        Intent intent = new Intent(this, ActivitySearch.class);
        startActivity(intent);
        finish();
    }
}

