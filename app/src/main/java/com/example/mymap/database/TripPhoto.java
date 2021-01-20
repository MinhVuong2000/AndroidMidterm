package com.example.mymap.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "photo")
public class TripPhoto {

    @PrimaryKey(autoGenerate = true)
    private int photoId;

    private int tripBelongId;

    private String photoPath;

    public TripPhoto(int tripBelongId, String photoPath) {
        this.tripBelongId = tripBelongId;
        this.photoPath = photoPath;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getTripBelongId() {
        return tripBelongId;
    }

    public void setTripBelongId(int tripBelongId) {
        this.tripBelongId = tripBelongId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
