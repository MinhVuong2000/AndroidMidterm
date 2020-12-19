package com.example.mymap.trip_screen.gallery;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mymap.R;

public class MyPhoto implements Parcelable {

    private String mUrl;
    private String mTitle;
    private int mId;

    public MyPhoto(String url, String title) {
        mUrl = url;
        mTitle = title;
    }
    public MyPhoto(int id, String title) {
        mId = id;
        mTitle = title;
    }

    protected MyPhoto(Parcel in) {
        mUrl = in.readString();
        mTitle = in.readString();
    }

    public static final Creator<MyPhoto> CREATOR = new Creator<MyPhoto>() {
        @Override
        public MyPhoto createFromParcel(Parcel in) {
            return new MyPhoto(in);
        }

        @Override
        public MyPhoto[] newArray(int size) {
            return new MyPhoto[size];
        }
    };

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public static MyPhoto[] getSpacePhotos() {
            return new MyPhoto[]{
                    new MyPhoto(R.drawable.ben_thanh_1, "benthanh1"),
                    new MyPhoto(R.drawable.ben_thanh_2, "benthanh2"),
                    new MyPhoto(R.drawable.ben_thanh_3, "benthanh3"),
                    new MyPhoto(R.drawable.ben_thanh_3, "benthanh3"),
                    new MyPhoto(R.drawable.ben_thanh_3, "benthanh3"),
                    new MyPhoto(R.drawable.ben_thanh_3, "benthanh3"),
                    new MyPhoto(R.drawable.ben_thanh_3, "benthanh3"),
                    new MyPhoto(R.drawable.ben_thanh_3, "benthanh3"),
                    new MyPhoto(R.drawable.ben_thanh_3, "benthanh3"),

            };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUrl);
        parcel.writeString(mTitle);
    }

    public int getId() {
        return mId;
    }
}
