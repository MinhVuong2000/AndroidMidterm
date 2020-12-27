package com.example.mymap.home_screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.mymap.R;
import com.example.mymap.database.MyDatabase;
import com.example.mymap.database.MyLocation;
import com.example.mymap.database.Trip;
import com.example.mymap.database.TripLocation;
import com.example.mymap.trip_screen.TripActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChooseLocationActivity extends AppCompatActivity {
    private static final String TAG = "ChooseLocationActivity";

    public static ArrayList<MyLocation> mLocationsArrayList;
    public static DatabaseReference firebaseReference;
    MyDatabase database;

    private ListView mListView;
    private Button mBtn_startTrip;
    MyLocationAdapter mLocationAdapter;

    String mTripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Choose Your Destinations");

        mTripName = getIntent().getStringExtra("TripName");
        database = MyDatabase.getInstance(this);

        

        initData();


    }

    private void initUI() {
        Log.d(TAG, "initUI: ");
        mLocationAdapter = new MyLocationAdapter(this, R.layout.home_activity_listview_item, mLocationsArrayList);
        mListView = findViewById(R.id.listView);
        mListView.setAdapter(mLocationAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), LocationInfoActivity.class);
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
        mLocationsArrayList = new ArrayList<>();
        firebaseReference = FirebaseDatabase.getInstance().getReference("myLocation");
        firebaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot di:dataSnapshot.getChildren()){
                    mLocationsArrayList.add(di.getValue(MyLocation.class));
                }
                initUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }



    private void start_trip() {
        Log.d(TAG, "start_trip: ");
        Trip new_trip = new Trip(mTripName);
        long rowId = database.myDAO().insertTrip(new_trip);
        int tripId = database.myDAO().getTripIdFromRowId(rowId);

        SparseBooleanArray sp = mLocationAdapter.getCheckStates();
        for(int i=0; i<sp.size(); i++){
            if(sp.valueAt(i)){
                Log.d(TAG, "start_trip: tripId" + tripId + "locationId" + sp.keyAt(i));
                TripLocation t = new TripLocation(tripId, sp.keyAt(i));
                database.myDAO().insertTripLocations(t);
            }
        }


        Intent intent = new Intent(this, TripActivity.class);
        intent.putExtra("tripId", tripId);
        startActivity(intent);
    }

}