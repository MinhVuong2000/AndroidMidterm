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
import com.example.mymap.database.MyDatabase;

public class Profile extends AppCompatActivity {

    MyDatabase DB;
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

        TextView trip1 = (TextView)findViewById(R.id.tv_numTripDone);
        TextView trip2 = (TextView)findViewById(R.id.tv_numTripPending);
        TextView trip3 = (TextView)findViewById(R.id.tv_numLocation);

        DB = MyDatabase.getInstance(this);
        trip1.setText("Số chuyến đi đã hoàn thành: " + DB.myDAO().getNumTripDone());
        trip2.setText("Số chuyến đi đang thực hiện: " + DB.myDAO().getNumTripPending());
        trip3.setText("Số địa điểm đã đi qua: " + DB.myDAO().getNumLocationPassed());

    }
}