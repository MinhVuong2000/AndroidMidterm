package com.example.mymap.trip_screen.gallery;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mymap.R;

import java.io.File;

public class PhotoActivity extends AppCompatActivity {

    private static final String TAG = "PhotoActivity";
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        mImageView = (ImageView) findViewById(R.id.image);
        String photoPath = getIntent().getStringExtra("photoPath");

        loadWithGlide(photoPath);

    }


    private void loadNormal(String photoPath) {
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
        mImageView.setImageBitmap(bitmap);
    }

    private void loadWithGlide(String photoPath) {

        Glide.with(this)
                .load(new File(photoPath))
                .asBitmap()
                .skipMemoryCache(true)
                .listener(new RequestListener<File, Bitmap>() {

                    @Override
                    public boolean onException(Exception e, File model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, File model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        onPalette(Palette.from(resource).generate());
                        mImageView.setImageBitmap(resource);

                        return false;
                    }

                    public void onPalette(Palette palette) {
                        if (null != palette) {
                            ViewGroup parent = (ViewGroup) mImageView.getParent().getParent();
                            parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY));
                        }
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(mImageView);
    }


}