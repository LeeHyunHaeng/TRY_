package com.yjk.sample.final_mission.heart_list;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yjk.sample.databinding.Activity1HeartListBinding;

public class ActivityMyList extends AppCompatActivity {
    public Activity1HeartListBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1HeartListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        
    }
}
