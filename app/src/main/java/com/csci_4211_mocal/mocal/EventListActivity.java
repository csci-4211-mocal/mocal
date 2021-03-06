package com.csci_4211_mocal.mocal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.csci_4211_mocal.mocal.adapters.EventListAdapter;
import com.csci_4211_mocal.mocal.dialogs.EditEventDialog;
import com.csci_4211_mocal.mocal.dialogs.NewEventDialog;
import com.csci_4211_mocal.mocal.dialogs.ShareDialog;
import com.csci_4211_mocal.mocal.models.Event;
import com.csci_4211_mocal.mocal.models.UserData;
import com.csci_4211_mocal.mocal.services.Conversion;
import com.csci_4211_mocal.mocal.services.Network;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventListActivity extends AppCompatActivity implements
        EventListAdapter.ItemListener,
        NewEventDialog.NewEventDialogListener,
        EditEventDialog.EditEventDialogListener,
        ShareDialog.ShareDialogListener {
    private String token;
    private Network network;
    private Date selectedDate;
    private Button backButton;
    private TextView textViewTitle;
    private RecyclerView recyclerViewEvents;
    private Button addEventButton;
    private ArrayList<Event> events;
    private Event shareEvent;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView((R.layout.activity_event_list));

        network = new Network(this);
        textViewTitle = findViewById(R.id.textViewTitle);
        backButton = findViewById(R.id.buttonBack);
        recyclerViewEvents = findViewById(R.id.recyclerViewEvents);
        addEventButton = findViewById(R.id.buttonNewEvent);

        Bundle extras = getIntent().getExtras();
        int selectedDay = extras.getInt("selected_day");
        int selectedMonth = extras.getInt("selected_month");
        int selectedYear = extras.getInt("selected_year");
        token = extras.getString("token");
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
        if (title.isEmpty()) {
            NewEventDialog newEventDialog = new NewEventDialog(true, "Title field is required", selectedDate);
            newEventDialog.show(getSupportFragmentManager(), "");
            return;
        }

        Event event = new Event(null, title, description, timestamp);
        events.add(event);

        layoutList();
    }

    @Override
    public void confirmEditEvent(Event event) {
        if (event.getTitle().isEmpty()) {
            EditEventDialog editEventDialog = new EditEventDialog(true, "Title field required", event);
            editEventDialog.show(getSupportFragmentManager(), "");
        }
        ArrayList<Event> updated = new ArrayList<>();
        for (Event e : events) {
            if (e.getId().equals(event.getId())) {
                updated.add(event);
            }
            else {
                updated.add(e);
            }
        }

        events = updated;
        layoutList();
    }

    @Override
    public void confirmDeleteEvent(Event event) {
        ArrayList<Event> updated = new ArrayList<>();
        for (Event e : events) {
            if (!e.getId().equals(event.getId())) {
                updated.add(e);
            }
        }

        events = updated;
        layoutList();
        layoutList();
    }

    @Override
    public void confirmShareEvent(Event event) {
        ShareDialog dialog = new ShareDialog(false, null, this, event);
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void itemClicked(int position, Event event) {
        EditEventDialog dialog = new EditEventDialog(false, null, event);
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void itemLongClicked(int position, Event event) {
        System.out.println("LOWWWNNGGG");
    }

    @Override
    public void confirmShare(String username, Event event) {
        shareEvent = event;
        try {
            network.shareEvent(token, username, event, shareCallback);
        }
        catch (JSONException e) {
            // Do nothing
        }
    }

    Network.ShareCallback shareCallback = new Network.ShareCallback() {
        @Override
        public void onShareResponse(VolleyError error, String res) {
            if (error != null && shareEvent != null) {
                ShareDialog dialog = new ShareDialog(true, "Could not share", EventListActivity.this, shareEvent);
                dialog.show(getSupportFragmentManager(), "");
            }
            else {
                AlertDialog alertDialog = new AlertDialog.Builder(EventListActivity.this).create();
                alertDialog.setTitle("Success!");
                alertDialog.setMessage("Successfully shared event");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
    };
}
