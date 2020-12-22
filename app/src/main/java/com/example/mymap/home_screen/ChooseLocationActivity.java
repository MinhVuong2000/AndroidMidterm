package com.example.mymap.home_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.mymap.DataLocations;
import com.example.mymap.R;
import com.example.mymap.database.MyDatabase;
import com.example.mymap.database.Trip;
import com.example.mymap.database.TripLocation;
import com.example.mymap.trip_screen.TripActivity;

import java.util.ArrayList;

public class ChooseLocationActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private ListView mListView;
    private Button mBtn_startTrip;
    MyLocationAdapter mLocationAdapter;
    MyDatabase database;
    String mTripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Choose Your Destinations");

        mTripName = getIntent().getStringExtra("TripName");
        Log.d(TAG, "onCreate: trip name" + mTripName);
        database = MyDatabase.getInstance(this);
//        Toast.makeText(getApplicationContext(),"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
        initData();
        initUI();

    }

    private void initUI() {
        mLocationAdapter = new MyLocationAdapter(this, R.layout.home_activity_listview_item, DataLocations.mData);
        mListView = findViewById(R.id.listView);
        mListView.setAdapter(mLocationAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MyLocationInfoScreen.class);
                intent.putExtra("location_idx", position);
                startActivity(intent);
            }

        });

        this.mBtn_startTrip = (Button)findViewById(R.id.button);
        this.mBtn_startTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_trip();
            }
        });
    }


    private void initData() {
        DataLocations.GenerateData(getBaseContext());
    }



    private void start_trip() {
        Trip new_trip = new Trip(mTripName);
        long rowId = database.myDAO().insertTrip(new_trip);
        int tripId = database.myDAO().getTripIdFromRowId(rowId);

        SparseBooleanArray sp = mLocationAdapter.get_checkStates();
        ArrayList<Integer> picked_locations_idx = new ArrayList<>();
        for(int i=0; i<sp.size(); i++){
            if(sp.valueAt(i)){
                Log.d(TAG, "start_trip: key at" + sp.keyAt(i));
                database.myDAO().insertTripLocations(new TripLocation(tripId, sp.keyAt(i)));
            }
        }


        Intent intent = new Intent(this, TripActivity.class);
        intent.putExtra("tripId", tripId);
        Log.d(TAG, "start_trip: tripId" + tripId);
        startActivity(intent);
    }

}