package com.csci_4211_mocal.mocal;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import com.csci_4211_mocal.mocal.models.Event;
import com.csci_4211_mocal.mocal.models.UserData;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.ItemListener {
    private static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 0;
    private TextView textViewMonthYear;
    private RecyclerView recyclerViewCalender;
    private LocalDate selectedDate;
    private EditText editTextCity;
    private ImageView imageView;
    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCalender = findViewById(R.id.recyclerViewCalendar);
        textViewMonthYear = findViewById(R.id.textViewMonthYear);
        editTextCity = findViewById(R.id.enterCity);
        imageView = findViewById(R.id.imageView);

        selectedDate = LocalDate.now();
        layoutMonth();
        FloatingActionButton fab = findViewById(R.id.submit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = editTextCity.getText().toString();

                if (location.equals("")) {
                    location = "Denver";
                }

                try {
                    String url1 = "https://api.openweathermap.org/data/2.5/weather?q=";
                    String url2 = "&appid=bc5175d04ffa12b84dbe6434edd06817";   //Replace xxxx with your own key
                    String url = url1 + location + url2;


                    sendAndRequestResponse (url);



                }
                catch (Exception e) {
                    Log.i ("info", e.getMessage());
                }
            }
        });
    }
    public void sendAndRequestResponse (String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        /*
        Constructor for StringRequest takes 4 parameters.
         */
        StringRequest requestString = new StringRequest (
                /*
                First parameter specify which method you want to use to make HTTP connection.
                There are two possibilities. We can either use get method or post metho.
                In my example I am using Get method.
                 */
                Request.Method.GET,
                /*
                Second parameter is the URl (in form of a string for the Web server
                 */
                url,
                /*
                Third parameter is Response.Listener object which must override
                the onResponse method
                 */
                new Response.Listener<String>() {
                    public void onResponse (String response) {
                        Weather weather = new Weather (response);
                        layoutMonth();
                        //displayResult(weather);
                    }
                },
                /*
                Fourt parameter is Response.Error listener object which must override
                omErrorResponse method.
                 */
                new Response.ErrorListener() {
                    public void onErrorResponse (VolleyError error) {
                        Log.i("info", "Error: " + error.toString());
                    }
                }
        );

        requestQueue.add(requestString);
    }
    public void displayResult (Weather weather) {

        //Add Picasso in build.gradle
        //Picasso.get().load(weather.getIconURL()).resize(1600,1600)
               // .into(imageView);
        Log.i ("info", "Image Url: " + weather.getIconURL());
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
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, selectedDate.getMonth(), this);

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