package com.csci_4211_mocal.mocal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csci_4211_mocal.mocal.R;
import com.csci_4211_mocal.mocal.models.Event;
import com.csci_4211_mocal.mocal.viewmodels.EventCell;
import com.csci_4211_mocal.mocal.viewmodels.SharedEventCell;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SharedEventListAdapter extends RecyclerView.Adapter<SharedEventCell> {

    private ArrayList<Event> events;
    private ItemListener listener;

    public SharedEventListAdapter(ArrayList<Event> events, ItemListener listener) {
        this.events = events;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SharedEventCell onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shared_event_list_cell, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height =  (int) (parent.getHeight() * 1.0/10.0);

        return new SharedEventCell(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SharedEventCell holder, int position) {
        Event event = events.get(position);
        if (event != null) {
            holder.textViewTitle.setText(event.getTitle());
            SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy @ hh:mm a");
            holder.textViewTimestamp.setText(format.format(event.getTimestamp()));
            holder.event = event;
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public interface ItemListener {
        void addClicked(Event event);
        void removeClicked(Event event);
    }
}
