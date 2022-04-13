package com.csci_4211_mocal.mocal.models;

import java.util.ArrayList;

public class UserData {
    private ArrayList<Event> events;

    public UserData(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public boolean addEvent(Event event) {
        boolean result = events.add(event);
        return result;
    }

    public boolean deleteEvent(Event event) {
        boolean result = false;

        for (int i = 0; i < events.size(); i++) {
            Event current = events.get(i);
            if (current == event) {
                events.remove(i);
                result = true;
            }
        }

        return result;
    }
}
