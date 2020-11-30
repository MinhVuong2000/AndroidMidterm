package com.example.mymap;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;


public class MyLocation implements Serializable {


    private int _pictureID;
    private String _name;
    private ArrayList<Integer> _listImages;
    private String _info;
    private LatLng _latlng;
    private ArrayList<String> mPhotoUri;





    public MyLocation(int pictureID, String name, ArrayList<Integer> listImages, String info, LatLng latLng) {
        _pictureID = pictureID;
        _name = name;
        _listImages = listImages;
        _info = info;
        _latlng = latLng;
        mPhotoUri = new ArrayList<>();
    }

    public int get_pictureID() {
        return _pictureID;
    }

    public LatLng get_latlng() {
        return _latlng;
    }

    public String get_name() {
        return _name;
    }

    public ArrayList<Integer> get_listImages() {
        return _listImages;
    }

    public String get_info() {
        return _info;
    }

    @Override
    public String toString() {
        return this._name;
    }

    public ArrayList<String> getmPhotoUri() {
        return mPhotoUri;
    }

    public void add_uri(String uri)
    {
        mPhotoUri.add(uri);
    }

    public String generate_nextPhotoName()
    {
        return _name + mPhotoUri.size() + ".jpg";
    }

}


