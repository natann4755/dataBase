package com.example.frame2;

import android.app.Activity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Result.class,ResultsItem.class},version = 3)

public abstract class AddDataBase extends RoomDatabase {

    public static final String DATABASE_NAME = "Arrey moovey";
    static AddDataBase INSTANCE;
    public abstract MooveiDao mooveiDao();
    public abstract TrealerDao trealerDao();
    public static AddDataBase getInstance(Activity context){
        if (INSTANCE ==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AddDataBase.class,DATABASE_NAME)
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    public static void destroyInstans(){
        INSTANCE=null;
    }

}
