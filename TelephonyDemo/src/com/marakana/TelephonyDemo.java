package com.marakana;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class TelephonyDemo extends Activity {
  TextView textOut;
  TelephonyManager telephonyManager;
  PhoneStateListener listener;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // Get the UI
    textOut = (TextView) findViewById(R.id.textOut);

    // Get the telephony manager
    telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

    // Create a new PhoneStateListener
    listener = new PhoneStateListener() {
      @Override
      public void onCallStateChanged(int state, String incomingNumber) {
        String stateString = "N/A";
        switch (state) {
        case TelephonyManager.CALL_STATE_IDLE:
          stateString = "Idle";
          break;
        case TelephonyManager.CALL_STATE_OFFHOOK:
          stateString = "Off Hook";
          break;
        case TelephonyManager.CALL_STATE_RINGING:
          stateString = "Ringing";
          break;
        }
        textOut.append(String.format("\nonCallStateChanged: %s", stateString));
      }
    };

    // Register the listener wit the telephony manager
    telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
  }
}