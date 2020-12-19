package com.example.mymap.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Trip.class, TripPhoto.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "trip.db";
    private static MyDatabase instance;

    public static synchronized MyDatabase getInstance(Context context)
    {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
        }
        return instance;
    }

    public abstract MyDAO myDAO();
}
