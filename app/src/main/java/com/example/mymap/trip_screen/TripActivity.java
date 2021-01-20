package com.example.mymap.trip_screen;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mymap.R;
import com.example.mymap.database.MyDatabase;
import com.example.mymap.database.Trip;
import com.example.mymap.trip_screen.gallery.GalleryFragment;
import com.example.mymap.trip_screen.map.MapsFragment;
import com.example.mymap.trip_screen.review.ReviewFragment;
import com.example.mymap.trip_screen.timeline.TimeLineFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TripActivity extends AppCompatActivity {
    private static final String TAG = "TripActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment mMapFragment;
    private Fragment mGalleryFragment;
    private Fragment mTimelineFragment;
    private Fragment mReviewFragment;
    private int mTripId;
    private int mIsDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Get tripId from home_screen
        Intent intent = getIntent();
        mTripId = intent.getIntExtra("tripId",0);
        mIsDone = intent.getIntExtra("isDone", 0);
        Log.d(TAG, "onCreate: isDone" + mIsDone);

        String tripName = MyDatabase.getInstance(this).myDAO().getTripName(mTripId);
        setTitle(tripName);

        initFragment();
        initUI();

    }

    private void initFragment() {
        mGalleryFragment = GalleryFragment.newInstance(mTripId);
        mMapFragment = MapsFragment.newInstance(mTripId);
        mTimelineFragment = TimeLineFragment.newInstance(mTripId);
        mReviewFragment = ReviewFragment.newInstance(mTripId);
    }

    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(mIsDone == 0) {
            adapter.addFragment(mMapFragment, "Maps");
        }
        else{
            adapter.addFragment(mReviewFragment, "Review");
        }
        adapter.addFragment(mGalleryFragment, "Gallery");
        adapter.addFragment(mTimelineFragment, "TimeLine");

        viewPager.setAdapter(adapter);
    }
}