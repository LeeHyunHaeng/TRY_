package com.yjk.sample.final_mission.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DataTable.class,DataTableVod.class} , version = 2)
public abstract class ActivityDataBase extends RoomDatabase {
    public abstract DataDao dataDao();
}
