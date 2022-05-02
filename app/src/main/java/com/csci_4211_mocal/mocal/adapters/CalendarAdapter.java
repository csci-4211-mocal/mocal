package com.csci_4211_mocal.mocal.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csci_4211_mocal.mocal.R;
import com.csci_4211_mocal.mocal.models.Event;
import com.csci_4211_mocal.mocal.models.UserData;
import com.csci_4211_mocal.mocal.services.Conversion;
import com.csci_4211_mocal.mocal.viewmodels.DayCell;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarAdapter extends RecyclerView.Adapter<DayCell> {

    private UserData userData;
    private Context context;
    private final ArrayList<String> days;
    private ArrayList<String> forecasts;
    private final int month;
    private final int year;
    private final ItemListener itemListener;
    private ImageView imageView;

    public CalendarAdapter(UserData userData, Context context, ArrayList<String> days, int month, int year, ArrayList<String> forecasts, ItemListener itemListener) {
        this.userData = userData;
        this.context = context;
        this.days = days;
        this.month = month;
        this.year = year;
        this.forecasts = forecasts;
        this.itemListener = itemListener;
    }

    @NonNull @Override public DayCell onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.calendar_day_cell, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 1.0/6.0);

        return new DayCell(view, itemListener);
    }

    @Override public void onBindViewHolder(@NonNull DayCell holder, int position) {
        holder.day.setText(days.get(position));

        LocalDate now = LocalDate.now();
        if (Integer.toString(now.getDayOfMonth()) == days.get(position)
                && now.getMonthValue() == month
                && now.getYear() == year) {
            holder.day.setTextColor(Color.parseColor("#000000"));
            holder.image.setBackgroundResource(R.drawable.cell_background_selected);
        }

        if (!forecasts.isEmpty()) {
            for (int i = 0; i < forecasts.size(); i++) {
                if (now.getMonthValue() == month && now.getYear() == year) {
                    if (days.get(position) == Integer.toString(now.getDayOfMonth() + i)) {
                        holder.imageStr = forecasts.get(i);
                        holder.loadImage();
                    }
                }
                else if (month == now.getMonthValue() + 1) {
                    if (now.getDayOfMonth() + (forecasts.size() - 1) > Month.of(now.getMonthValue()).maxLength()) {
                        Month lastMonth = Month.of(month - 1);
                        int daysLastMonth = lastMonth.maxLength();
                        String day = Integer.toString((now.getDayOfMonth() + i) % daysLastMonth);
                        System.out.println("Day " + day);
                        if (days.get(position) == day && Integer.parseInt(day) <= forecasts.size()) {
                            holder.imageStr = forecasts.get(i);
                            holder.loadImage();
                        }
                    }
                }
            }
        }

        try {
            Calendar calendar = Calendar.getInstance();
            int day = Integer.parseInt(days.get(position));
            calendar.set(year, month - 1, day);
            Date date = calendar.getTime();
            if (userData != null) {
                ArrayList<Event> filtered = Conversion.filterAndOrderEventsByDate(userData.getEvents(), date);

                if (filtered.size() > 0) {
                    if (filtered.size() <= 3) {
                        holder.statusView.setBackgroundResource(R.drawable.cell_status_low);
                    }
                    else if (filtered.size() <= 6) {
                        holder.statusView.setBackgroundResource(R.drawable.cell_status_med);
                    }
                    else {
                        holder.statusView.setBackgroundResource(R.drawable.cell_status_high);
                    }
                }
            }
        }
        catch(NumberFormatException e) {
            // Do nothing
        }
    }

    @Override public int getItemCount() {
        return days.size();
    }

    public void setForecasts(ArrayList<String> forecasts) {
        this.forecasts = forecasts;
    }

    public interface ItemListener {
        void itemClicked(int position, String day);
    }

//    String getImage(String code) {
//        switch (code) {
//            case "01d":
//                return "clear"
//            default:
//                break;
//        }
//    }
}
