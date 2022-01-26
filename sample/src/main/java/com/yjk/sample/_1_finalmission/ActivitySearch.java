package com.yjk.sample._1_finalmission;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yjk.sample.databinding.Activity1SerchBinding;

public class ActivitySearch extends AppCompatActivity {
    private Activity1SerchBinding binding;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1SerchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}
