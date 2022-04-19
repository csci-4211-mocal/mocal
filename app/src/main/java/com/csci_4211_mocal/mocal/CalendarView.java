package com.csci_4211_mocal.mocal;

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
import com.squareup.picasso.Picasso;

public class CalendarView extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView day;
    private final CalendarAdapter.ItemListener itemListener;
    public final ImageView image;

    public CalendarView(@NonNull View itemView, CalendarAdapter.ItemListener itemListener) {
        super(itemView);
        day = itemView.findViewById(R.id.cellDayText);
        image = itemView.findViewById(R.id.imageView);
        this.itemListener = itemListener;
        itemView.setOnClickListener(this);
    }

    @Override public void onClick(View view) {
        itemListener.itemClicked(getAdapterPosition(), (String) day.getText());
    }
}
