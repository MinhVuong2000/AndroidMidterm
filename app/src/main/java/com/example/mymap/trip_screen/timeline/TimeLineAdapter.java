package com.example.mymap.trip_screen.timeline;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymap.R;
import com.example.mymap.database.TripLocation;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.Date;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {
    private static final String TAG = "TimeLineAdapter";
    ArrayList<TripLocation> mListTripLocation;
    Context mContext;
    public TimeLineAdapter(Context context,ArrayList<TripLocation> list)
    {
        mListTripLocation = list;
        mContext = context;
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
        Log.d(TAG, "onBindViewHolder: position: " + position + "TripLocation: "+ tripLocation.getLocationId());
        holder.mTimelineView.setMarker(mContext.getDrawable(R.drawable.ic_marker_active));
        Date date = tripLocation.getTimePassed();
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        holder.mTv_date.setText("Time: " + df.format("yyyy-MM-dd hh:mm a", date));
        holder.mTv_message.setText("Đã đi qua location id: " + tripLocation.getLocationId());
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
        public TimeLineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            mTimelineView = itemView.findViewById(R.id.timeline);
            mTimelineView.initLine(viewType);
            mTv_date = itemView.findViewById(R.id.tv_date);
            mTv_message = itemView.findViewById(R.id.tv_title);
        }
    }
}
