package com.csci_4211_mocal.mocal.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.csci_4211_mocal.mocal.R;
import com.csci_4211_mocal.mocal.models.AccountInfo;

public class Network {
    Context context;

    public interface WeatherCallback {
        void onWeatherResponse(String res);
    }

    public interface LoginCallback {
        void onLoginResponse(String res);
    }

    public Network(Context context) {
        this.context = context;
    }

    public void getWeather (double lat, double lon, WeatherCallback callback) {
        String endpoint = context.getString(R.string.weather_api_url);
        String params = "?lat=" + lat + "&lon=" + lon + "&apikey=" + context.getString(R.string.api_token);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest requestString = new StringRequest (
                Request.Method.GET,
                endpoint + params,
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

    public AccountInfo login(String username, String password, LoginCallback callback) {
        String endpoint = context.getString(R.string.api_url);
        String params = "/accounts/login";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest requestString = new StringRequest(
                Request.Method.POST,
                endpoint + params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onLoginResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("info", "Error: " + error.toString());
                        callback.onLoginResponse(error.toString());
                    }
                }
        );

        return new AccountInfo("", "", "");
    }
}
