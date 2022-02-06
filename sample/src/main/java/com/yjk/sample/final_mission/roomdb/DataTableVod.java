package com.yjk.sample.final_mission.roomdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DataTableVod {
    @PrimaryKey(autoGenerate = true)
    public int id;

    //좋아요 영상 저장
    public String vodId,title,uri,channelId;
    public boolean like;

}
