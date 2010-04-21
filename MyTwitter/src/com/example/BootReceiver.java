package com.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/** This receiver is called when system boots, 
 * so it can start our UpdaterService.
 */
public class BootReceiver extends BroadcastReceiver {
  static final String TAG = "BootReceiver";
  @Override
  public void onReceive(Context context, Intent intent) {
    context.startService(new Intent(context, UpdaterService.class));
    Log.d(TAG, "onReceive started UpdaterService");
  }
}
