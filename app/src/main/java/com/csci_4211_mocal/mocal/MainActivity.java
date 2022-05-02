package com.csci_4211_mocal.mocal;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.csci_4211_mocal.mocal.adapters.CalendarAdapter;
import com.csci_4211_mocal.mocal.dialogs.LoginDialog;
import com.csci_4211_mocal.mocal.dialogs.NewEventDialog;
import com.csci_4211_mocal.mocal.models.AccountInfo;
import com.csci_4211_mocal.mocal.models.Event;
import com.csci_4211_mocal.mocal.models.UserData;
import com.csci_4211_mocal.mocal.services.Conversion;
import com.csci_4211_mocal.mocal.services.DataManager;
import com.csci_4211_mocal.mocal.services.GPS;
import com.csci_4211_mocal.mocal.services.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements
        CalendarAdapter.ItemListener,
        LoginDialog.LoginDialogListener {

    private DataManager dataManager;
    private Network network;
    private UserData userData;
    private TextView textViewMonthYear;
    private TextView textViewGreeting;
    private TextView textViewUsername;
    private RecyclerView recyclerViewCalender;
    private LocalDate selectedDate;
    private ArrayList<String> forecasts;
    private ArrayList<Event> preloadedEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCalender = findViewById(R.id.recyclerViewCalendar);
        textViewMonthYear = findViewById(R.id.textViewMonthYear);
        textViewGreeting = findViewById(R.id.textViewGreeting);
        textViewUsername = findViewById(R.id.textViewUsername);
        forecasts = new ArrayList<>();
        selectedDate = LocalDate.now();
        dataManager = new DataManager(this);
        network = new Network(this);
        userData = dataManager.load();

//        Wipe account
//        userData = null;
//        dataManager.update(userData);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            preloadedEvents = extras.getParcelableArrayList("events");
        }

        network.healthCheck(healthCallback);
        layoutMonth();

        GPS gps = new GPS(this);
        Location location =  gps.getLocation();

        if (location != null) {
            network.getWeather(location.getLatitude(), location.getLongitude(), weatherCallback);
        }
    }

    public void previousMonthClicked(View view) {
        selectedDate = selectedDate.minusMonths(1);
        layoutMonth();
    }

    public void nextMonthClicked(View view) {
        selectedDate = selectedDate.plusMonths(1);
        layoutMonth();
    }

    private void layoutMonth() {
        textViewMonthYear.setText(getMonthYear(selectedDate));
        ArrayList<String> days = getDays(selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(userData, this, days, selectedDate.getMonthValue(), selectedDate.getYear(), forecasts, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        recyclerViewCalender.setLayoutManager(layoutManager);
        recyclerViewCalender.setAdapter(calendarAdapter);
    }

    private String getMonthYear(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    private ArrayList<String> getDays(LocalDate date) {
        ArrayList<String> days = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int numDays = yearMonth.lengthOfMonth();

        LocalDate first = selectedDate.withDayOfMonth(1);
        int weekDay = first.getDayOfWeek().getValue();

        for (int i = 0; i < 42; i++) {
            if (i < weekDay || i >= numDays + weekDay) {
                days.add("");
            }
            else {
                days.add(String.valueOf(i - weekDay + 1));
            }
        }
        return days;
    }

    @Override
    public void itemClicked(int position, String day) {
        if (day.equals("")) {

        } else {
            Intent intent = new Intent(this, EventListActivity.class);
            intent.putExtra("selected_day", Integer.parseInt(day));
            intent.putExtra("selected_month", selectedDate.getMonthValue());
            intent.putExtra("selected_year", selectedDate.getYear());
            intent.putExtra("token", userData.getToken());
            intent.putExtra("events", userData.getEvents());
            startActivity(intent);
        }
    }

    @Override
    public void confirmLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            LoginDialog loginDialog = new LoginDialog(true, "Invalid input(s)");
            loginDialog.show(getSupportFragmentManager(), "");
        }
        else {
            try {
                network.login(username, password, loginCallback);
            }
            catch (JSONException e) {
                // Do nothing
            }
        }
    }

    Network.HealthCallback healthCallback = new Network.HealthCallback() {
        @Override
        public void onHealthResponse(VolleyError error, String res) {
            if (error != null) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("API health check failure: " + error.toString());
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Retry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                network.healthCheck(healthCallback);
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            else {

                if (userData == null) {
                    Log.i("User Data", "User data is null");
                    LoginDialog loginDialog = new LoginDialog(false, null);
                    loginDialog.show(getSupportFragmentManager(), "");
                }
                else if (preloadedEvents != null) {
                    Log.i("User Data", "User data preloaded");
                    userData.updateEvents(preloadedEvents);
                    preloadedEvents = null;
                    dataManager.update(userData);
                }
                else {
                    Log.i("User Data", "User data found");
                    userData = dataManager.load();
                    if (userData != null) {
                        if (userData.getAccountInfo() != null) {
                            try {
                                network.getAccountInfo(userData.getToken(), accountInfoCallback);
                            }
                            catch (JSONException e) {
                                // Do nothing
                            }
                        }
                    }
                }
            }
        }
    };

    Network.LoginCallback loginCallback = new Network.LoginCallback() {
        @Override
        public void onLoginResponse(VolleyError error, String res) {
            if (error != null) {
                LoginDialog loginDialog = new LoginDialog(true, "An error occurred");
                loginDialog.show(getSupportFragmentManager(), "");
            }
            else {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String token = jsonObject.getString("token");
                    JSONObject accountInfo = jsonObject.getJSONObject("info");
                    String accountId = accountInfo.getString("id");
                    String username = accountInfo.getString("username");
                    String password = accountInfo.getString("password");
                    ArrayList<Event> e = new ArrayList<>();
                    UserData newUser = new UserData(token, e);
                    AccountInfo info = new AccountInfo(accountId, username, password);
                    newUser.setAccountInfo(info);
                    dataManager.update(newUser);
                    textViewGreeting.setText(Conversion.getGreeting());
                    textViewUsername.setText(username);
                }
                catch (JSONException e) {
                    // Do nothing
                }
            }
        }
    };

    Network.AccountInfoCallback accountInfoCallback = new Network.AccountInfoCallback() {
        @Override
        public void onAccountInfoResponse(VolleyError error, String res) {
            if (error != null) {
                LoginDialog loginDialog = new LoginDialog(true, "Session expired");
                loginDialog.show(getSupportFragmentManager(), "");
            }
            else {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String token = jsonObject.getString("token");
                    JSONObject accountInfo = jsonObject.getJSONObject("info");
                    String accountId = accountInfo.getString("id");
                    String username = accountInfo.getString("username");
                    String password = accountInfo.getString("password");
                    AccountInfo info = new AccountInfo(accountId, username, password);
                    userData.setAccountInfo(info);
                    userData.setToken(token);
                    dataManager.update(userData);
                    textViewGreeting.setText(Conversion.getGreeting());
                    textViewUsername.setText(username);
                }
                catch(JSONException e) {
                    // Do nothing
                }
            }
        }
    };

    Network.WeatherCallback weatherCallback = new Network.WeatherCallback() {
        @Override
        public void onWeatherResponse(VolleyError error, String res) {
            if (error != null) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Failed to fetch weather data: " + error.toString());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            else {
                try {
                    forecasts = Conversion.parseWeather(res);
                    layoutMonth();
                } catch (JSONException e) {
                    Log.e("Weather", e.toString());
                }
            }
        }
    };
}