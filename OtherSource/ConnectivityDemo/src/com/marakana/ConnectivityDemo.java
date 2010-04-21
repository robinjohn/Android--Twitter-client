package com.marakana;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ConnectivityDemo extends Activity {
  ConnectivityManager connectivity;
  NetworkInfo wifiInfo, mobileInfo;
  static final String LOG_TAG = "ConnectivityDemo->Socket";
  String ipaddress;
  TextView textStatus;
 
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // Get UI
    textStatus = (TextView) findViewById(R.id.textStatus);

    // Setup Connectivity
    connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    connectivity.setNetworkPreference(ConnectivityManager.TYPE_WIFI);
    wifiInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    mobileInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

/*    //Extracting IP address
    try {
        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
            NetworkInterface intf = en.nextElement();
            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                InetAddress inetAddress = enumIpAddr.nextElement();
                if (!inetAddress.isLoopbackAddress()) {
                    ipaddress = inetAddress.getHostAddress().toString();
                }
            }
        }
    } catch (SocketException ex) {
        Log.e(LOG_TAG, ex.toString());
    }      
*/    
    // print info
    textStatus.append("\n\n" + wifiInfo.toString());
    textStatus.append("\n\n" + mobileInfo.toString());
/*    if(ipaddress!=null)
    {
    	textStatus.append("\n\n" + ipaddress);
    }
    else
    {
    	textStatus.append("\n\n Failed fetching IP.");
    }
*/  
  }

}