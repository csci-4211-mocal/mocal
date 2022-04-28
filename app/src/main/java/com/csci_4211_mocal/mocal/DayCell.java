package com.csci_4211_mocal.mocal;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class DayCell extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView day;
    private final CalendarAdapter.ItemListener itemListener;
    public final ImageView image;
    public String imageStr;

    public DayCell(@NonNull View itemView, CalendarAdapter.ItemListener itemListener) {
        super(itemView);
        day = itemView.findViewById(R.id.cellDayText);
        image = itemView.findViewById(R.id.weatherIcon);
        this.itemListener = itemListener;
        itemView.setOnClickListener(this);
    }

    public void loadImage() {
        System.out.println(imageStr);
        if (imageStr != null) {
            switch (imageStr) {
                case "01d":
                    image.setImageResource(R.drawable.clear);
                    break;
                case "01n":
                    image.setImageResource(R.drawable.clear_night);
                    break;
                case "02d":
                    image.setImageResource(R.drawable.partly_cloudy);
                    break;
                case "02n":
                    image.setImageResource(R.drawable.partly_cloudy_night);
                    break;
                case "03d":
                case "03n":
                case "04d":
                case "04n":
                    image.setImageResource(R.drawable.cloudy);
                    break;
                case "09d":
                case "09n":
                case "10d":
                case "10n":
                    image.setImageResource(R.drawable.rainy);
                    break;
                case "11d":
                case "11n":
                    image.setImageResource(R.drawable.thunderstormy);
                    break;
                case "13d":
                case "13n":
                    image.setImageResource(R.drawable.snowy);
                    break;
                case "50d":
                case "50n":
                    image.setImageResource(R.drawable.misty);
                    break;
                default:
                    break;
            }
        }
    }

    @Override public void onClick(View view) {
        itemListener.itemClicked(getAdapterPosition(), (String) day.getText());
    }
}
