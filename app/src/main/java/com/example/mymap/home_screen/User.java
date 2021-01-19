package com.example.mymap.home_screen;

import android.graphics.drawable.Drawable;
import android.net.Uri;

public class User {
    private Uri _avatar;
    private int _background;
    private int _doneTrip;
    private int _doingTrip;
    private int _countTrip;

    User(Uri a, int b, int x, int y, int z)
    {
        _avatar = a;
        _background = b;
        _doneTrip = x;
        _doingTrip = y;
        _countTrip = z;
    }

    public Uri get_avatar() {
        return _avatar;
    }

    public int get_background() {
        return _background;
    }

    public int get_doneTrip() {
        return _doneTrip;
    }

    public int get_doingTrip() {
        return _doingTrip;
    }

    public int get_countTrip() {
        return _countTrip;
    }
}
