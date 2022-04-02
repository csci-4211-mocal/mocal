package com.csci_4211_mocal.mocal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarView extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView day;
    private final CalendarAdapter.ItemListener itemListener;

    public CalendarView(@NonNull View itemView, CalendarAdapter.ItemListener itemListener) {
        super(itemView);
        day = itemView.findViewById(R.id.cellDayText);
        this.itemListener = itemListener;
        itemView.setOnClickListener(this);
    }

    @Override public void onClick(View view) {
        itemListener.itemClicked(getAdapterPosition(), (String) day.getText());
    }
}
