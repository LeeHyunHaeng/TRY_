package com.yjk.sample._1_final_mission.roomdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DataTable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String Contents;
}
