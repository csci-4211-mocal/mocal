package com.csci_4211_mocal.mocal.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

public class Event implements Parcelable {
    private final String id;
    private String title;
    private String description;
    private Date timestamp;

    public Event(String title, String description, Date timestamp) {
        id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }

    protected Event(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        timestamp = new Date(in.readLong());
    }

    public String getId() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeLong(timestamp.getTime());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
