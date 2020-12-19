package com.example.mymap.trip_screen.gallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymap.R;

import java.io.File;
import java.util.ArrayList;

public class MyGalleryAdapter extends RecyclerView.Adapter<MyGalleryAdapter.MyViewHolder>  {

    @Override
    public MyGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.acitivity_gallery_recyclerview_item, parent, false);
        MyGalleryAdapter.MyViewHolder viewHolder = new MyGalleryAdapter.MyViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyGalleryAdapter.MyViewHolder holder, int position) {
        MyPhoto myPhoto = mMyPhotos.get(position);
        ImageView imageView = holder.mPhotoImageView;

        File file = new File(myPhoto.getUrl());
        Uri imageUri = Uri.fromFile(file);
        Glide.with(mContext)
                .load(imageUri)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return (mMyPhotos.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mPhotoImageView;

        public MyViewHolder(View itemView) {

            super(itemView);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                MyPhoto myPhoto = mMyPhotos.get(position);
                Intent intent = new Intent(mContext, PhotoActivity.class);
                intent.putExtra(PhotoActivity.EXTRA_SPACE_PHOTO, myPhoto);
                mContext.startActivity(intent);
            }
        }
    }

    private ArrayList<MyPhoto> mMyPhotos;
    private Context mContext;

    public MyGalleryAdapter(Context context, ArrayList<MyPhoto> MyPhotos) {
        mContext = context;
        mMyPhotos = MyPhotos;
    }

}
