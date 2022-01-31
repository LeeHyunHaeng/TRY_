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

    @Insert
    void insertVod(DataTableVod dataTableVod);

    @Delete
    void delete(DataTable dataTable);

    @Delete
    void deleteVod(DataTableVod dataTableVod);

    @Query("SELECT * FROM DataTable")
    List<DataTable> getAll();

    @Query("SELECT * FROM DataTableVod")
    List<DataTableVod> getVodAll();
}
