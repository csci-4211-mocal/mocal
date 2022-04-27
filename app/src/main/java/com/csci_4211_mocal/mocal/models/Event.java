package com.csci_4211_mocal.mocal.models;

import java.util.Date;
import java.util.UUID;

public class Event {
    private final UUID id;
    private String title;
    private String description;
    private Date timestamp;

    public Event(String title, String description, Date timestamp) {
        id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
