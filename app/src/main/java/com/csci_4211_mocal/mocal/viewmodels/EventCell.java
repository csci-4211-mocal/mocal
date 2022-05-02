package com.csci_4211_mocal.mocal.viewmodels;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csci_4211_mocal.mocal.R;
import com.csci_4211_mocal.mocal.adapters.EventListAdapter;
import com.csci_4211_mocal.mocal.models.Event;

public class EventCell extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private final EventListAdapter.ItemListener listener;
    public final TextView textViewTitle;
    public final TextView textViewDescription;
    public final TextView textViewTimestamp;
    public Event event;

    public EventCell(@NonNull View itemView, EventListAdapter.ItemListener listener) {
        super(itemView);
        this.listener = listener;
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        textViewDescription = itemView.findViewById(R.id.textViewDescription);
        textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        listener.itemClicked(getAdapterPosition(), event);
    }

    @Override
    public boolean onLongClick(View view) {
        listener.itemLongClicked(getAdapterPosition(), event);
        return true;
    }
}
