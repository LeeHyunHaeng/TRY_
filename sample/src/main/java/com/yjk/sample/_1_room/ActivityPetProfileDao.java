package com.yjk.sample._1_room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ActivityPetProfileDao {
    @Insert
    void insert(ActivityPetProfile dataProfile);

    @Delete
    void delete(ActivityPetProfile dataProfile);

    @Update
    void update(ActivityPetProfile dataProfile);

    @Query("SELECT * FROM ActivityPetProfile")
    List<ActivityPetProfile> getAll();
}
