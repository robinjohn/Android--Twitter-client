package com.example;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class WiFiScanReceiver extends BroadcastReceiver {
  private static final String TAG = "WiFiScanReceiver";

  public WiFiScanReceiver() {
    super();
  }

  @Override
  public void onReceive(Context c, Intent intent) {
    WifiManager wifi = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
    List<ScanResult> results = wifi.getScanResults();
    ScanResult bestSignal = null;
    for (ScanResult result : results) {
      if (bestSignal == null
          || WifiManager.compareSignalLevel(bestSignal.level, result.level) < 0)
        bestSignal = result;
    }

    Toast.makeText(c, String.format("%s networks found.", results.size()),
        Toast.LENGTH_LONG);

    Toast.makeText(c, String.format("%s is the strongest.", bestSignal.SSID),
        Toast.LENGTH_LONG);

    Log.d(TAG, "onReceive()");
  }
}
