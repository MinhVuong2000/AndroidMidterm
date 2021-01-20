package com.example.mymap.trip_screen.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymap.R;
import com.example.mymap.database.MyDatabase;
import com.example.mymap.database.TripPhoto;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GalleryFragment extends Fragment implements MyPhotoAdapter.OnItemListener{
    private static final String ARG_PARAM1 = "tripId";
    private static final String ARG_PARAM2 = "isDone";

    private static final String TAG = "GalleryFragment";

    private int mTripId;
    private int mIsDone;
    private List<String> mListPhotoPath;
    private MyPhotoAdapter myPhotoAdapter;
    private RecyclerView mRecyclerView;
    private ImageButton mIBtn_camera;
    private MyDatabase mDB;
    private String currentPhotoPath;


    private static final int REQUEST_IMAGE_CAPTURE = 123 ;

    public GalleryFragment(){}

    public static GalleryFragment newInstance(int param1, int param2){
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mRecyclerView =  view.findViewById(R.id.rv_gallery);
        mIBtn_camera = view.findViewById(R.id.ibtn_openCamera);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(myPhotoAdapter);

        mIBtn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        if(mIsDone == 1) mIBtn_camera.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mTripId = bundle.getInt(ARG_PARAM1,1);
        mIsDone = bundle.getInt(ARG_PARAM2,0);
        mDB = MyDatabase.getInstance(getContext());

        mListPhotoPath = new ArrayList<>();
        mListPhotoPath = MyDatabase.getInstance(getContext()).myDAO().getListPhotoPathFromTrip(mTripId);
        myPhotoAdapter = new MyPhotoAdapter(mListPhotoPath, this);


    }

    private void addPhotoToTrip() {
        String photoPath = currentPhotoPath;

        if(TextUtils.isEmpty(photoPath)){
            Toast.makeText(getContext(), "There no photo to add", Toast.LENGTH_SHORT).show();
            return;
        }

        TripPhoto photo = new TripPhoto(mTripId, photoPath);
        mDB.myDAO().insertPhoto(photo);
        Toast.makeText(getContext(), "Add photo successfully", Toast.LENGTH_SHORT).show();

        mListPhotoPath = mDB.myDAO().getListPhotoPathFromTrip(mTripId);
        myPhotoAdapter.setData(mListPhotoPath);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == getActivity().RESULT_CANCELED){
                File file = new File(currentPhotoPath);
                file.delete();
                currentPhotoPath = "";
            }
            if(resultCode == getActivity().RESULT_OK){
                addPhotoToTrip();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), PhotoActivity.class);
        intent.putExtra("photoPath", mListPhotoPath.get(position));
        startActivity(intent);
    }
}