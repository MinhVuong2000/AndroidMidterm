package com.example.mymap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";
    private TextView mTextView;
    private int mLocationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mLocationId = intent.getIntExtra("reached_idx", 0);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_gallery);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_images);
        //Nho change/////////////////
        DataLocations.GenerateData(getBaseContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        MyLocation mLocation = DataLocations.mData.get(mLocationId);

        ArrayList<MyPhoto> myPhotos = new ArrayList<>();
        ArrayList<String> listUri = mLocation.getmPhotoUri();
        for(int i=0; i<listUri.size(); i++)
            myPhotos.add(new MyPhoto(listUri.get(i), mLocation.get_name()+i));

        MyGalleryAdapter adapter = new MyGalleryAdapter(this,myPhotos);
        mRecyclerView.setAdapter(adapter);

        Button btnContinueTrip = findViewById(R.id.btnContinueTrip);
        btnContinueTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GalleryActivity.this,MapsActivity.class);
                intent.putIntegerArrayListExtra("picked_locations_idx",MapsActivity.locations_idx);
                intent.putExtra("roundIntent", MapsActivity.roundIntent);
                startActivity(intent);
            }
        });
    }

    public void open_camera(View view) {
        Intent intent = new Intent(this, AndroidCameraApi.class);
        intent.putExtra("location_id", mLocationId);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_images);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        MyLocation mLocation = DataLocations.mData.get(mLocationId);

        ArrayList<MyPhoto> myPhotos = new ArrayList<>();
        ArrayList<String> listUri = mLocation.getmPhotoUri();
        for(int i=0; i<listUri.size(); i++) {
            myPhotos.add(new MyPhoto(listUri.get(i), mLocation.get_name() + i));
            Log.d(TAG, "Uri" + i + listUri.get(i));
        }
        MyGalleryAdapter adapter = new MyGalleryAdapter(this, myPhotos);
        mRecyclerView.setAdapter(adapter);
    }
}