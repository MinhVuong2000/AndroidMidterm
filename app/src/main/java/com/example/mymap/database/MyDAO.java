package com.example.mymap.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MyDAO {

    @Insert
    void insertTrip(Trip trip);

    @Insert
    void insertPhoto(TripPhoto photo);

    @Query("SELECT * FROM trip")
    List<Trip> getListTrip();

    @Query("SELECT photoPath FROM photo WHERE tripBelongId = :tripId")
    List<String> getListPhotoFromTrip(int tripId);

}
