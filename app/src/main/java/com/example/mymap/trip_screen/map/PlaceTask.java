package com.example.mymap.trip_screen.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mymap.R;
import com.example.mymap.trip_screen.map.MyJsonParser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.mymap.trip_screen.map.MapsFragment.iconItems;

public class PlaceTask extends AsyncTask<String,Integer,String> {
    GoogleMap mMap;
    Fragment mActivity;
    int mPosition;
    ArrayList<String> place_idArray;
    ArrayList<Marker> markersItems;

    public PlaceTask(GoogleMap mMap, Fragment activity, int position){
        this.mMap = mMap;
        this.mActivity = activity;
        this.mPosition = position;
        place_idArray = new ArrayList<>();
        markersItems = null;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = null;
        try {
            data = downloadUrl(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        new ParserTask().execute(s);
    }


    private String downloadUrl(String string) throws IOException {
        URL url = new URL(string);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        String data = builder.toString();
        reader.close();
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            MyJsonParser myJsonParser = new MyJsonParser();
            List<HashMap<String, String>> mapList = null;
            JSONObject object = null;
            try {
                object = new JSONObject(strings[0]);
                mapList = myJsonParser.parseResult(object, mPosition == -1 ? true : false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            if (mPosition != -1) {
                if (markersItems != null) {
                    for (int i = 0; i < markersItems.size(); i++)
                        markersItems.get(i).remove();
                }
                markersItems = new ArrayList<>();
                for (int i = 0; i < hashMaps.size(); i++) {
                    HashMap<String, String> hashMapList = hashMaps.get(i);
                    double lat = Double.parseDouble(hashMapList.get("lat"));
                    double lng = Double.parseDouble(hashMapList.get("lng"));
                    String name = hashMapList.get("name");
                    String place_id = hashMapList.get("place_id");
                    LatLng latLng = new LatLng(lat, lng);
                    MarkerOptions options = new MarkerOptions().position(latLng)
                            .title(name)
                            .icon(bitmapDescriptorFromVector(mActivity.getActivity(), iconItems[mPosition]));
                    place_idArray.add(place_id);
                    markersItems.add(mMap.addMarker(options));
                }
                setUpClickDetailsPlace(mMap);
                //mMap.animateCamera(CameraUpdateFactory.newLatLng(markersItems.get(0).getPosition()));
            }
            else{
                ArrayList<String> photos = new ArrayList<>();
                ArrayList<String> infos = new ArrayList<>();
                for (int i = 0; i < hashMaps.size()-1; i++) {
                    HashMap<String, String> hashMapList = hashMaps.get(i);
                    String height = hashMapList.get("height");
                    String photo_reference = hashMapList.get("photo_reference");
                    String urlPhoto = "https://maps.googleapis.com/maps/api/place/photo" +
                            "?maxheight=" + height +
                            "&photoreference=" + photo_reference +
                            "&key=" + mActivity.getResources().getString(R.string.google_maps_key);
                    photos.add(urlPhoto);
                }
                HashMap<String, String> hashMapList = hashMaps.get(hashMaps.size()-1);
                String name = hashMapList.get("name");
                String formatted_address = hashMapList.get("formatted_address");
                String formatted_phone_number = hashMapList.get("formatted_phone_number");
                String rating = hashMapList.get("rating");
                String user_ratings_total = hashMapList.get("user_ratings_total");
                String url = hashMapList.get("url");
                String open_now = hashMapList.get("open_now");

                infos.add(name);
                infos.add(formatted_address);
                infos.add(formatted_phone_number);
                infos.add(rating);
                infos.add(user_ratings_total);
                infos.add(url);
                infos.add(open_now);

                Intent intent = new Intent(mActivity.getActivity(),DetailsPlace.class);
                intent.putStringArrayListExtra("infos", infos);
                intent.putStringArrayListExtra("photos",photos);
                mActivity.startActivity(intent);
            }
    }

        private void setUpClickDetailsPlace(GoogleMap map) {
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    int place_id = markersItems.indexOf(marker);
                    String url = "https://maps.googleapis.com/maps/api/place/details/json" +
                            "?place_id=" + place_idArray.get(place_id) +
                            "&fields=formatted_phone_number,formatted_address,opening_hours,website,url,price_level,rating,name,user_ratings_total,review,photo" +
                            "&key=" + mActivity.getResources().getString(R.string.google_maps_key);

                    Log.d("Maps", "getDetailPlace: "+ url);
                    //exe place task method to download json data
                    new PlaceTask(mMap,mActivity, -1).execute(url);
                    return false;
                }
            });
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
