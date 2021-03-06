package com.csci_4211_mocal.mocal.services;

import com.csci_4211_mocal.mocal.models.AccountInfo;
import com.csci_4211_mocal.mocal.models.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class Conversion {

    public static ArrayList<String> parseWeather(String response) throws JSONException {
        ArrayList<String> forecastIcons = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray forecasts = jsonObject.getJSONArray("list");

        int initial = 0;

        Instant instant = Instant.now();
        LocalDateTime now = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        int hour = now.getHour();

        if (hour < 3)
            initial = 3;
        else if (hour < 6)
            initial = 2;
        else if (hour < 9)
            initial = 1;
        else if (hour < 12)
            initial = 0;
        else if (hour < 15)
            initial = -1;
        else if (hour < 18)
            initial = -2;
        else if (hour < 21)
            initial = -3;
        else
            initial = -4;

        for (int i = 0, j = 0; i < forecasts.length() && j < 5; i += 8, j++) {
            JSONObject day = forecasts.getJSONObject(i);
            JSONObject forecastData = day.getJSONArray("weather").getJSONObject(0);
            String forecast = forecastData.getString("icon");
            forecastIcons.add(forecast);

            if (i == 0) {
                i += initial;
            }
        }

        return forecastIcons;
    }

    public static ArrayList<Event> filterAndOrderEventsByDate(ArrayList<Event> events, Date date) {
        ArrayList<Event> filtered = new ArrayList<>();
        for (Event event : events) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(event.getTimestamp());
            int eventDay = calendar.get(Calendar.DAY_OF_MONTH);
            int eventMonth = calendar.get(Calendar.MONTH);
            int eventYear = calendar.get(Calendar.YEAR);

            calendar.setTime(date);
            int compareDay = calendar.get(Calendar.DAY_OF_MONTH);
            int compareMonth = calendar.get(Calendar.MONTH);
            int compareYear = calendar.get(Calendar.YEAR);

            if (eventDay == compareDay && eventMonth == compareMonth && eventYear == compareYear) {
                filtered.add(event);
            }
        }

        filtered.sort(new Comparator<Event>() {
            @Override
            public int compare(Event a, Event b) {
                return a.getTimestamp().compareTo(b.getTimestamp());
            }
        });

        return filtered;
    }

    public static String getGreeting() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour < 12) {
            return "Good morning,";
        }
        else if (hour < 18) {
            return "Good afternoon,";
        }
        else {
            return "Good evening,";
        }
    }
}
