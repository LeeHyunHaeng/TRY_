package com.yjk.sample._1_final_mission.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = DataTable.class,version = 1)
public abstract class ActivityDataBase extends RoomDatabase {
    public abstract DataDao dataDao();
}
