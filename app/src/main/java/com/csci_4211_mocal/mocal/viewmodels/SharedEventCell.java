package com.csci_4211_mocal.mocal.viewmodels;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csci_4211_mocal.mocal.R;
import com.csci_4211_mocal.mocal.SharedEventListActivity;
import com.csci_4211_mocal.mocal.adapters.EventListAdapter;
import com.csci_4211_mocal.mocal.adapters.SharedEventListAdapter;
import com.csci_4211_mocal.mocal.models.Event;

public class SharedEventCell extends RecyclerView.ViewHolder {
    private final SharedEventListAdapter.ItemListener listener;
    public final TextView textViewTitle;
    public final TextView textViewTimestamp;
    public final Button buttonAdd;
    public final Button buttonRemove;
    public Event event;

    public SharedEventCell(@NonNull View itemView, SharedEventListAdapter.ItemListener listener) {
        super(itemView);
        this.listener = listener;
        this.textViewTitle = itemView.findViewById(R.id.textViewTitle);
        this.textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
        this.buttonAdd = itemView.findViewById(R.id.buttonAdd);
        this.buttonRemove = itemView.findViewById(R.id.buttonRemove);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.addClicked(event);
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.removeClicked(event);
            }
        });
    }
}
