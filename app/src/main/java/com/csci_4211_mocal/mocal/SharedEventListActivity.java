package com.csci_4211_mocal.mocal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.csci_4211_mocal.mocal.adapters.EventListAdapter;
import com.csci_4211_mocal.mocal.adapters.SharedEventListAdapter;
import com.csci_4211_mocal.mocal.models.Event;
import com.csci_4211_mocal.mocal.services.Conversion;
import com.csci_4211_mocal.mocal.services.Network;

import org.json.JSONException;

import java.util.ArrayList;

public class SharedEventListActivity extends AppCompatActivity implements SharedEventListAdapter.ItemListener {
    private String token;
    private Network network;
    private TextView textViewTitle;
    private Button buttonBack;
    private RecyclerView recyclerViewEvents;
    private ArrayList<Event> receivedEvents;
    private ArrayList<Event> events;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_event_list);

        network = new Network(this);
        textViewTitle = findViewById(R.id.textViewTitle);
        buttonBack = findViewById(R.id.buttonBack);
        recyclerViewEvents = findViewById(R.id.recyclerViewEvents);

        Bundle extras = getIntent().getExtras();
        token = extras.getString("token");
        receivedEvents = extras.getParcelableArrayList("shared_events");
        events = extras.getParcelableArrayList("events");

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SharedEventListActivity.this, MainActivity.class);
                intent.putExtra("events", events);
                SharedEventListActivity.this.startActivity(intent);
            }
        });

        layoutList();
    }

    private void layoutList() {
        SharedEventListAdapter adapter = new SharedEventListAdapter(receivedEvents, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerViewEvents.setLayoutManager(layoutManager);
        recyclerViewEvents.setAdapter(adapter);
    }

    @Override
    public void addClicked(Event event) {
        events.add(event);
        removeClicked(event);
    }

    @Override
    public void removeClicked(Event event) {
        ArrayList<Event> updated = new ArrayList<>();
        for (Event e : receivedEvents) {
            if (!e.getId().equals(event.getId())) {
                updated.add(e);
            }
        }

        receivedEvents = updated;
        layoutList();
        layoutList();

        try {
            network.deleteEvent(token, event.getId(), deleteEventCallback);
        }
        catch (JSONException e) {
            // Do nothing
        }
    }

    Network.DeleteEventCallback deleteEventCallback = new Network.DeleteEventCallback() {
        @Override
        public void onDeletedResponse(VolleyError error, String res) {
            if (error != null) {
                Log.e("Delete Error", "Failed to delete shared event");
            }
            else {
                Log.i("Deleted", "Shared event successfully deleted");
            }
        }
    };
}
