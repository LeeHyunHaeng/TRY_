package com.yjk.sample.final_mission.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DataTable.class,DataTableVod.class} , version = 2)
public abstract class ActivityDataBase extends RoomDatabase {
    private static ActivityDataBase INSTANCE = null;
    public abstract DataDao dataDao();

    public synchronized static ActivityDataBase getAppDatabase(Context context) {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ActivityDataBase.class, "vod-db").build();
        }
        return INSTANCE;
    }
}
