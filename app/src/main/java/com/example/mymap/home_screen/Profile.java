package com.example.mymap.home_screen;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mymap.R;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        int x1 = 2, x2 = 2, x3 = 5;

        User user = new User(Uri.parse("android.resource://" + getPackageName() + "/drawable/" + "avatar"),
                R.drawable.background,x1,x2,x3);

        FrameLayout f = findViewById(R.id.framelayout);
        f.setBackgroundResource(user.get_background());

        ImageView avatar = (ImageView)findViewById(R.id.avt);
        avatar.setImageURI(user.get_avatar());

        TextView trip1 = (TextView)findViewById(R.id.tr1);
        TextView trip2 = (TextView)findViewById(R.id.tr2);
        TextView trip3 = (TextView)findViewById(R.id.tr3);

        trip1.setText(Integer.toString(user.get_doneTrip()));
        trip2.setText(Integer.toString(user.get_doingTrip()));
        trip3.setText(Integer.toString(user.get_countTrip()));

    }
}