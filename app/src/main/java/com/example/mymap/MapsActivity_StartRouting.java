package com.example.mymap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.Route;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity_StartRouting extends AppCompatActivity {

    GoogleMap mMap;

    private TextView textShowRouting;
    private Button btnGotoRouting;
    private ImageButton btnCurLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps__start_routing);

        textShowRouting = (TextView) findViewById(R.id.textShowRouting);
        btnGotoRouting = (Button) findViewById(R.id.btnGoToRouting);
        btnCurLocation = (ImageButton) findViewById(R.id.btnCurLocation);

        Intent intent = this.getIntent();

    }

    @Override
    protected void onResume() {
        super.onResume();
        btnGotoRouting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity_StartRouting.this,MapsActivity.class);
                intent.putIntegerArrayListExtra("picked_locations_idx",MapsActivity.locations_idx);
                intent.putExtra("roundIntent", MapsActivity.roundIntent);
                startActivity(intent);
            }
        });
    }
}