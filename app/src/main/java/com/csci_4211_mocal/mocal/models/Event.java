package com.csci_4211_mocal.mocal.models;

import java.util.Date;

public class Event {
    private final String title;
    private final String description;
    private final Date timestamp;

    public Event(String title, String description, Date timestamp) {
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
