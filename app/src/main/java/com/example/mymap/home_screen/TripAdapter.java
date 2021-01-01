package com.example.mymap.home_screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymap.R;
import com.example.mymap.database.Trip;

import java.util.Date;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private List<Trip> mListTrip;
    private OnItemListener mOnItemListener;

    public TripAdapter(List<Trip> mListTrip, OnItemListener mOnItemListener) {
        this.mListTrip = mListTrip;
        this.mOnItemListener = mOnItemListener;
    }

    public void setData(List<Trip> list){
        this.mListTrip = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = mListTrip.get(position);
        if(trip == null)
            return;
        holder.tvTripName.setText(trip.getTripName());
        Date date = trip.getDateCreate();
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        holder.tvDate.setText("" + df.format("yyyy-MM-dd hh a", date));
    }

    @Override
    public int getItemCount() {
        if(mListTrip!=null){
            return mListTrip.size();
        }
        return 0;
    }

    public class TripViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTripName;
        private TextView tvDate;
        private OnItemListener onItemListener;
        public TripViewHolder(@NonNull View itemView, OnItemListener listener) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_dateCreate);
            tvTripName = itemView.findViewById(R.id.tv_tripName);
            onItemListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    private interface OnItemListener{
        void onItemClick(int position);
    }
}
