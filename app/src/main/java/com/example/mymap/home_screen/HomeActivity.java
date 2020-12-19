package com.example.mymap.home_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mymap.DataLocations;
import com.example.mymap.R;
import com.example.mymap.trip_screen.TripActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    //ArrayList<MyLocation> _listLocations;
    private ListView _listView;
    MyLocationAdapter _myLocationAdapter;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toast.makeText(getApplicationContext(),"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
        setTitle("Choose Your Destinations");
        initData();
        initListView();
        initButton();


    }

    private void initListView() {
        _myLocationAdapter = new MyLocationAdapter(this, R.layout.home_activity_listview_item, DataLocations.mData);
        _listView = findViewById(R.id.listView);
        _listView.setAdapter(_myLocationAdapter);
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MyLocationInfoScreen.class);
                intent.putExtra("location_idx", position);
                startActivity(intent);
            }

        });
    }


    private void initData() {
        DataLocations.GenerateData(getBaseContext());
    }


    private void initButton() {
        this.button = (Button)findViewById(R.id.button);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                let_go();
            }
        });
    }

    private void let_go() {
        SparseBooleanArray sp = _myLocationAdapter.get_checkStates();
        ArrayList<Integer> picked_locations_idx = new ArrayList<>();
        for(int i=0; i<sp.size(); i++){
            if(sp.valueAt(i)){
                picked_locations_idx.add(sp.keyAt(i));
            }
        }

        Log.d(TAG, "num picked "+picked_locations_idx.size());
        for(int i=0; i<picked_locations_idx.size(); i++)
            Log.d(TAG, "picked "+ picked_locations_idx.get(i));
        Intent intent = new Intent(this, TripActivity.class);
        intent.putIntegerArrayListExtra("picked_locations_idx",picked_locations_idx);
        intent.putExtra("roundIntent", 1);
        startActivity(intent);
    }

}