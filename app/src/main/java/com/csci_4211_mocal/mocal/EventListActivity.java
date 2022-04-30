package com.csci_4211_mocal.mocal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csci_4211_mocal.mocal.adapters.EventListAdapter;
import com.csci_4211_mocal.mocal.dialogs.NewEventDialog;
import com.csci_4211_mocal.mocal.models.Event;
import com.csci_4211_mocal.mocal.models.UserData;
import com.csci_4211_mocal.mocal.services.Conversion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventListActivity extends AppCompatActivity implements EventListAdapter.ItemListener, NewEventDialog.NewEventDialogListener {
    private Date selectedDate;
    private Button backButton;
    private TextView textViewTitle;
    private RecyclerView recyclerViewEvents;
    private Button addEventButton;
    private ArrayList<Event> events;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView((R.layout.activity_event_list));

        textViewTitle = findViewById(R.id.textViewTitle);
        backButton = findViewById(R.id.buttonBack);
        recyclerViewEvents = findViewById(R.id.recyclerViewEvents);
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

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewEventDialog newEventDialog = new NewEventDialog(false, null, selectedDate);
                newEventDialog.show(getSupportFragmentManager(), "");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventListActivity.this, MainActivity.class);
                intent.putExtra("events", events);
                EventListActivity.this.startActivity(intent);
            }
        });

        layoutList();
    }

    private void layoutList() {
        ArrayList<Event> filtered = Conversion.filterAndOrderEventsByDate(events, selectedDate);

        EventListAdapter adapter = new EventListAdapter(filtered, this, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerViewEvents.setLayoutManager(layoutManager);
        recyclerViewEvents.setAdapter(adapter);
    }

    @Override
    public void confirmNewEvent(String title, String description, Date timestamp) {
        if (title.isEmpty() || description.isEmpty()) {
            NewEventDialog newEventDialog = new NewEventDialog(true, "Invalid input(s)", selectedDate);
            newEventDialog.show(getSupportFragmentManager(), "");
            return;
        }

        Event event = new Event(title, description, timestamp);
        events.add(event);

        layoutList();
    }

    @Override
    public void itemClicked(int position, String index) {

    }
}
