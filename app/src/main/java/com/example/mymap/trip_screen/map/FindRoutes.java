//package com.example.mymap.trip_screen.map;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.location.Location;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.directions.route.Route;
//import com.example.mymap.FinishTrip;
//import com.example.mymap.R;
//import com.example.mymap.database.MyLocation;
//import com.example.mymap.database.TripLocation;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.BitmapDescriptor;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.Polyline;
//import com.google.android.gms.maps.model.PolylineOptions;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//import static com.example.mymap.home_screen.HomeActivity.mLocationsArrayList;
//
//public class FindRoutes {
//
//
//    public void arrayRoutes() {
//        //init for find route
//        index_minDistance = 0;
//        round=1;
//        Log.d(TAG, "arrayRoutes: enter");
//        //create list latlng for find routes
//        latLngArrayList = new ArrayList<>();
//        latLngArrayList.add(curLocation);
//        for (int i=roundIntent-1; i<tripLocationList.size(); i++){
//            latLngArrayList.add(mLocationsArrayList.get(tripLocationList.get(i).getLocationId()).getLatlngLatlng());
//        }
//        if (curLocation == null){
//            Toast.makeText(getContext(),"Can get your location. Turn back again!",Toast.LENGTH_LONG).show();
//        }
//        else{
//            sizeLatLngList = latLngArrayList.size();
//            distanceList = new Integer[sizeLatLngList-1];
//
//            routeMapsRound = new ArrayList<>();
//            routeMaps = new ArrayList<Route>(sizeLatLngList-1);
//
//            mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(mLocationsArrayList.get(tripLocationList.get(roundIntent-1).getLocationId()).getLatlngLatlng(), zoomDefault));
//            mMap.addMarker(new MarkerOptions().position(curLocation)
//                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_home)));
//            for (int nextLoca=1;nextLoca<sizeLatLngList;nextLoca++){
//                for (int nextNextLoca = nextLoca;nextNextLoca<sizeLatLngList;nextNextLoca++){
//                    Log.d(TAG, "arrayRoutes: "+nextLoca+" "+nextNextLoca);
//                    Findroutes(latLngArrayList.get(nextLoca-1),latLngArrayList.get(nextNextLoca));
//                }
//                Log.d(TAG, "arrayRoutes: roundIntent"+roundIntent + "nextLoca: "+nextLoca);
//                addScaleIconLocation(mMap,mLocationsArrayList.get(tripLocationList.get(nextLoca-1).getLocationId()),120,120, false);
//            }
//        }
//    }
//
//
//    private void addScaleIconLocation(GoogleMap mMap, MyLocation myLocation, int width, int height, boolean filter) {
//        Bitmap image = MyLocation.getPicture(myLocation.getIcon());
//        Bitmap scaledImage = Bitmap.createScaledBitmap(image, width, height, filter);
//        BitmapDescriptor scaledIcon = BitmapDescriptorFactory.fromBitmap(scaledImage);
//        mMap.addMarker(new MarkerOptions().position(myLocation.getLatlngLatlng())
//                .title(myLocation.getName())
//                .icon(scaledIcon));
//    }
//
//
//    public void startARoute() {
//        startARouteInt=true;
//        LatLng start = routeMaps.get(roundIntent-1).getPoints().get(0);
//        showRoute(routeMaps.get(roundIntent-1),mMap,false,startARouteInt);
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start,zoomDefault+2));
//        btnGotoRouting.setText("Next Location");
//        btnGotoRouting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                roundIntent++;
//                database.myDAO().updateTimePassed(tripId,roundIntent-2,new Date());
//                //checkRightDestination(curLocation,end);
//                if (roundIntent <= tripLocationList.size()){
//                    startARoute();
//                    if (roundIntent == tripLocationList.size())
//                        btnGotoRouting.setText("Finish Trip");
//                }
//
//                else{
//                    Intent intent = new Intent(getActivity(), FinishTrip.class);
//                    intent.putExtra("tripId", tripId);
//                    startActivity(intent);
//                }
//            }
//        });
//    }
//
//
//    private void checkRightDestination(LatLng curLocation, LatLng end) {
//        if ((curLocation.latitude-end.latitude<epsilon && end.latitude-curLocation.latitude<epsilon)
//                && (curLocation.longitude-end.longitude<epsilon && end.longitude-curLocation.longitude<epsilon)){
//            database.myDAO().updateTimePassed(tripId,roundIntent-2,new Date());
//        }
//        else {
//            Toast.makeText(getActivity(),"Here is not Destination. Please go on!",Toast.LENGTH_LONG).show();
//        }
//    }
//
//
//    void addARoute(){
//        List < Integer > numberList = Arrays.asList(distanceList);
//        index_minDistance = numberList.indexOf(Collections.min(numberList));
//        if (index_minDistance!=0){
//            //swap latlng
//            LatLng temp = latLngArrayList.get(round);
//            latLngArrayList.set(round,latLngArrayList.get(index_minDistance+round));
//            latLngArrayList.set(index_minDistance+round,temp);
//
//            //swap tripLocationList
//            TripLocation tmp = tripLocationList.get(roundIntent - 1 + round-1);
//            tripLocationList.set(roundIntent - 1 + round-1,tripLocationList.get(roundIntent - 1 + index_minDistance+round-1));
//            tripLocationList.set(roundIntent - 1 + index_minDistance+round-1,tmp);
//        }
//        routeMaps.add(routeMapsRound.get(index_minDistance));
//        showRoute(routeMaps.get(round-1), mMap,roundIntent>round, startARouteInt);
//        round++;
//
//        routeMapsRound.clear();
//        routeMapsRound = new ArrayList<>();
//        distanceList = new Integer[sizeLatLngList-round];
//        index_minDistance = 0;
//    }
//
//
//    public static void showRoute(Route r, GoogleMap mMap, boolean changeColor, boolean startARoute){
//        List<Polyline> polylines = null;
//        if(polylines!=null) {
//            polylines.clear();
//        }
//        PolylineOptions polyOptions = new PolylineOptions();
//
//        polylines = new ArrayList<>();
//        if (changeColor)
//            polyOptions.color(Color.argb(100,100,100,100));
//        if (startARoute)
//            polyOptions.color(Color.argb(255,0,0,255));
//        polyOptions.width(7);
//        polyOptions.addAll(r.getPoints());
//        Polyline polyline = mMap.addPolyline(polyOptions);
//        polylines.add(polyline);
//    }
//
//
//
//    @SuppressLint("MissingPermission")
//    private void getLastLocation(final boolean onlyGetLocation)
//    {
//        Log.d(TAG, "getLastLocation: enter");
//        // check if permissions are given
//        if (permissionLocation.checkPermissions()) {
//            // check if location is enabled
//            if (permissionLocation.isLocationEnabled()) {
//
//                Log.d(TAG, "getLastLocation: isLocationEnable");
//                // getting last location from FusedLocationClient object
//                Task<Location> task = mFusedLocationClient.getLastLocation();
//                task.addOnSuccessListener(new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location != null) {
//                            curLocation = new LatLng(location.getLatitude()
//                                    , location.getLongitude());
//                            Log.d(TAG, "Location: " + curLocation.toString());
//                            if (!onlyGetLocation){
//                                arrayRoutes();
//                            }
//                            mMap.setMyLocationEnabled(true);
//                        }
//                        else  {
//                            permissionLocation.requestNewLocationData();
//                        }
//                    }
//                });
//            }
//            else {
//                permissionLocation.showAlertDialog();
//                getLastLocation(onlyGetLocation);
//            }
//        }
//        else {
//            // if permissions aren't available, request for permissions
//            permissionLocation.requestPermissions();
//            getLastLocation(onlyGetLocation);
//        }
//    }
//}
