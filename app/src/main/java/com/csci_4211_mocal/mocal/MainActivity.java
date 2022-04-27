package com.csci_4211_mocal.mocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.csci_4211_mocal.mocal.models.Event;
import com.csci_4211_mocal.mocal.models.UserData;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.ItemListener {
    private TextView textViewMonthYear;
    private RecyclerView recyclerViewCalender;
    private LocalDate selectedDate;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCalender = findViewById(R.id.recyclerViewCalendar);
        textViewMonthYear = findViewById(R.id.textViewMonthYear);

        selectedDate = LocalDate.now();
        layoutMonth();
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

        }
        else {

        }
    }
}