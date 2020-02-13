package com.example.frame2;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Result.class},version = 1)
@TypeConverters({Converters.class})
public abstract class AddDataBase extends RoomDatabase {

    public static final String DATABASE_NAME = "Arrey moovey";
    static AddDataBase INSTANCE;
    public abstract MooveiDao mooveiDao();
    public static AddDataBase getInstans(retrofit2.Callback<ImegeSearchResult> context){
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
