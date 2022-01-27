package com.yjk.sample._1_finalmission;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yjk.common.view.base.BaseActivity;
import com.yjk.sample.R;
import com.yjk.sample._1_finalmission.adapter.MainAdapter;
import com.yjk.sample._1_finalmission.search.ActivitySearch;
import com.yjk.sample.databinding.Activity1MainBinding;

public class ActivityYouTubeMain extends BaseActivity {

    private Activity1MainBinding binding;
    private MainAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        setEvent();
    }

    @Override
    protected void initView() {

        binding.recyclerviewMain.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerviewMain.setAdapter(adapter);
    }

    @Override
    protected void setEvent() {
    }

    //검색버튼 누르면 검색화면으로 전환 메서드
    public void videoSearch(View view) {
        Intent intent = new Intent(this,ActivitySearch.class);
        startActivity(intent);
    }
}

