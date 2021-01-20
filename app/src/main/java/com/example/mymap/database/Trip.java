package com.example.mymap.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName="trip")
public class Trip {
    @PrimaryKey(autoGenerate = true)
    private int tripId;

    private String tripName;
    private Date dateCreate;
    private Boolean isDone;

    public Trip(String tripName, Date dateCreate, Boolean isDone) {
        this.dateCreate = dateCreate;
        this.tripName = tripName;
        this.isDone = isDone;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
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

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
}
