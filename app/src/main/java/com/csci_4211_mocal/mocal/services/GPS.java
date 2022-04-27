package com.csci_4211_mocal.mocal.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class GPS extends Service implements LocationListener {

    Context context;
    LocationManager manager;
    String info;

    public GPS(Context context) {
        System.out.println("makin it");
        this.context = context;
    }

    @SuppressLint("MissingPermission")
    public Location getLocation() {
        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            info = LocationManager.GPS_PROVIDER;
        } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            info = LocationManager.NETWORK_PROVIDER;
        }

        if (!info.isEmpty()) {
            manager.requestLocationUpdates(
                    info,
                    1000 * 60 * 1,
                    10,
                    this
            );

            if (manager != null) {
                return manager.getLastKnownLocation(info);
            }
        }

        return null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}
