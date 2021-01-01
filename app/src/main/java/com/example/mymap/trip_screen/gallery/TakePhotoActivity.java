package com.example.mymap.trip_screen.gallery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mymap.R;
import com.example.mymap.database.MyDatabase;
import com.example.mymap.database.TripPhoto;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TakePhotoActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 123 ;
    private RecyclerView mRecyclerView;
    private ImageButton ibtn_camera;
    private int mTripId;
    private List<String> mListPhotoPath;
    private MyPhotoAdapter myPhotoAdapter;
    String currentPhotoPath;

    private static final String TAG = "TakePhotoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_to_trip_actitivy);

        mTripId = getIntent().getIntExtra("tripId", 0);
        Log.d(TAG, "onCreate: tripId: " + mTripId);


        ibtn_camera = findViewById(R.id.ibtn_camera);

        mListPhotoPath = new ArrayList<>();
        mListPhotoPath = MyDatabase.getInstance(this).myDAO().getListPhotoPathFromTrip(mTripId);
        myPhotoAdapter = new MyPhotoAdapter(mListPhotoPath);

        mRecyclerView = findViewById(R.id.recyclerView_test);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(myPhotoAdapter);

        ibtn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

    }

    private void addPhotoToTrip() {
        String photoPath = currentPhotoPath;

        if(TextUtils.isEmpty(photoPath)){
            Toast.makeText(this, "There no photo to add", Toast.LENGTH_SHORT).show();
            return;
        }

        TripPhoto photo = new TripPhoto(mTripId, photoPath);
        MyDatabase.getInstance(this).myDAO().insertPhoto(photo);
        Toast.makeText(this, "Add photo successfully", Toast.LENGTH_SHORT).show();

        mListPhotoPath = MyDatabase.getInstance(this).myDAO().getListPhotoPathFromTrip(mTripId);
        myPhotoAdapter.setData(mListPhotoPath);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_CANCELED){
                File file = new File(currentPhotoPath);
                file.delete();
                currentPhotoPath = "";
                Toast.makeText(this, "there no photo", Toast.LENGTH_SHORT).show();
            }
            if(resultCode == RESULT_OK){
                addPhotoToTrip();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}