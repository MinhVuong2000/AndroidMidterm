package com.example.mymap;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, RoutingListener {
    private static final String TAG = "MapsActivity";
    private static final double epsilon = 0.001;//check to at destination
    //google map object
    private GoogleMap mMap;

    FusedLocationProviderClient mFusedLocationClient;

    int PERMISSION_ID = 44;

    private TextView textShowRouting;
    private Button btnGotoRouting;
    private ImageButton btnCurLocation;

    static Marker[] markers = null;
    static ArrayList<Route> routeMaps = null;
    static LatLng curLocation=null;
    static ArrayList<LatLng> latLngArrayList = null;
    static int sizeLatLngList;
    static int roundIntent;
    static ArrayList<Integer> locations_idx = null;

    Integer[] distanceList=null;
    int index_minDistance, round;



    ArrayList<Route> routeMapsRound = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //get intent from HomeActivity
        Intent intent = this.getIntent();
        locations_idx =  intent.getIntegerArrayListExtra("picked_locations_idx");
        roundIntent = intent.getIntExtra("roundIntent",0);

        latLngArrayList = new ArrayList<>();
        for (int i=0; i<locations_idx.size(); i++){
            Log.d(TAG, "location: "+ locations_idx.get(i) + DataLocations.mData.get(locations_idx.get(i)).get_name());
            latLngArrayList.add(DataLocations.mData.get(locations_idx.get(i)).get_latlng());
        }

        index_minDistance = 0;
        round=1;

        textShowRouting = (TextView) findViewById(R.id.textShowRouting);
        btnGotoRouting = (Button) findViewById(R.id.btnGoToRouting);

        btnCurLocation = (ImageButton) findViewById(R.id.btnCurLocation);

        Log.d(TAG, "onCreate: oncreate");
        //init google map fragment to show map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: onResume");

        if (checkPermissions()) {
            //getLastLocation(false);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        btnCurLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation(true);
                addMarkerOnMap(curLocation,"Current Location",BitmapDescriptorFactory.HUE_ROSE, true);
            }
        });

        btnGotoRouting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (roundIntent!=sizeLatLngList){
                    //change color marker start and destination
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLngArrayList.get(round-1)));
                    SetupMarker(markers[roundIntent-1],"Start",BitmapDescriptorFactory.HUE_BLUE,true);
                    SetupMarker(markers[roundIntent],"Destination",BitmapDescriptorFactory.HUE_RED, false);

                    //change color other markers
                    for (int location=0;location < sizeLatLngList;location++){
                        if (location!=roundIntent && location!=roundIntent-1){
                            SetupMarker(markers[location],""+location,BitmapDescriptorFactory.HUE_GREEN, false);
                        }

                    }
                    Log.d(TAG, "onClick: aaaaaa");
                    startARoute();

                }
                else{
                    //Intent intent = new Intent(MapsActivity.this,EndJourney.class);
                    //startActivity(intent);
                        //Toast.makeText(HomeActivity.this,"Congrat to complete Your Trip. Make feel Happier!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MapsActivity.this,HomeActivity.class);
                        startActivity(intent);
                }

            }
        });
        arrayRoutes();
    }

    public static void SetupMarker(Marker marker, String title, float color, boolean showInfoWindow){
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(color));
        marker.setTitle(title);
        if (showInfoWindow)
            marker.showInfoWindow();
    }

    public void startARoute() {
        int k=routeMaps.get(roundIntent-1).getPoints().size();
        //LatLng start = routeMaps.get(round-1).getPoints().get(0);
        final LatLng end = routeMaps.get(roundIntent-1).getPoints().get(k-1);
        roundIntent++;
        btnGotoRouting.setText("Check in Destination");
        btnGotoRouting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //drawCurLocation();
                Log.d(TAG, "onClick: "+curLocation.latitude+""+end.latitude);
                Intent intent = new Intent(MapsActivity.this,GalleryActivity.class);
                intent.putExtra("reached_idx", locations_idx.get(roundIntent-2));
                startActivity(intent);
//                if ((curLocation.latitude-end.latitude<epsilon && end.latitude-curLocation.latitude<epsilon) &&
//                        (curLocation.longitude-end.longitude<epsilon && end.longitude-curLocation.longitude<epsilon)){
//                    Intent intent = new Intent(MapsActivity.this,GalleryActivity.class);
//                    startActivity(intent);
//                }
//                else Toast.makeText(MapsActivity.this,"Here is not Destination. Please go on!",Toast.LENGTH_LONG).show();

            }
        });
    }



    private void arrayRoutes() {
        curLocation = new LatLng(10.762533,106.6815594);
        latLngArrayList.add(0,curLocation);
        sizeLatLngList = latLngArrayList.size();
        markers = new Marker[sizeLatLngList];
        distanceList = new Integer[sizeLatLngList-1];

        routeMapsRound = new ArrayList<>();
        routeMaps = new ArrayList<Route>(sizeLatLngList-1);


        for (int nextLoca=1;nextLoca<sizeLatLngList;nextLoca++){
            for (int nextNextLoca = nextLoca;nextNextLoca<sizeLatLngList;nextNextLoca++){
                Log.d(TAG, "arrayRoutes: "+nextLoca+" "+nextNextLoca);
                Findroutes(latLngArrayList.get(nextLoca-1),latLngArrayList.get(nextNextLoca));
            }
        }
    }


    public Marker addMarkerOnMap(LatLng latLng, String title, float color, boolean showInfoWindow) {
        Marker marker = null;
        if (latLng!=null){
            MarkerOptions markerOptions = new MarkerOptions()
                    .draggable(true)
                    .title(title)
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(color));
            marker = mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));
            if (showInfoWindow)
                marker.showInfoWindow();
        }
        return marker;
    }

    // function to find Routes.
    public void Findroutes(LatLng Start, LatLng End)
    {
        if(Start==null || End==null) {
            if (Start==null)
                Log.d(TAG, "Findroutes: Null cur");
            else
                Log.d(TAG, "Findroutes: Null desti");
            Toast.makeText(MapsActivity.this,"Unable to get location", Toast.LENGTH_LONG).show();
        }
        else
        {

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(false)
                    .waypoints(Start, End)
                    .key("AIzaSyBpGN09VGtFFgsjF0kvIK_QMk2MzWZlqUs")  //also define your api key here.
                    .build();

            routing.execute();
        }
    }

    //Routing call back functions.
    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar= Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();
