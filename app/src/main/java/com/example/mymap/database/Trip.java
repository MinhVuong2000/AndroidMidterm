package com.example.mymap.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="trip")
public class Trip {


    @PrimaryKey(autoGenerate = true)
    private int tripId;

    private String tripName;


    public Trip(String tripName) {
        this.tripName = tripName;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

}
