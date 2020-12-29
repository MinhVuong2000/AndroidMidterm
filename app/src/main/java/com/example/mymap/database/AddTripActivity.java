package com.example.mymap.database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymap.R;
import com.example.mymap.trip_screen.gallery.TakePhotoActivity;

import java.util.ArrayList;
import java.util.List;

public class AddTripActivity extends AppCompatActivity implements TripAdapter.OnItemListener{

    private static final String TAG = "AddActivity";
    
    final int REQUEST_IMAGE_CAPTURE = 123;

    private EditText edtText_tripName;
    private TextView textView_path;
    private ImageButton imgbtn_camera;
    private Button btn_add;
    private RecyclerView recyclerView;

    public static MyDatabase database;

    private TripAdapter tripAdapter;
    private List<Trip> mListTrip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        initUi();

        mListTrip = MyDatabase.getInstance(this).myDAO().getListTrip();
        if(mListTrip == null)
            mListTrip = new ArrayList<>();
        tripAdapter = new TripAdapter(mListTrip, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(tripAdapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTrip();
            }
        });
    }

    private void addTrip() {
        String strTripname = edtText_tripName.getText().toString().trim();

        if(TextUtils.isEmpty(strTripname)){
            return;
        }

        Trip trip = new Trip(strTripname);
        MyDatabase.getInstance(this).myDAO().insertTrip(trip);
        Toast.makeText(this, "Add trip successfully", Toast.LENGTH_SHORT).show();

        edtText_tripName.setText("");

        mListTrip = MyDatabase.getInstance(this).myDAO().getListTrip();
        tripAdapter.setData(mListTrip);
    }


    void initUi()
    {
        edtText_tripName = findViewById(R.id.editText_tripName);
        textView_path = findViewById(R.id.textView_path);
        btn_add = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.recyclerView);
    }


    @Override
    public void onItemClick(int position) {
        Trip trip = mListTrip.get(position);
        Intent intent = new Intent(this, TakePhotoActivity.class);
        intent.putExtra("tripId", trip.getTripId());
        startActivity(intent);
    }
}