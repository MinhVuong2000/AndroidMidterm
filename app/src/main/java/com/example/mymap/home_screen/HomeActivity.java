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

import com.example.mymap.trip_screen.TripDoneInfo;
import com.example.mymap.trip_screen.ViewPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TripAdapter.OnItemListener {
    private DrawerLayout drawer;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment mPendingFragment;
    private Fragment mDoneFragment;
    private ArrayList<Trip> mListTrip;
    private TripAdapter mTripAdapter;
    private Button btnNewTrip;

    public static ArrayList<MyLocation> mLocationsArrayList;
    public static DatabaseReference firebaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trip);




        initData();
        initFragment();
        initUI();
    }

    private void initUI() {
        initToolbar();
        initViewPager();
        btnNewTrip = findViewById(R.id.btn_newTrip);
        btnNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent named_location = new Intent(HomeActivity.this, NamedTripActivity.class);
                startActivity(named_location);
            }
        });
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager_home);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_home);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(mDoneFragment, "Done");
        adapter.addFragment(mPendingFragment, "Pending");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }



    private void initFragment() {
        mDoneFragment = DoneFragment.newInstance();
        mPendingFragment = PendingFragment.newInstance();
    }

    private void initData() {
        mLocationsArrayList = MyLocation.getAllLocations();
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
        Intent intent;
        if (mListTrip.get(position).getIsDone()==0){
            intent = new Intent(this, TripActivity.class);
        }
        else{
            intent = new Intent(this, TripDoneInfo.class);
        }
        intent.putExtra("tripId", trip.getTripId());
        startActivity(intent);
    }




}