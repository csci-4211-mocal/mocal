package com.csci_4211_mocal.mocal.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Network {
    Context context;

    public interface WeatherCallback {
        void onWeatherResponse(String res);
    }

    public Network(Context context) {
        this.context = context;
    }

    public void getWeather (double lat, double lon, WeatherCallback callback) {
        String endpoint = "https://api.openweathermap.org/data/2.5/forecast";
        String location = "?lat=" + lat + "&lon=" + lon + "&apikey=bc5175d04ffa12b84dbe6434edd06817";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest requestString = new StringRequest (
                Request.Method.GET,
                endpoint + location,
                new Response.Listener<String>() {
                    public void onResponse (String response) {
                        callback.onWeatherResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse (VolleyError error) {
                        Log.i("info", "Error: " + error.toString());
                        callback.onWeatherResponse(error.toString());
                    }
                }
        );

        requestQueue.add(requestString);
    }
}
