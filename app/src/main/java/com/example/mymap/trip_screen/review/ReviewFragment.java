package com.example.mymap.trip_screen.review;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mymap.R;
import com.example.mymap.database.MyDatabase;
import com.example.mymap.database.Trip;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewFragment extends Fragment {

    private static final String TAG = "ReviewFragment";
    private static final String ARG_PARAM1 = "tripId";

    private int mTripId;
    private TextView mTvReview;
    private RatingBar mRateBar;
    private Trip mTrip;
    public ReviewFragment() {
    }

    public static ReviewFragment newInstance(Integer param1) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTripId = getArguments().getInt(ARG_PARAM1);
            Log.d(TAG, "onCreate: " + mTripId);
        }

        mTrip = MyDatabase.getInstance(getContext()).myDAO().getTrip(mTripId);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        mTvReview = view.findViewById(R.id.tv_review);
        mRateBar = view.findViewById(R.id.rb_review);
        mTvReview.setText(mTrip.getReview());
        mRateBar.setRating(mTrip.getReviewStar());
        return view;
    }
}