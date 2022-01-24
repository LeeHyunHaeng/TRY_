package com.yjk.sample.bind_java;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yjk.sample.databinding.ActivitystartMainBinding;

public class ActivityStart extends AppCompatActivity {
    private ActivitystartMainBinding vBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBinding = ActivitystartMainBinding.inflate(getLayoutInflater());
        setContentView(vBinding.getRoot());

        fetchProfile();
    }

    public void fetchProfile() {
        DataStart dataStart = new DataStart();

        dataStart.name = "이행수";
        dataStart.age = "2살";
        dataStart.gender = 0;
        dataStart.imageUrl ="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory&fname=https://k.kakaocdn.net/dn/EShJF/btquPLT192D/SRxSvXqcWjHRTju3kHcOQK/img.png";

        vBinding.setDataStart(dataStart);
    }

}
