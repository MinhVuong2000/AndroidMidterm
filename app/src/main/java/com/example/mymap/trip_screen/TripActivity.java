package com.example.mymap.trip_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.mymap.R;
import com.example.mymap.database.MyDatabase;
import com.example.mymap.database.Trip;
import com.example.mymap.trip_screen.gallery.GalleryFragment;
import com.example.mymap.trip_screen.map.MapsFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TripActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment mMapFragment;
    private Fragment mGalleryFragment;
    private int mTripId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get tripId from home_screen
        mTripId = getIntent().getIntExtra("tripId",0);
        String tripName = MyDatabase.getInstance(this).myDAO().getTripName(mTripId);
        setTitle(tripName);

//        pass to mapsfragment using bundle
//        Bundle bundle = new Bundle();
//        bundle.putIntegerArrayList("picked_locations_idx", locations_idx);
//        bundle.putInt("roundIntent", roundIntent);
//
//        MapFragment = new MapsFragment();
//        MapFragment.setArguments(bundle);

        initFragment();
        initUI();

    }

    private void initFragment() {
        mMapFragment = new MapsFragment();
        mGalleryFragment = new GalleryFragment();

        Bundle bundle = new Bundle();


    }

    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(mMapFragment, "Maps");
        adapter.addFragment(new WorldFragment(), "World");
        viewPager.setAdapter(adapter);
    }
}