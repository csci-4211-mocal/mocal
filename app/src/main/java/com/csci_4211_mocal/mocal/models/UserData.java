package com.csci_4211_mocal.mocal.models;

import java.util.ArrayList;

public class UserData {
    private final ArrayList<Event> events;

    public UserData(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void persist() {

    }
}
