package com.example.mymap.database;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = "trip_location", primaryKeys = {"tripBelongId", "locationId"})
public class TripLocation {

    private int tripBelongId;
    private int locationId;
    private int routeOrder;
    private Date timePassed;


    public TripLocation(int tripBelongId, int locationId) {
        this.tripBelongId = tripBelongId;
        this.locationId = locationId;
        timePassed = null;
        routeOrder = 0;
    }

    @Ignore
    public TripLocation(int tripBelongId, int locationId, int order, Date timePassed) {
        this.tripBelongId = tripBelongId;
        this.locationId = locationId;
        this.routeOrder = order;
        this.timePassed = timePassed;
    }

    public int getTripBelongId() {
        return tripBelongId;
    }

    public void setTripBelongId(int tripBelongId) {
        this.tripBelongId = tripBelongId;
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

    public int getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(int routeOrder) {
        this.routeOrder = routeOrder;
    }
}
