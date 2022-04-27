package com.csci_4211_mocal.mocal;

<<<<<<< Updated upstream
import android.graphics.Color;
=======
import android.media.Image;
import android.provider.ContactsContract;
>>>>>>> Stashed changes
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarView> {

    private final ArrayList<String> days;
    private final Month month;
    private final ItemListener itemListener;
    private ImageView imageView;

    public CalendarAdapter(ArrayList<String> days, Month month, ItemListener itemListener) {
        this.days = days;
        this.month = month;
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

        LocalDate now = LocalDate.now();
        if (Integer.toString(now.getDayOfMonth()) == days.get(position)
            && now.getMonth() == month) {
            holder.day.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override public int getItemCount() {
        return days.size();
    }

    public interface ItemListener {
        void itemClicked(int position, String day);
    }
}
