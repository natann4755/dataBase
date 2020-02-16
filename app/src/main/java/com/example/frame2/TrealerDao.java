package com.example.frame2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TrealerDao {

    @Query("SELECT * FROM ResultsItem WHERE mooveyId = :id")
    ResultsItem getTreailer (int id);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert (ResultsItem resultsItem);
}
