package com.example.mymap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.mymap.database.MyDatabase;
import com.example.mymap.home_screen.HomeActivity;

public class FinishTrip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_trip);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        int mTripId = getIntent().getIntExtra("tripId", 0);

        setTitle(MyDatabase.getInstance(this).myDAO().getTripName(mTripId));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText editTextComment = (EditText) findViewById(R.id.rating_comment);
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        Button btRate = (Button) findViewById(R.id.finish_trip);

        btRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double stars = Double.parseDouble(String.valueOf(ratingBar.getRating()));
                String comment = editTextComment.getText().toString();
                //Log.d("RatingDebug", "Stars = " + stars);
                //Log.d("RatingDebug", "Comment = " + comment);
                if (stars == 0.0)
                    addNotRateYetDialog();
                else{
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
            }

        });
    }

    private void addNotRateYetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn chưa đánh ");
        builder.setMessage("Bạn có thể không đánh giá bằng chữ nhưng cần đánh giá bằng sao.");
        builder.setCancelable(true);
        builder.setPositiveButton( "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}