package com.example.mymap.trip_screen.timeline;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymap.R;
import com.example.mymap.database.MyDatabase;

import com.example.mymap.database.TripLocation;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.mymap.home_screen.HomeActivity.mLocationsArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeLineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeLineFragment extends Fragment {

    private static final String TAG = "TimeLineFragment";
    private static final String ARG_PARAM1 = "tripId";


    private RecyclerView mRecyclerView;
    private int mTripId;
    private ArrayList<TripLocation> mListTripLocation;
    private MyDatabase DB;
    private TimeLineAdapter mTimelineAdapter;
    public TimeLineFragment() {
        // Required empty public constructor
    }

    public static TimeLineFragment newInstance(int param1) {
        TimeLineFragment fragment = new TimeLineFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            initData();
            mTimelineAdapter.setData(mListTripLocation);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = MyDatabase.getInstance(getContext());
        mTripId = getArguments().getInt(ARG_PARAM1, 1);

        initData();
        mTimelineAdapter = new TimeLineAdapter(getContext(), mListTripLocation, mLocationsArrayList);
    }

    private void initData() {
        List list = DB.myDAO().getListTripLocationFromTrip(mTripId);
        mListTripLocation = new ArrayList<>(list);
        Log.d(TAG, "initData: " + mListTripLocation.size());
    }

//    private void initDump() {
//        Log.d(TAG, "initDump: ");
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2018, 11, 31, 10, 01);
//        Date date1 = calendar.getTime();
//        calendar.set(2018, 11, 31, 12, 00);
//        Date date2 = calendar.getTime();
//        mListTripLocation = new ArrayList<>();
//        mListTripLocation.add(new TripLocation(1,11, order, date1));
//        mListTripLocation.add(new TripLocation(1,12, order, date2));
//        mListTripLocation.add(new TripLocation(1,13));
//        mListTripLocation.add(new TripLocation(1,14));
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView_timeline);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mTimelineAdapter);
        return view;
    }

}