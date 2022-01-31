package com.yjk.sample.final_mission.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataDao {
    @Insert
    void insert(DataTable dataTable);

    @Delete
    void delete(DataTable dataTable);

    @Query("SELECT * FROM DataTable")
    List<DataTable> getAll();
}
