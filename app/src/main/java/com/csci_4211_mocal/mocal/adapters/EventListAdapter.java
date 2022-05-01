package com.csci_4211_mocal.mocal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csci_4211_mocal.mocal.R;
import com.csci_4211_mocal.mocal.models.Event;
import com.csci_4211_mocal.mocal.viewmodels.EventCell;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventCell> {
    private ArrayList<Event> events;
    private Context context;
    private ItemListener listener;

    public EventListAdapter(ArrayList<Event> events, Context context, ItemListener listener) {
        this.events = events;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventCell onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.event_list_cell, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height =  (int) (parent.getHeight() * 1.0/10.0);

        return new EventCell(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventCell holder, int position) {
        Event event = events.get(position);
        System.out.println(event);
        if (event != null) {
            holder.textViewTitle.setText(event.getTitle());
            holder.textViewDescription.setText(event.getDescription());
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
            holder.textViewTimestamp.setText(format.format(event.getTimestamp()));
            holder.event = event;
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public interface ItemListener {
        void itemClicked(int position, Event event);
    }
}
