package com.example.mymap.home_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.mymap.DataLocations;
import com.example.mymap.database.MyLocation;
import com.example.mymap.R;

import java.util.ArrayList;

public class MyLocationInfoScreen extends AppCompatActivity {

    private static final String TAG = "LocationInfoScreen";
    private ArrayList<Integer> mListImages = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_info_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int location_idx = intent.getIntExtra("location_idx", 0);
        MyLocation location = DataLocations.mData.get(location_idx);
        setTitle(location.get_name());
        mListImages = location.get_listImages();

        final ViewFlipper viewFlipper = findViewById(R.id.infoScreen_viewFlipper);
        for(int i=0; i<mListImages.size(); i++)
        {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(mListImages.get(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        TextView textView = findViewById(R.id.infoScreen_textView2);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(location.get_info());

        final ImageButton btn_next = (ImageButton)findViewById(R.id.infoScreen_btn_next);
        final ImageButton btn_previous = (ImageButton)findViewById(R.id.infoScreen_btn_previous);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showNext();

            }
        });
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showPrevious();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}