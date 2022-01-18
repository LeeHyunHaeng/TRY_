package com.yjk.sample._1_recyclerview_viewpager2.datamodule;


import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

public class Camera_Gallery_List_Data {
    private Bitmap bitmap;
    private String string;
    private boolean isLike = false;

    public Camera_Gallery_List_Data(Bitmap bitmap,String str) {
        this.bitmap = bitmap;
        this.string = str;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bit) {
        this.bitmap = bit;
    }

    public String getString(){
        return string;
    }

    public void setString(String str){
        this.string = str;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like){
        isLike = like;
    }
}
