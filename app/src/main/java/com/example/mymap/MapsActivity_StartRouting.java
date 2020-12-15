package com.example.mymap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

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
                Intent intent = new Intent(MapsActivity_StartRouting.this, MapsFragment.class);
                intent.putIntegerArrayListExtra("picked_locations_idx", MapsFragment.locations_idx);
                intent.putExtra("roundIntent", MapsFragment.roundIntent);
                startActivity(intent);
            }
        });
    }
}