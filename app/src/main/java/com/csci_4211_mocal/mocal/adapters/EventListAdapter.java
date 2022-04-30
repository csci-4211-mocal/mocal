package com.csci_4211_mocal.mocal.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csci_4211_mocal.mocal.viewmodels.EventCell;

public class EventListAdapter extends RecyclerView.Adapter<EventCell> {
    @NonNull
    @Override
    public EventCell onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull EventCell holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
