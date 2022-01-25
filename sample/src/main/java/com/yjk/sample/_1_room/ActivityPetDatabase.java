package com.yjk.sample._1_room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = ActivityPetProfile.class, version = 1)
public abstract class ActivityPetDatabase extends RoomDatabase {
    public abstract ActivityPetProfileDao activityPetProfileDao();

}
