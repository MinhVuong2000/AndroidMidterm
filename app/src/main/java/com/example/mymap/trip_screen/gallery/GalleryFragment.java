package com.example.mymap.trip_screen.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymap.R;
import com.example.mymap.database.MyDatabase;
import com.example.mymap.database.MyPhotoAdapter;
import com.example.mymap.trip_screen.TripActivity;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    private static final String TAG = "GalleryActivity";
    private TextView mTextView;
    private int mLocationId;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mRecyclerView =  view.findViewById(R.id.rv_images);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mListPhotoPath = new ArrayList<>();
//        mListPhotoPath = MyDatabase.getInstance(getContext()).myDAO().getListPhotoPathFromTrip(TripActivity.mTrip);
//        myPhotoAdapter = new MyPhotoAdapter(mListPhotoPath);
//
//        mRecyclerView = findViewById(R.id.recyclerView_test);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setAdapter(myPhotoAdapter);
//
//        ibtn_camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dispatchTakePictureIntent();
//            }
//        });

    }

    public void open_camera(View view) {
        Intent intent = new Intent(getActivity(), AndroidCameraApi.class);
        intent.putExtra("location_id", mLocationId);
        startActivity(intent);
    }

}