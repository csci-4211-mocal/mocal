package com.csci_4211_mocal.mocal.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class UserData {
    private String token;
    private AccountInfo accountInfo;
    private ArrayList<Event> events;

    public UserData(String token, ArrayList<Event> events) {
        this.token = token;
        this.events = events;
        this.accountInfo = null;
    }

    public String getToken() {
        return token;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo info) {
        this.accountInfo = info;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void updateEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
