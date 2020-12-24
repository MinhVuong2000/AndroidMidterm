package com.example.mymap.trip_screen.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymap.database.MyLocation;
import com.example.mymap.R;

import java.util.ArrayList;

public class GalleryActivity extends Fragment {
    private static final String TAG = "GalleryActivity";
    private TextView mTextView;
    private int mLocationId;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_gallery, container, false);
        mRecyclerView =  view.findViewById(R.id.rv_images);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        //MyLocation mLocation = DataLocations.mData.get(mLocationId);

        ArrayList<MyPhoto> myPhotos = new ArrayList<>();
       // ArrayList<String> listUri = mLocation.getmPhotoUri();
//        for(int i=0; i<listUri.size(); i++)
//            myPhotos.add(new MyPhoto(listUri.get(i), mLocation.get_name()+i));

        MyGalleryAdapter adapter = new MyGalleryAdapter(getContext(),myPhotos);
        mRecyclerView.setAdapter(adapter);

    }

    public void open_camera(View view) {
        Intent intent = new Intent(getActivity(), AndroidCameraApi.class);
        intent.putExtra("location_id", mLocationId);
        startActivity(intent);
    }

}