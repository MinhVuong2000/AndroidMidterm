package com.example.mymap.home_screen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymap.R;
import com.example.mymap.database.MyDatabase;
import com.example.mymap.database.Trip;
import com.example.mymap.trip_screen.TripActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PendingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PendingFragment extends Fragment implements TripAdapter.OnItemListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView mRecyclerView;
    private ArrayList<Trip> mListTrip;
    private TripAdapter mTripAdapter;

    public PendingFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PendingFragment newInstance() {
        PendingFragment fragment = new PendingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        List<Trip> list = MyDatabase.getInstance(getContext()).myDAO().getListTripPending();
        mListTrip = new ArrayList<>(list);
        mTripAdapter.setData(mListTrip);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView_pendingFragment);

        List<Trip> list = MyDatabase.getInstance(getContext()).myDAO().getListTripPending();
        mListTrip = new ArrayList<>(list);
        mTripAdapter = new TripAdapter(list,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mTripAdapter);
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Trip trip = mListTrip.get(position);
        Intent intent = new Intent(getContext(), TripActivity.class);
        intent.putExtra("tripId", trip.getTripId());
        startActivity(intent);
    }
}