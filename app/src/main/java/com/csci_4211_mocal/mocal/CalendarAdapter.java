package com.csci_4211_mocal.mocal;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<DayCell> {

    private Context context;
    private final ArrayList<String> days;
    private ArrayList<String> forecasts;
    private final Month month;
    private final ItemListener itemListener;
    private ImageView imageView;

    public CalendarAdapter(Context context, ArrayList<String> days, Month month, ArrayList<String> forecasts, ItemListener itemListener) {
        this.context = context;
        this.days = days;
        this.month = month;
        this.forecasts = forecasts;
        this.itemListener = itemListener;
    }

    @NonNull @Override public DayCell onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.calendar_day_cell, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 1.0/8.0);

        return new DayCell(view, itemListener);
    }

    @Override public void onBindViewHolder(@NonNull DayCell holder, int position) {
        holder.day.setText(days.get(position));

        LocalDate now = LocalDate.now();
            if (Integer.toString(now.getDayOfMonth()) == days.get(position)
                    && now.getMonth() == month) {
                holder.day.setTextColor(Color.parseColor("#000000"));
            }

            if (!forecasts.isEmpty()) {
                for (int i = 0; i < forecasts.size(); i++) {
                    if (now.getMonth() == month) {
                        if (days.get(position) == Integer.toString(now.getDayOfMonth() + i)) {
                            holder.imageStr = forecasts.get(i);
                            holder.loadImage();
                        }
                    }
                    else if (month.getValue() == now.getMonth().getValue() + 1) {
                        Month lastMonth = Month.of(month.getValue() - 1);
                        int daysLastMonth = lastMonth.maxLength();
                        String day = Integer.toString((now.getDayOfMonth() + i) % daysLastMonth);
                        if (days.get(position) == day && Integer.parseInt(day) <= forecasts.size()) {
                            System.out.println(days.get(position));
                            holder.imageStr = forecasts.get(i);
                            holder.loadImage();
                        }
                    }
                }
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
