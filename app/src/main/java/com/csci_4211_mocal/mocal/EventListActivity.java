package com.csci_4211_mocal.mocal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.csci_4211_mocal.mocal.dialogs.NewEventDialog;
import com.csci_4211_mocal.mocal.models.Event;
import com.csci_4211_mocal.mocal.models.UserData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventListActivity extends AppCompatActivity implements NewEventDialog.NewEventDialogListener {
    private Date selectedDate;
    private Button backButton;
    private TextView textViewTitle;
    private RecyclerView eventList;
    private Button addEventButton;
    private ArrayList<Event> events;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView((R.layout.activity_event_list));

        textViewTitle = findViewById(R.id.textViewTitle);
        backButton = findViewById(R.id.buttonBack);
        eventList = findViewById(R.id.recyclerViewEvents);
        addEventButton = findViewById(R.id.buttonNewEvent);

        Bundle extras = getIntent().getExtras();
        int selectedDay = extras.getInt("selected_day");
        int selectedMonth = extras.getInt("selected_month");
        int selectedYear = extras.getInt("selected_year");
        events = extras.getParcelableArrayList("events");

        Calendar calendar = Calendar.getInstance();
        calendar.set(selectedYear, selectedMonth - 1, selectedDay);
        selectedDate = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
        textViewTitle.setText(format.format(selectedDate));

        addEventButton.setOnClickListener(v -> {
            NewEventDialog newEventDialog = new NewEventDialog(false, null);
            newEventDialog.show(getSupportFragmentManager(), "");
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventListActivity.this, MainActivity.class);
            intent.putExtra("events", events);
            EventListActivity.this.startActivity(intent);
        });
    }

    @Override
    public void confirmNewEvent(String title, String description, Date timestamp) {
        if (title.isEmpty() || description.isEmpty()) {
            NewEventDialog newEventDialog = new NewEventDialog(true, "Invalid input(s)");
            newEventDialog.show(getSupportFragmentManager(), "");
            return;
        }

        Event event = new Event(title, description, timestamp);
        events.add(event);
    }
}
