package com.yjk.sample._1_finalmission.search;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.yjk.sample._1_finalmission.ActivityYouTubeMain;
import com.yjk.sample._1_finalmission.adapter.SearchAdapter;
import com.yjk.sample._1_finalmission.datamodule.SearchData;
import com.yjk.sample.databinding.Activity1SerchBinding;

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

public class ActivitySearch extends YouTubeBaseActivity {
    static final String TAG = "HAENG";

    private Activity1SerchBinding binding;
    private String originUrl;
    private String vodId = "";
    private SearchAdapter adapter;
    private ArrayList<SearchData> mList;
    private Context mContext;

    private final String API_KEY = "AIzaSyB2HSYPTzhG6UiuTMKipC1kUzXfZPDXnME";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1SerchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        검색창에서 엔터 누르면 바로 search클래스 실행
        binding.search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN)&& (i == KeyEvent.KEYCODE_ENTER)) {

                    ActivitySearch.searchTask searchTask = new ActivitySearch.searchTask();
                    searchTask.execute();
                    Log.d(TAG, "onKey: 실행");
                    return true;
                }
                return false;
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerviewSearch.setLayoutManager(linearLayoutManager);

    }

    private class searchTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                JSONObject jsonObject = getUtube();
                parsingJsonData(jsonObject);
                Log.d(TAG, "doInBackground: searchTask 실행");
            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            adapter = new SearchAdapter(mContext,mList);
            binding.recyclerviewSearch.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    public JSONObject getUtube() throws IOException {

        originUrl = "https://www.googleapis.com/youtube/v3/search?"
                + "part=snippet&q=" + binding.search.getText().toString()
                + "&key="+ API_KEY+"&maxResults=50";

        Log.d(TAG, "getUtube: url = " + originUrl);

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
            String changString = stringToHtmlSign(title);

//            String channelId = o.getJSONObject("snippet").getString("channelId");
//            String changStringId = stringToHtmlSign(channelId);
//
//            String viewCount = o.getJSONObject("statistics").getString("viewCount");

            String imageUrl = o.getJSONObject("snippet").getJSONObject("thumbnails")
                    .getJSONObject("default").getString("url");


            mList = new ArrayList<>();
            mList.add(new SearchData(vodId,changString,imageUrl));
            Log.d(TAG, "parsingJsonData: mList = " + mList);


        }
    }

    private String stringToHtmlSign(String str) {
        return str.replaceAll("&amp;", "[&]")
                .replaceAll("[<]","&lt;")
                .replaceAll("[>]","&gt;")
                .replaceAll("&quot;","'")
                .replaceAll("&#39;","'");
    }

    public void backActivity(View view) {
        Intent intent = new Intent(this, ActivityYouTubeMain.class);
        startActivity(intent);
    }
}















































