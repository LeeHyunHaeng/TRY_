package com.yjk.sample.bind_java;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class BindAdapter {
    @BindingAdapter("imageUrl")
    public static void lodeImage(ImageView v, String Url){
        Glide.with(v.getContext()).load(Url).into(v);
    }
}
