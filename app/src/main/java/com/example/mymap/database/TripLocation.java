package com.example.mymap.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.Date;

@Entity(tableName = "trip_location", primaryKeys = {"tripBelongId","locationId"})
public class TripLocation {

    private int tripBelongId;
    private int locationId;
    private int routeOrder;
    private Date timePassed;


    public TripLocation(int tripBelongId, int locationId) {
        this.tripBelongId = tripBelongId;
        this.locationId = locationId;
        routeOrder = -1;
        timePassed = null;
    }

    public int getTripBelongId() {
        return tripBelongId;
    }

    public void setTripBelongId(int tripBelongId) {
        this.tripBelongId = tripBelongId;
    }

    public int getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(int routeOrder) {
        this.routeOrder = routeOrder;
    }

    public Date getTimePassed() {
        return timePassed;
    }

    public void setTimePassed(Date timePassed) {
        this.timePassed = timePassed;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}
