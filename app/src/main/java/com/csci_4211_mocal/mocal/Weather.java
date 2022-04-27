package com.csci_4211_mocal.mocal;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Weather {
    private String date;
    private String temp;
    private String minTemp;
    private String maxTemp;
    private String humidity;
    private String description;
    private String iconURL;

    /**
     * {
     *   "coord": { "lon": 139,"lat": 35},
     *   "weather": [
     *     {
     *       "id": 800,
     *       "main": "Clear",
     *       "description": "clear sky",
     *       "icon": "01n"
     *     }
     *   ],
     *   "base": "stations",
     *   "main": {
     *     "temp": 281.52,
     *     "feels_like": 278.99,
     *     "temp_min": 280.15,
     *     "temp_max": 283.71,
     *     "pressure": 1016,
     *     "humidity": 93
     *   },
     *   "wind": {
     *     "speed": 0.47,
     *     "deg": 107.538
     *   },
     *   "clouds": {
     *     "all": 2
     *   },
     *   "dt": 1560350192,
     *   "sys": {
     *     "type": 3,
     *     "id": 2019346,
     *     "message": 0.0065,
     *     "country": "JP",
     *     "sunrise": 1560281377,
     *     "sunset": 1560333478
     *   },
     *   "timezone": 32400,
     *   "id": 1851632,
     *   "name": "Shuzenji",
     *   "cod": 200
     * }
     */

    public Weather (String weather) {

        //Create JSON Object that we will use to extract data
        try {
            JSONObject jsonObject = new JSONObject(weather);
            long timeStamp = jsonObject.getLong ("dt");
            date = convertTimeStampToDate(timeStamp);

            JSONObject main = jsonObject.getJSONObject("main");
            /* This will extract the following object
             {
     *     "temp": 281.52,
     *     "feels_like": 278.99,
     *     "temp_min": 280.15,
     *     "temp_max": 283.71,
     *     "pressure": 1016,
     *     "humidity": 93
     *   }
             */

            temp = kelvinToFahrenheit(main.getInt("temp"));
            minTemp = kelvinToFahrenheit(main.getInt("temp_min"));
            maxTemp = kelvinToFahrenheit(main.getInt("temp_max"));

            humidity = NumberFormat.getPercentInstance().format(main.getInt("humidity") / 100.00);

            JSONArray weatherList = jsonObject.getJSONArray("weather");
            JSONObject day = weatherList.getJSONObject(0);
            description = day.getString("description");
            iconURL = "http://openweathermap.org/img/w/" + day.getString("icon") + ".png";


        }
        catch (JSONException e) {
            Log.i ("info", e.getMessage());
        }

    }



    private String convertTimeStampToDate (long timeStamp) {
        String dateInString = "";

        Calendar calendar = Calendar.getInstance(); //Create calendar object

        //Set date for calendar. Arguments in milliseconds
        calendar.setTimeInMillis(timeStamp * 1000);

        TimeZone tz = TimeZone.getDefault(); //Get users time zone


        //Date date = new Date(timeStamp);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm");

        dateInString = df.format(calendar.getTime());

        return dateInString;
    }

    private String kelvinToFahrenheit (int temp) {
        String result = "";
        NumberFormat nnf = NumberFormat.getInstance();
        nnf.setMaximumFractionDigits(0);

        double tempF = ((temp - 273.15) * 9 / 5) + 32;

        result = nnf.format(tempF) + "\u00B0F";


        return result;
    }


    public String getDate() {
        return date;
    }

    public String getTemp() {
        return temp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getDescription() {
        return description;
    }

    public String getIconURL() {
        return iconURL;
    }
}
