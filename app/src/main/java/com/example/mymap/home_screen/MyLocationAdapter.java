package com.example.mymap.home_screen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mymap.database.MyLocation;
import com.example.mymap.R;

import java.util.ArrayList;

public class MyLocationAdapter extends ArrayAdapter<MyLocation>  implements CompoundButton.OnCheckedChangeListener {
    Context _context;
    int _layoutResourceID;
    ArrayList<MyLocation> _locations = null;
    SparseBooleanArray _checkStates;

    public MyLocationAdapter(@NonNull Context context, int textViewResourceId,
                             @NonNull ArrayList<MyLocation> objects) {
        super(context, textViewResourceId, objects);
        _context = context;
        _layoutResourceID = textViewResourceId;
        _locations = objects;
        _checkStates = new SparseBooleanArray(_locations.size());
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(_context);
            convertView = layoutInflater.inflate(_layoutResourceID, parent, false);

            holder = new ViewHolder();

            holder.pictureView = (ImageView)convertView.findViewById(R.id.imgView_locationPicture);
            holder.locationName = (TextView)convertView.findViewById(R.id.textView_locationName);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.btn_checkbox);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyLocation location = _locations.get(position);

        Bitmap avt = BitmapFactory.decodeResource(_context.getResources(), location.get_pictureID());
        holder.pictureView.setImageBitmap(avt);
        holder.locationName.setText(location.get_name());

        holder.checkBox.setTag(position);
        holder.checkBox.setChecked(_checkStates.get(position, false));
        holder.checkBox.setOnCheckedChangeListener(this);

        return convertView;
    }

    public SparseBooleanArray get_checkStates() {
        return _checkStates;
    }

    @Override
    public int getCount() {
        return _locations.size();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        _checkStates.put((Integer) buttonView.getTag(), isChecked);
    }

    static class ViewHolder{
        ImageView pictureView;
        TextView locationName;
        CheckBox checkBox;
    }

}

