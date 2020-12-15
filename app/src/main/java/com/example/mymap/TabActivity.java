package com.example.mymap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TabActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment MapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get intent from homeActivity
        Intent intent = getIntent();
        ArrayList<Integer> locations_idx =  intent.getIntegerArrayListExtra("picked_locations_idx");
        int roundIntent = intent.getIntExtra("roundIntent",0);

        //pass to mapsfragment using bundle
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("picked_locations_idx", locations_idx);
        bundle.putInt("roundIntent", roundIntent);

        MapFragment = new MapsFragment();
        MapFragment.setArguments(bundle);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(MapFragment, "Maps");
        adapter.addFragment(new WorldFragment(), "World");
        viewPager.setAdapter(adapter);
    }
}