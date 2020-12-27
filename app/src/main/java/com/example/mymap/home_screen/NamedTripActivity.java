package com.example.mymap.home_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mymap.R;
import com.example.mymap.database.MyDatabase;
import com.example.mymap.database.Trip;
import com.example.mymap.database.TripLocation;

import java.util.ArrayList;
import java.util.List;

public class NamedTripActivity extends AppCompatActivity {

    private static final String TAG = "NamedTripActivity";
    EditText et_namedTrip;
    Button btn_namedTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
//        List<Trip> list1 = MyDatabase.getInstance(this).myDAO().getListTrip();
//        for(int i=0; i<list1.size(); i++)
//            Log.d(TAG, "tripId: " + list1.get(i).getTripId());
//        List<TripLocation> list = MyDatabase.getInstance(this).myDAO().getListTripLocationFromTrip(2);
//        for (int i =0; i<list.size(); i++)
//            Log.d(TAG, "tripLocation: " + list.get(i).getLocationId());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_named_trip);

        et_namedTrip = findViewById(R.id.et_namedTrip);
        btn_namedTrip = findViewById(R.id.btn_namedTrip);

        et_namedTrip.addTextChangedListener(myWatcher);
        btn_namedTrip.setOnClickListener(onClick);

    }


    TextWatcher myWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nameInput = et_namedTrip.getText().toString().trim();
            btn_namedTrip.setEnabled(!nameInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(), ChooseLocationActivity.class);
            intent.putExtra("TripName", et_namedTrip.getText().toString().trim());
            startActivity(intent);
        }
    };
}