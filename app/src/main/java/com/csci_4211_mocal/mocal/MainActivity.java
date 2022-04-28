package com.csci_4211_mocal.mocal;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csci_4211_mocal.mocal.models.UserData;
import com.csci_4211_mocal.mocal.services.Conversion;
import com.csci_4211_mocal.mocal.services.DataManager;
import com.csci_4211_mocal.mocal.services.GPS;
import com.csci_4211_mocal.mocal.services.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.ItemListener {
    private DataManager dataManager;
    private UserData userData;
    private TextView textViewMonthYear;
    private RecyclerView recyclerViewCalender;
    private LocalDate selectedDate;
    private ArrayList<String> forecasts;

    Network.WeatherCallback weatherCallback = new Network.WeatherCallback() {
        @Override
        public void onWeatherResponse(String res) {
            try {
                forecasts = Conversion.parseWeather(res);
                layoutMonth();
            }
            catch(JSONException e) {
                Log.i("Weather", e.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        dataManager = new DataManager(this);
//        userData = dataManager.load();
//        if (userData == null) {
//            userData = new UserData(new ArrayList<>());
//            dataManager.update(userData);
//        }

        recyclerViewCalender = findViewById(R.id.recyclerViewCalendar);
        textViewMonthYear = findViewById(R.id.textViewMonthYear);
        forecasts = new ArrayList<>();

        selectedDate = LocalDate.now();
        layoutMonth();

        GPS gps = new GPS(this);
        Location location =  gps.getLocation();

        if (location != null) {
            Network network = new Network(this);
            network.getWeather(location.getLatitude(), location.getLongitude(), weatherCallback);
        }
    }

    public void previousMonthClicked(View view) {
        selectedDate = selectedDate.minusMonths(1);
        layoutMonth();
    }

    public void nextMonthClicked(View view) {
        selectedDate = selectedDate.plusMonths(1);
        layoutMonth();
    }

    private void layoutMonth() {
        textViewMonthYear.setText(getMonthYear(selectedDate));
        ArrayList<String> days = getDays(selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(userData, this, days, selectedDate.getMonth(), forecasts, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        recyclerViewCalender.setLayoutManager(layoutManager);
        recyclerViewCalender.setAdapter(calendarAdapter);
    }

    private String getMonthYear(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    private ArrayList<String> getDays(LocalDate date) {
        ArrayList<String> days = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int numDays = yearMonth.lengthOfMonth();

        LocalDate first = selectedDate.withDayOfMonth(1);
        int weekDay = first.getDayOfWeek().getValue();

        for (int i = 0; i < 42; i++) {
            if (i < weekDay || i >= numDays + weekDay) {
                days.add("");
            }
            else {
                days.add(String.valueOf(i - weekDay + 1));
            }
        }
        return days;
    }

    @Override
    public void itemClicked(int position, String day) {
        if (day.equals("")) {

        } else {

        }
    }
}