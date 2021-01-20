package com.example.mymap.home_screen;


import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.app.Fragment;

import android.widget.Button;
import android.widget.Toast;


import com.example.mymap.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.app.AlertDialog;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {


    private int MapTypeNormal = 0;
    private int MapTypeHybrid = 1;
    private int MapTypeSatelite = 2;
    private int ThemeLight = 0;
    private int ThemeDark = 1;
    private int TimelineLight = 0;
    private int TimelineDark = 1;
    private int UnitMile = 0;
    private int UnitKm = 1;

    public static int map_type;
    public static int theme;
    public static int timeline;
    public static int unit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Button btnMapType = findViewById(R.id.map_type);
        Button btnTheme = findViewById(R.id.theme);
        Button btnTimeline = findViewById(R.id.timeline);
        Button btnUnit = findViewById(R.id.unit);

        btnMapType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Map type");
                final String[] datas = {"Normal", "Hybrid", "Satelite"};
                final int[] res = { MapTypeNormal, MapTypeHybrid, MapTypeSatelite };
                final int[] checkedItem = {0};

                builder.setSingleChoiceItems(datas, checkedItem[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItem[0] = which;
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        map_type = res[checkedItem[0]];
                        Toast.makeText(SettingsActivity.this,"Map type: " + datas[checkedItem[0]],
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Theme");
                final String[] datas = {"Light", "Dark"};
                final int[] res = { ThemeLight, ThemeDark };
                final int[] checkedItem = {0};

                builder.setSingleChoiceItems(datas, checkedItem[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItem[0] = which;
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        theme = res[checkedItem[0]];
                        Toast.makeText(SettingsActivity.this,"Theme: " + datas[checkedItem[0]],
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Timeline");
                final String[] datas = {"Light", "Dark"};
                final int[] res = { TimelineLight, TimelineDark };
                final int[] checkedItem = {0};

                builder.setSingleChoiceItems(datas, checkedItem[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItem[0] = which;
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timeline = res[checkedItem[0]];
                        Toast.makeText(SettingsActivity.this,"Timeline: " + datas[checkedItem[0]],
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Unit");
                final String[] datas = {"Mile", "Kilometer"};
                final int[] res = { UnitMile, UnitKm };
                final int[] checkedItem = {0};

                builder.setSingleChoiceItems(datas, checkedItem[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItem[0] = which;
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        unit = res[checkedItem[0]];
                        Toast.makeText(SettingsActivity.this,"Unit: " + datas[checkedItem[0]],
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}