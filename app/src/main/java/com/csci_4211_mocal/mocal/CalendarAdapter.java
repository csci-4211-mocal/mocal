package com.csci_4211_mocal.mocal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarView> {

    private final ArrayList<String> days;
    private final ItemListener itemListener;

    public CalendarAdapter(ArrayList<String> days, ItemListener itemListener) {
        this.days = days;
        this.itemListener = itemListener;
    }

    @NonNull @Override public CalendarView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.calendar_day_cell, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 1.0/6.0);

        return new CalendarView(view, itemListener);
    }

    @Override public void onBindViewHolder(@NonNull CalendarView holder, int position) {
        holder.day.setText(days.get(position));
    }

    @Override public int getItemCount() {
        return days.size();
    }

    public interface ItemListener {
        void itemClicked(int position, String day);
    }
}
