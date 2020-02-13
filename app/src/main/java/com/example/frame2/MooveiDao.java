package com.example.frame2;

import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MooveiDao {

    @Query("SELECT * FROM Result ORDER BY popularity DESC")
    List<Result>getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll (ArrayList<Result> movies);

    @Query("DELETE FROM Result")
    void deleteAll();
}
