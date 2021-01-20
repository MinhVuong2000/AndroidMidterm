package com.example.mymap.trip_screen.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.mymap.R;

import java.util.ArrayList;

public class DetailsPlace extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_place);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final ArrayList<String> info = intent.getStringArrayListExtra("infos");
        ArrayList<String> photos = intent.getStringArrayListExtra("photos");
        if (photos.size()==0)
            photos.add("https://www.google.com/search?q=image+android&sxsrf=ALeKk003Q8eaT7gRhJgljT-Ic6rd-tvLvg:1609401996530&source=lnms&tbm=isch&sa=X&ved=2ahUKEwiK77bS4fftAhXRIqYKHd6TC10Q_AUoAXoECAUQAw&biw=1230&bih=667#imgrc=XKD7TltIpTVYkM");


        final ViewFlipper viewFlipper = findViewById(R.id.infoScreen_viewFlipper);
        for(int i=0; i<photos.size(); i++)
        {
            ImageView imageView = new ImageView(this);
            Glide.with(this).load(photos.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        TextView textViewAddress = findViewById(R.id.address);
        TextView textViewPhone = findViewById(R.id.phone);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView textViewUserRatingTotal = findViewById(R.id.user_rating_total);
        TextView textViewUrl = findViewById(R.id.url);
        TextView textViewOpenNow = findViewById(R.id.openHours);

        this.setTitle(info.get(0));
        textViewAddress.setText(info.get(1));
        textViewPhone.setText(info.get(2));

        if (info.get(2)!="None"){
            textViewPhone.setTextColor(getResources().getColor(R.color.quantum_purple200));
            textViewPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    String tel = "tel:" + info.get(2);
                    intent.setData(Uri.parse(tel));
                    startActivity(intent);
                }
            });
        }
        ratingBar.setRating((float) Double.parseDouble(info.get(3)));
        textViewUserRatingTotal.setText(info.get(4)+" người đã đánh giá");

        textViewUrl.setText(info.get(5));
        textViewUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(info.get(5)));
                startActivity(intent);
            }
        });
        textViewOpenNow.setText(info.get(6));

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