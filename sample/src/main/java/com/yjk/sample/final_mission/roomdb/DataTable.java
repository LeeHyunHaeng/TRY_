package com.yjk.sample.final_mission.roomdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DataTable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    //검색기록 저장
    public String Contents;

    //좋아요 영상 저장
    public String vodId,title,uri,channelId;
}
