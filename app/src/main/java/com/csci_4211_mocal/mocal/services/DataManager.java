package com.csci_4211_mocal.mocal.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.csci_4211_mocal.mocal.R;
import com.csci_4211_mocal.mocal.models.UserData;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DataManager {
    private Context context;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public DataManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.shared_prefs), Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public UserData load() {
        String stored = sharedPreferences.getString(String.valueOf(R.string.user_data), "");
        if (stored == "") {
            return null;
        }

        UserData data = gson.fromJson(stored, UserData.class);
        return data;
    }

    public boolean update(UserData userData) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(userData);
        editor.putString(String.valueOf(R.string.user_data), json);
        boolean result = editor.commit();

        return result;
    }
}
