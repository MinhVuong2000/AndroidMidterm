package com.example.mymap.home_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import com.example.mymap.R;
import com.example.mymap.database.MyDatabase;
import com.example.mymap.database.MyLocation;
import com.example.mymap.database.Trip;
import com.example.mymap.trip_screen.TripActivity;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TripAdapter.OnItemListener {
    private DrawerLayout drawer;
    private RecyclerView mRecyclerView;
    private ArrayList<Trip> mListTrip;
    private TripAdapter mTripAdapter;

    public static ArrayList<MyLocation> mLocationsArrayList;
    public static DatabaseReference firebaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trip);

        initToolbar();
        initRecyclerView();
        Button new_trip = findViewById(R.id.newtrip);
        new_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent named_location = new Intent(HomeActivity.this, NamedTripActivity.class);
                startActivity(named_location);
            }
        });

        initData();
    }

    private void initData() {
        mLocationsArrayList = MyLocation.getAllLocations();
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Trip> list = MyDatabase.getInstance(this).myDAO().getListTrip();
        mListTrip = new ArrayList<>(list);
        mTripAdapter.setData(mListTrip);
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView_home);
        List<Trip> list = MyDatabase.getInstance(this).myDAO().getListTrip();
        mListTrip = new ArrayList<>(list);
        mTripAdapter = new TripAdapter(list,this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mTripAdapter);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mytrip_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.person)
        {
            Intent profile_intent = new Intent(HomeActivity.this, Profile.class);
            startActivity(profile_intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                Intent setting_intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(setting_intent);
                break;
            case R.id.action_about:
                Intent about_intent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(about_intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onItemClick(int position) {
        Trip trip = mListTrip.get(position);
        Intent intent = new Intent(this, TripActivity.class);
        intent.putExtra("tripId", trip.getTripId());
        startActivity(intent);
    }




}