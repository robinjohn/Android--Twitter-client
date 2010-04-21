package com.example;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/** Helper class to add support for location in the status updates. */
public class LocationHelper implements LocationListener {
  static final String TAG = "LocationHelper";
  static final String LOC = "@loc"; // @loc gets replaced with location
  static final long  MIN_TIME = 90000; // 15 min
  static final float MIN_DISTANCE = 100; // 10m
  Context context;
  LocationManager locationManager;
  Location location;
  Criteria criteria;
  String bestProvider;

  public LocationHelper(Context context) {
    this.context = context;
    locationManager = (LocationManager) context
        .getSystemService(Context.LOCATION_SERVICE);
    criteria = new Criteria();
    bestProvider = locationManager.getBestProvider(criteria, false);
    location = locationManager.getLastKnownLocation(bestProvider);

    Log.d(TAG, "construct'd with location: " + location);
  }

  /** Converts the LOC code to current location */
  public String updateStatusForLocation(String input) {
    String output;
    if (location == null) {
      output = input.replaceAll(LOC, "UNKNOWN");
    } else {
      output = input.replaceAll(LOC, String.format("(%f,%f)", location
          .getLongitude(), location.getLatitude()));
    }

    // Make sure we don't go over 160 characters
    output = (output.length() > 159) ? output.substring(0, 159) : output;

    Log.d(TAG, String.format("updateStatusForLocation(%s)=>%s", input, output));
    return output;
  }

  public void requestLocationUpdates() {
    locationManager.requestLocationUpdates(bestProvider, MIN_TIME,
        MIN_DISTANCE, this);
  }

  public void removeUpdates() {
    locationManager.removeUpdates(this);
  }

  // ---  Location Listener Methods  ---
  public void onLocationChanged(Location location) {
    this.location = location;
  }

  public void onProviderDisabled(String provider) {
    updateLocation();
  }

  public void onProviderEnabled(String provider) {
    updateLocation();
  }

  public void onStatusChanged(String provider, int status, Bundle extras) {
    updateLocation();
  }

  private void updateLocation() {
    bestProvider = locationManager.getBestProvider(criteria, true);
    location = locationManager.getLastKnownLocation(bestProvider);
    Log.d(TAG, "updateLocation() updated with location: " + location);
  }
}
