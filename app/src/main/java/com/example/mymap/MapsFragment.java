package com.example.mymap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
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

public class MapsFragment extends Fragment
        implements OnMapReadyCallback, RoutingListener {
    private static final String TAG = "MapsFragment";
    private static final double epsilon = 0.001;//check to at destination
    //google map object
    private GoogleMap mMap;

    FusedLocationProviderClient mFusedLocationClient;

    int PERMISSION_ID = 44;

    private Button btnGotoRouting;
    private ImageButton btnCurLocation;

    static ArrayList<Route> routeMaps = null;
    static LatLng curLocation=null;
    static ArrayList<LatLng> latLngArrayList = null;
    static ArrayList<Integer> locations_idx = null;
    static int sizeLatLngList;
    static int roundIntent;

    Integer[] distanceList=null;
    int index_minDistance, round;

    ArrayList<Route> routeMapsRound = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_maps, container, false);

        btnGotoRouting = (Button) view.findViewById(R.id.btnGoToRouting);
        btnCurLocation = (ImageButton) view.findViewById(R.id.btnCurLocation);
        Log.d(TAG, "onCreateView: createview");

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());//get intent from HomeActivity

        Bundle bundle = getArguments();
        if(bundle!=null){
            locations_idx = bundle.getIntegerArrayList("picked_locations_idx");
            roundIntent = bundle.getInt("roundIntent", 0);
        }

        //create list latlng for find routes
        latLngArrayList = new ArrayList<>();
        for (int i=0; i<locations_idx.size(); i++){
            Log.d(TAG, "location: "+ locations_idx.get(i) + DataLocations.mData.get(locations_idx.get(i)).get_name());
            latLngArrayList.add(DataLocations.mData.get(locations_idx.get(i)).get_latlng());
        }

        //init for find route
        index_minDistance = 0;
        round=1;

        Log.d(TAG, "onCreate: oncreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: onResume");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: enter");
        mMap = googleMap;

        if (checkPermissions()) {
            getLastLocation(false);
        }

        btnCurLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation(true);
            }
        });

        btnGotoRouting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (roundIntent!=sizeLatLngList){
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLngArrayList.get(roundIntent-1)));
                    startARoute();

                }
                else{
                    Intent intent = new Intent(getActivity(),HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    private void arrayRoutes() {
        Log.d(TAG, "arrayRoutes: enter");
        if (curLocation==null){
            getLastLocation(true);
        }
        latLngArrayList.add(0,curLocation);
        sizeLatLngList = latLngArrayList.size();
        distanceList = new Integer[sizeLatLngList-1];

        routeMapsRound = new ArrayList<>();
        routeMaps = new ArrayList<Route>(sizeLatLngList-1);

        mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(DataLocations.mData.get(locations_idx.get(roundIntent-1)).get_latlng(), 20));
        for (int nextLoca=1;nextLoca<sizeLatLngList;nextLoca++){
            for (int nextNextLoca = nextLoca;nextNextLoca<sizeLatLngList;nextNextLoca++){
                Log.d(TAG, "arrayRoutes: "+nextLoca+" "+nextNextLoca);
                Findroutes(latLngArrayList.get(nextLoca-1),latLngArrayList.get(nextNextLoca));
            }
            addScaleIconLocation(mMap,DataLocations.mData.get(locations_idx.get(nextLoca-1)), ((int)mMap.getCameraPosition().zoom)*100,((int)mMap.getCameraPosition().zoom)*100, false);
        }
    }


    private void addScaleIconLocation(GoogleMap mMap, MyLocation myLocation, int width, int height, boolean filter) {
        Bitmap image = BitmapFactory.decodeResource(getActivity().getResources(), myLocation.get_pictureID());
        Bitmap scaledImage = Bitmap.createScaledBitmap(image, width, height, filter);
        BitmapDescriptor scaledIcon = BitmapDescriptorFactory.fromBitmap(scaledImage);
        mMap.addMarker(new MarkerOptions().position(myLocation.get_latlng())
                .title(myLocation.get_name())
                .icon(scaledIcon));
    }


    public void startARoute() {
        int k=routeMaps.get(roundIntent-1).getPoints().size();
        final LatLng end = routeMaps.get(roundIntent-1).getPoints().get(k-1);
        roundIntent++;
        btnGotoRouting.setText("Check in Destination");
        btnGotoRouting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //drawCurLocation();
                Log.d(TAG, "onClick: " + curLocation.latitude + "" + end.latitude);
                Intent intent = new Intent(getActivity(), GalleryActivity.class);
                intent.putExtra("reached_idx", locations_idx.get(roundIntent - 2));
                startActivity(intent);
                //checkRightDestination(curLocation,end);
            }
        });
    }


    private void checkRightDestination(LatLng curLocation, LatLng end) {
        if ((curLocation.latitude-end.latitude<epsilon && end.latitude-curLocation.latitude<epsilon)
                && (curLocation.longitude-end.longitude<epsilon && end.longitude-curLocation.longitude<epsilon)){
            Intent intent = new Intent(getActivity(),GalleryActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getActivity(),"Here is not Destination. Please go on!",Toast.LENGTH_LONG).show();
        }
    }


    // function to find Routes.
    public void Findroutes(LatLng Start, LatLng End)
    {
        if(Start==null || End==null) {
            if (Start==null)
            Toast.makeText(getActivity(),"Unable to get your location", Toast.LENGTH_LONG).show();
        }
        else
        {
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(false)
                    .waypoints(Start, End)
                    .key("AIzaSyBpGN09VGtFFgsjF0kvIK_QMk2MzWZlqUs")
                    .build();

            routing.execute();
        }
    }

    //Routing call back functions.
    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = getView().findViewById(android.R.id.content);
        Snackbar snackbar= Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(getActivity(),"Finding Route...",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRoutingCancelled() {
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
                break;
            }
        }
    }

    void addARoute(){
        List < Integer > numberList = Arrays.asList(distanceList);
        index_minDistance = numberList.indexOf(Collections.min(numberList));
        if (index_minDistance!=0){
            //swap latlng
            LatLng temp = latLngArrayList.get(round);
            latLngArrayList.set(round,latLngArrayList.get(index_minDistance+round));
            latLngArrayList.set(index_minDistance+round,temp);

            //swap location_idx
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
    }


    public void showRoute(Route r, GoogleMap mMap, boolean changeColor){
        List<Polyline> polylines = null;
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

    @SuppressLint("MissingPermission")
    private void getLastLocation(final boolean onlyGetLocation)
    {
        Log.d(TAG, "getLastLocation: enter");
        // check if permissions are given
        if (checkPermissions()) {
            // check if location is enabled
            if (isLocationEnabled()) {

                Log.d(TAG, "getLastLocation: isLocationEnable");
                // getting last location from FusedLocationClient object
                Task<Location> task = mFusedLocationClient.getLastLocation();
                task.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            curLocation = new LatLng(location.getLatitude()
                                    , location.getLongitude());
                            Log.d(TAG, "Location: " + curLocation.toString());
                            if (!onlyGetLocation) arrayRoutes();
                        }
                        else  requestNewLocationData();
                    }
                });
            }
            else {
                Toast.makeText(getActivity(),"Please turn on"+ " your location...",Toast.LENGTH_LONG).show();
                Intent intent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
        else {
            // if permissions aren't available, request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData()
    {
        // Initializing LocationRequest object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mFusedLocationClient.requestLocationUpdates( mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult( LocationResult locationResult){
            Location mLastLocation = locationResult.getLastLocation();
            curLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    };

    // method to check for permissions
    private boolean checkPermissions()
    {
        return ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // method to requestfor permissions
    private void requestPermissions()
    {
        ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled()
    {
        LocationManager locationManager  = (LocationManager)getActivity().getSystemService( Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation(false);
            }
        }
    }
}