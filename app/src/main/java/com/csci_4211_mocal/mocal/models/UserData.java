package com.csci_4211_mocal.mocal.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class UserData {
    private String token;
    private ArrayList<Event> events;

    public UserData(String token, ArrayList<Event> events) {
        this.token = token;
        this.events = events;
    }

    public String getToken() {
        return token;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void updateEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
