package com.example.mymap.trip_screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mymap.R;
import com.example.mymap.database.MyDatabase;

public class TripDoneInfo extends AppCompatActivity {
    int mTripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_done_info);

        mTripId = getIntent().getIntExtra("tripId",0);

        String tripName = MyDatabase.getInstance(this).myDAO().getTripName(mTripId);
        setTitle(tripName);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}