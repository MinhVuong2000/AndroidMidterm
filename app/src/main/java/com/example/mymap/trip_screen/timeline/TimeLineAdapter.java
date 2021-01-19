package com.example.mymap.trip_screen.timeline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.VectorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.example.mymap.R;
import com.example.mymap.database.MyLocation;
import com.example.mymap.database.TripLocation;
import com.github.vipulasri.timelineview.TimelineView;
import com.google.android.material.drawable.DrawableUtils;

import java.util.ArrayList;
import java.util.Date;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {
    private static final String TAG = "TimeLineAdapter";
    Context mContext;
    ArrayList<TripLocation> mListTripLocation;
    ArrayList<MyLocation> mListLocation;


    public TimeLineAdapter(Context mContext, ArrayList<TripLocation> mListTripLocation, ArrayList<MyLocation> mListLocation) {
        this.mContext = mContext;
        this.mListTripLocation = mListTripLocation;
        this.mListLocation = mListLocation;
    }

    public void setData(ArrayList<TripLocation> list)
    {
        mListTripLocation = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_timeline, parent, false);
        return new TimeLineViewHolder(view, viewType);
    }


    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {
        TripLocation tripLocation = mListTripLocation.get(position);

        if(tripLocation.getTimePassed() == null) {
            holder.mTimelineView.setMarker(mContext.getDrawable(R.drawable.ic_marker_inactive));
            holder.mTimelineView.setLineStyle(1); //dash
            holder.mTv_message.setText("Sắp đến " + mListLocation.get(mListTripLocation.get(position).getLocationId()));
            int color = mContext.getColor(R.color.colorWhite);
            holder.mCardView.setCardBackgroundColor(color);
        }
        else{
        holder.mTimelineView.setMarker(mContext.getDrawable(R.drawable.ic_marker_active));
        Date date = tripLocation.getTimePassed();
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        holder.mTv_date.setText("Time: " + df.format("yyyy-MM-dd hh:mm a", date));
        holder.mTv_message.setText("Đã đến " + mListLocation.get(mListTripLocation.get(position).getLocationId()));

        }
    }

    @Override
    public int getItemCount() {
        if(mListTripLocation != null)
            return mListTripLocation.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public TimelineView mTimelineView;
        public TextView mTv_date;
        public TextView mTv_message;
        public CardView mCardView;
        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            mTimelineView = itemView.findViewById(R.id.timeline);
            mTimelineView.initLine(viewType);
            mTv_date = itemView.findViewById(R.id.tv_date);
            mTv_message = itemView.findViewById(R.id.tv_title);
            mCardView = itemView.findViewById(R.id.cardView_item);
        }
    }
}
