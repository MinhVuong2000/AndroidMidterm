package com.example.mymap.trip_screen.map.scroll_view;

import com.example.mymap.database.TripLocation;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<TripLocation> tripLocationList;
    public Data(List<TripLocation> tripLocations) {
        this.tripLocationList = tripLocations;
    }

    public List<Item> getData() {
        List<Item> res = new ArrayList<>();
        for (int i=0;i<tripLocationList.size();i++)
            res.add(new Item(tripLocationList.get(i)));
        return res;
    }
}
