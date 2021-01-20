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
import com.directions.route.Route;
import com.example.mymap.R;
import com.example.mymap.home_screen.SettingsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ScrollViewAdapter extends PagerAdapter {
    private List<Item> data;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    ArrayList<Route> distanceList;
    GoogleMap map;

    public ScrollViewAdapter(List<Item> data, Context context, GoogleMap map, ArrayList<Route> distance) {
        this.data = data;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.map = map;
        this.distanceList = distance;
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
        TextView distance = layout.findViewById(R.id.distance);

        Glide.with(mContext)
                .load(data.get(position).getIcon())
                .into(imageView);
        name.setText(data.get(position).getName());
        state.setText(data.get(position).getState());
        String distanceText = SettingsActivity.unit==0?""+String.format("%.2f",distanceList.get(position).getDistanceValue()* 0.000621) + " miles":""+ String.format("%.2f",distanceList.get(position).getDistanceValue()*0.001) + " km";
        if (position==0)
            distance.setText("Quảng đường xuất phát từ vị trí ban đầu:\n\t\t\t"+ distanceText);
        else distance.setText("Quảng đường xuất phát từ " + data.get(position-1).getName()+":\n\t\t\t"+ distanceText);

        container.addView(layout);
        return layout;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);

        map.animateCamera(CameraUpdateFactory.newLatLng(data.get((position)%getCount()).getLatLng()));

    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
