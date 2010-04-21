package com.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
  private static final String TAG = "SMSReceiver";

  @Override
  public void onReceive(Context c, Intent intent) {
    Log.d(TAG, "onReceive()");
    Bundle bundle = intent.getExtras();

    if (bundle != null) {
      // Extract the message
      SmsMessage[] msgs = null;
      String message = "";
      Object[] pdus = (Object[]) bundle.get("pdus");
      msgs = new SmsMessage[pdus.length];
      for (int i = 0; i < msgs.length; i++) {
        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
        message = String.format("SMS from %s: %s\n", msgs[i]
            .getOriginatingAddress(), msgs[i].getMessageBody().toString());
      }
      Log.d(TAG, message);
    }
  }

}
