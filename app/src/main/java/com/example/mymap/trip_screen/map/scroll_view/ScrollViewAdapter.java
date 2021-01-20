package com.example.mymap.trip_screen.map.scroll_view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.mymap.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

public class ScrollViewAdapter extends PagerAdapter {
    private List<Item> data;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private boolean mIsTwoWay;
    GoogleMap map;

    public ScrollViewAdapter(List<Item> data, Context context, GoogleMap map) {
        this.data = data;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mIsTwoWay = false;
        this.map = map;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final View layout;
        Log.d("Maps", "instantiateItem: "+position);

        layout= mLayoutInflater.inflate(R.layout.slide_place_item,
                container, false);

        ImageView imageView = layout.findViewById(R.id.imagePlace);
        TextView name = layout.findViewById(R.id.namePlace);
        TextView state = layout.findViewById(R.id.statedPlace);

        Glide.with(mContext)
                .load(data.get(position).getIcon())
                .into(imageView);
        name.setText(data.get(position).getName());
        state.setText(data.get(position).getState());
        map.animateCamera(CameraUpdateFactory.newLatLng(data.get((position+1)%getCount()).getLatLng()));

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
