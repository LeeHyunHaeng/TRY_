package com.yjk.sample.final_mission.roomdb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {DataTable.class,DataTableVod.class} , version = 3)
public abstract class ActivityDataBase extends RoomDatabase {
    private static ActivityDataBase INSTANCE = null;

    public abstract DataDao dataDao();

    public synchronized static ActivityDataBase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ActivityDataBase.class, "vod-db")
                    .addMigrations(MIGRATION_2_3)
                    .build();
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE DataTableVod ADD COLUMN 'like' INTEGER NOT NULL DEFAULT 0");
        }
    };
}


