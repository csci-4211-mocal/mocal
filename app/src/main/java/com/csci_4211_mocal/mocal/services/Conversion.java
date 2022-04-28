package com.csci_4211_mocal.mocal.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Conversion {

    public static ArrayList<String> parseWeather(String response) throws JSONException {
        ArrayList<String> forecastIcons = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray forecasts = jsonObject.getJSONArray("list");
        for (int i = 0; i < forecasts.length(); i+=8) {
            JSONObject day = forecasts.getJSONObject(i);
            JSONObject forecastData = day.getJSONArray("weather").getJSONObject(0);
            String forecast = forecastData.getString("icon");
            forecastIcons.add(forecast);
        }

        return forecastIcons;
    }
}
