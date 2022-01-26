package com.yjk.sample._1_finalmission;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.yjk.common.view.base.BaseActivity;
import com.yjk.sample.databinding.Activity1MainBinding;
import com.yjk.sample.databinding.Activity1PlayBinding;

public class ActivityYouTubeMain extends BaseActivity {

    private Activity1PlayBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity1PlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        setEvent();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setEvent() {

    }
}
