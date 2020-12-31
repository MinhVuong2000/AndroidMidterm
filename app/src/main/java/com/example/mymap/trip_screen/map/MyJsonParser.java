package com.example.mymap.trip_screen.map;

import android.util.Log;


import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MyJsonParser {
    static String TAG = "Maps";
    private HashMap<String,String> parseJsonObject(JSONObject object, boolean detailsPlace, boolean parsePhotos){
        HashMap<String,String> dataList = new HashMap<>();
        try {
            if (parsePhotos){
                Log.d(TAG, "parseJsonObject: parsePhoto");
                String height = object.getString("height");
                String width = object.getString("width");
                String photo_reference = object.getString("photo_reference");
                dataList.put("height",height);
                dataList.put("width",width);
                dataList.put("photo_reference",photo_reference);
            }
            else{
                if (detailsPlace){
                    Log.d(TAG, "parseJsonObject: detail");
                    String name = object.getString("name");
                    String formatted_address = object.getString("formatted_address");
                    String formatted_phone_number = object.getString("formatted_phone_number");
                    String open_now = object.getJSONObject("opening_hours").getBoolean("formatted_address")?"Opening":"Closed";
                    String rating = object.getString("rating");
                    String user_ratings_total = object.getString("user_ratings_total");
                    String url = object.getString("url");
                    dataList.put("name",name);
                    dataList.put("formatted_address",formatted_address);
                    dataList.put("formatted_phone_number",formatted_phone_number);
                    dataList.put("rating",rating);
                    dataList.put("user_ratings_total",user_ratings_total);
                    dataList.put("url",url);
                    dataList.put("open_now",open_now);
                }
                else{
                    Log.d(TAG, "parseJsonObject: nearbySearch");
                    String name = object.getString("name");
                    String latitude = object.getJSONObject("geometry")
                            .getJSONObject("location").getString("lat");
                    String longitude = object.getJSONObject("geometry")
                            .getJSONObject("location").getString("lng");
                    String place_id = object.getString("place_id");
                    dataList.put("name",name);
                    dataList.put("lat",latitude);
                    dataList.put("lng",longitude);
                    dataList.put("place_id",place_id);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return dataList;
    }


    private List<HashMap<String,String>> parseJsonArray(JSONArray jsonArray, boolean parsePhotos){
        List<HashMap<String,String>> dataList = new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++){
            try {
                HashMap<String,String> data = parseJsonObject((JSONObject) jsonArray.get(i),false,parsePhotos);
                dataList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return dataList;
    }

    public List<HashMap<String,String>> parsePhotos(JSONObject object){
        JSONArray jsonArray = null;
        try {
            jsonArray = object.getJSONArray("photos");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return parseJsonArray(jsonArray,true);
    }

    public List<HashMap<String,String>> parseResult(JSONObject object, boolean detailsPlace){
        if (detailsPlace){
            JSONObject jsonObject = null;
            try {
                jsonObject = object.getJSONObject("result");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            List<HashMap<String,String>> res = parsePhotos(jsonObject);
            if (res==null)
                res = new ArrayList<>();
            res.add(parseJsonObject(jsonObject,detailsPlace,false));
            return res;
        }
        JSONArray jsonArray = null;
        try {
            jsonArray = object.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return parseJsonArray(jsonArray, false);
    }
}
