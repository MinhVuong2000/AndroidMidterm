package com.example.mymap.database;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.example.mymap.home_screen.ChooseLocationActivity;
import com.example.mymap.home_screen.HomeActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@IgnoreExtraProperties
public class MyLocation {
    private String icon;
    private String info;
    private String latlng;
    private String name;
    private String picture1;
    private String picture2;
    private String picture3;


    public MyLocation(){

    }

    public MyLocation(String icon, String info, String latlng, String name, String picture1, String picture2, String picture3) {
        this.icon = icon;
        this.info = info;
        this.latlng = latlng;
        this.name = name;
        this.picture1 = picture1;
        this.picture2 = picture2;
        this.picture3 = picture3;
    }

    public String getIcon() {

        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLatlng() {
        return latlng;
    }

    public LatLng getLatlngLatlng() {
        String[] latlong =  latlng.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        return new LatLng(latitude,longitude);
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPictures(){
        ArrayList<String> result = new ArrayList<>();
        result.add(picture1);
        result.add(picture2);
        result.add(picture3);
        return result;
    }

    public static MyLocation getLocationAtPos(final Integer pos){
        final MyLocation[] result = new MyLocation[1];
        DatabaseReference firebaseReference= FirebaseDatabase.getInstance().getReference("myLocation").child(pos.toString());
        firebaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                result[0] = dataSnapshot.getValue(MyLocation.class);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return result[0];
    }

    public static ArrayList<MyLocation> getAllLocations() {
        final ArrayList<MyLocation> res = new ArrayList<>();
        DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference("myLocation");
        firebaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot di:dataSnapshot.getChildren()){
                    res.add(di.getValue(MyLocation.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return res;
    }

    public static Bitmap getPicture(String urlSrc){
        try {
            URL url = new URL(urlSrc);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getPicture3() {
        return picture3;
    }

    public void setPicture3(String picture3) {
        this.picture3 = picture3;
    }

    @Override
    public String toString() {
        return name;
    }
}