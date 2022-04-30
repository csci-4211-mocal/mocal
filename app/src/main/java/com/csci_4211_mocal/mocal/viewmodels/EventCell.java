package com.csci_4211_mocal.mocal.viewmodels;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csci_4211_mocal.mocal.R;
import com.csci_4211_mocal.mocal.adapters.EventListAdapter;

public class EventCell extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final EventListAdapter.ItemListener listener;
    public final TextView textViewTitle;
    public final TextView textViewDescription;
    public final TextView textViewTimestamp;

    public EventCell(@NonNull View itemView, EventListAdapter.ItemListener listener) {
        super(itemView);
        this.listener = listener;
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        textViewDescription = itemView.findViewById(R.id.textViewDescription);
        textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
    }

    @Override
    public void onClick(View view) {

    }
}
