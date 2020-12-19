package com.example.mymap.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymap.R;

import java.util.List;

public class MyPhotoAdapter extends RecyclerView.Adapter<MyPhotoAdapter.PhotoViewHolder> {

    private List<String> mListPhotoPath;

    public MyPhotoAdapter(List<String> mListPhotoPath) {
        this.mListPhotoPath = mListPhotoPath;
    }

    public void setData(List<String> list)
    {
        mListPhotoPath = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        String path = mListPhotoPath.get(position);
        if(path == null)
            return;

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inSampleSize = 16;
        Bitmap bitmap = BitmapFactory.decodeFile(path, opt);
        holder.imageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        if(mListPhotoPath != null)
            return mListPhotoPath.size();
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_photo_test);
        }
    }
}
