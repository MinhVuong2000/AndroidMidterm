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
    private int reviewStar;
    private String review;
    private int isDone;

    public Trip(String tripName, Date dateCreate, int isDone) {
        this.dateCreate = dateCreate;
        this.tripName = tripName;
        this.isDone = isDone;
        review = "";
        reviewStar = 0;
    }

    public int getReviewStar() {
        return reviewStar;
    }

    public void setReviewStar(int reviewStar) {
        this.reviewStar = reviewStar;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getIsDone() {
        return isDone;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
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
