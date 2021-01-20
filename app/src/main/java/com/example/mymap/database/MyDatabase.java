package com.example.mymap.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Trip.class, TripPhoto.class, TripLocation.class}, version = 1)
@TypeConverters({Converters.class})
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
