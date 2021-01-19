package com.example.mymap.trip_screen.map.scroll_view;

import com.example.mymap.database.MyLocation;
import com.example.mymap.database.TripLocation;
import com.google.android.gms.maps.model.LatLng;

import static com.example.mymap.home_screen.HomeActivity.mLocationsArrayList;

public class Item {
    private String name;
    private String state;
    private String icon;
    private LatLng latLng;

    public Item(TripLocation location) {
        this.name = mLocationsArrayList.get(location.getLocationId()).getName();
        this.state = location.getTimePassed()==null?"Chưa đi qua":"Đã đi qua";
        this.icon = mLocationsArrayList.get(location.getLocationId()).getIcon();
        this.latLng = mLocationsArrayList.get(location.getLocationId()).getLatlngLatlng();
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getIcon() {
        return icon;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", icon='" + icon + '\'' +
                ", latLng=" + latLng +
                '}';
    }
}