//        Findroutes(start,end);
    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(MapsActivity.this,"Finding Route...",Toast.LENGTH_SHORT).show();
    }

    //If Route finding success..
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        //add route(s) to the map using polyline
        for (int i = 0; i <route.size(); i++) {

            if(i==shortestRouteIndex)
            {
                routeMapsRound.add(route.get(i));
                distanceList[index_minDistance]=route.get(i).getDistanceValue();
                index_minDistance+=1;
                //Toast.makeText(this,""+index_minDistance+" "+round,Toast.LENGTH_SHORT).show();
                if (index_minDistance == sizeLatLngList-round){
                    addARoute();

                }
                if (round==sizeLatLngList){
                    textShowRouting.setText("Order You can go:\n0. Your location");
                    for (int location=0;location < sizeLatLngList;location++){
                        markers[location] = addMarkerOnMap(latLngArrayList.get(location),""+location,BitmapDescriptorFactory.HUE_GREEN,false);
                        if (location!=0){
                            textShowRouting.setText(textShowRouting.getText()+"\n"+location+". "+
                                    DataLocations.mData.get(locations_idx.get(location-1)).get_name());
                        }
                    }
                }
                break;
            }
        }
    }

    void addARoute(){
        List < Integer > numberList = Arrays.asList(distanceList);
        index_minDistance = numberList.indexOf(Collections.min(numberList));
        if (index_minDistance!=0){
            LatLng temp = latLngArrayList.get(round);
            latLngArrayList.set(round,latLngArrayList.get(index_minDistance+round));
            latLngArrayList.set(index_minDistance+round,temp);

            Log.d(TAG, "addARoute: done");
            Integer tmp = locations_idx.get(round-1);
            locations_idx.set(round-1, locations_idx.get(index_minDistance+round-1));
            locations_idx.set(index_minDistance+round-1, tmp);
        }
        routeMaps.add(routeMapsRound.get(index_minDistance));
        showRoute(routeMaps.get(round-1), mMap,roundIntent>round);
        round++;

        routeMapsRound.clear();
        routeMapsRound = new ArrayList<>();
        distanceList = new Integer[sizeLatLngList-round];
        index_minDistance = 0;
        //Toast.makeText(this,""+round,Toast.LENGTH_SHORT).show();
    }


    public void showRoute(Route r, GoogleMap mMap, boolean changeColor){

        //polyline object
        List<Polyline> polylines = null;
        //CameraUpdate center = CameraUpdateFactory.newLatLng(r.getPoints().get(0));
        //CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
        if(polylines!=null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();

        polylines = new ArrayList<>();
        if (changeColor)
            polyOptions.color(Color.argb(100,100,100,100));
        polyOptions.width(7);
        polyOptions.addAll(r.getPoints());
        Polyline polyline = mMap.addPolyline(polyOptions);
        polylines.add(polyline);
    }


    @Override
    public void onRoutingCancelled() {
        //Findroutes(start,end);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(final boolean onlyGetLocation)
    {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                Task<Location> task = mFusedLocationClient.getLastLocation()
                        .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    curLocation = new LatLng(task.getResult().getLatitude(),task.getResult().getLongitude());
                                    Log.d(TAG, "Task:");
                                    if (!onlyGetLocation) arrayRoutes();

                                }
                            }
                        });
            }
            else {
                Toast.makeText(this,"Please turn on"+ " your location...",Toast.LENGTH_LONG).show();

                Intent intent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
        else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData()
    {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest
                = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mFusedLocationClient.requestLocationUpdates( mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult( LocationResult locationResult){
            Location mLastLocation = locationResult.getLastLocation();

            LatLng userPos = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(userPos).title("Marker in Current Location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userPos,20));
        }
    };

    // method to check for permissions
    private boolean checkPermissions()
    {
        return ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)== PackageManager.PERMISSION_GRANTED

    }

    // method to requestfor permissions
    private void requestPermissions()
    {
        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled()
    {
        LocationManager locationManager  = (LocationManager)getSystemService( Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(
                        requestCode,
                        permissions,
                        grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation(false);
            }
        }
    }
}