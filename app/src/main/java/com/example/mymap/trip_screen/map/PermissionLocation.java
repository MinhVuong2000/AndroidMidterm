package com.example.mymap.trip_screen.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import static com.example.mymap.trip_screen.map.MapsFragment.PERMISSION_ID;
import static com.example.mymap.trip_screen.map.MapsFragment.mFusedLocationClient;

public class PermissionLocation {
    LatLng curLocation;
    Fragment mActivity;
    
    public PermissionLocation(LatLng cur,Fragment activity){
        this.curLocation = cur;
        this.mActivity = activity;
    }

    @SuppressLint("MissingPermission")
    public void requestNewLocationData()
    {
        // Initializing LocationRequest object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity.getActivity());
        mFusedLocationClient.requestLocationUpdates( mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    public LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult( LocationResult locationResult){
            Location mLastLocation = locationResult.getLastLocation();
            curLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    };

    // method to check for permissions
    public boolean checkPermissions()
    {
        return ActivityCompat.checkSelfPermission(mActivity.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mActivity.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // method to requestfor permissions
    public void requestPermissions()
    {
        ActivityCompat.requestPermissions(mActivity.getActivity(), new String[] { Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSION_ID);
    }

    // method to check if location is enabled
    public boolean isLocationEnabled()
    {
        LocationManager locationManager  = (LocationManager)mActivity.getActivity().getSystemService( Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void showAlertDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mActivity.getActivity());
        builder1.setMessage("You need turn on GPS to find best Trip for you. " +
                "Can you turn on your GPS?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mActivity.getActivity().startActivity(intent);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
