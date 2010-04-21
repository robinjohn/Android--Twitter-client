package com.example;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyTwitter extends Activity implements OnClickListener {
  static final String TAG = "MyTwitter";
  Twitter twitter;
  SharedPreferences prefs;
  Button buttonUpdate;
  EditText textStatus;
  LocationHelper locationHelper;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
       
    setContentView(R.layout.main);

    // Find views by id
    buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
    textStatus = (EditText) findViewById(R.id.textStatus);

    // Add listener
    buttonUpdate.setOnClickListener(this);

    // Initialize preferences
    prefs = PreferenceManager.getDefaultSharedPreferences(this);
    prefs
        .registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener() {
          public void onSharedPreferenceChanged(SharedPreferences prefs,
              String arg1) {
            twitter = null;
          }
        });

    // Screen orientation
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      textStatus.setMinLines(3);
      buttonUpdate.setTextSize(22);
    }

    // Setup location helper
    locationHelper = new LocationHelper(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    // Start the UpdaterService
    startService(new Intent(this, UpdaterService.class));
    
    // Request location updates
    locationHelper.requestLocationUpdates();
  }
  
  @Override
  public void onStop() {
    super.onStop();
    
    // Unregister for location updates
    locationHelper.removeUpdates();
  }
  @Override
  public void onDestroy() {
    super.onDestroy();
    // Stop the UpdaterService
    stopService(new Intent(this, UpdaterService.class));
  }

  /** Called when Update button is clicked */
  public void onClick(View v) {
    String status = textStatus.getText().toString();
    String message = "Status set to: " + status;
    Log.d(TAG, message);

    // Ignore empty updates
    if (status.length() == 0)
      return;

    // Update status for location
    status = locationHelper.updateStatusForLocation(status);

    // Connect to twitter.com and update your status
    try {
      getTwitter().setStatus(status);
    } catch (TwitterException e) {
      Log.e(TAG, String.format("Twitter exception: %s", e));
    }
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  /** Initializes the menu. Called only once, first time user clicks on menu. **/
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  /** Called when menu item is selected **/
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case R.id.menuPrefs:
      startActivity(new Intent(this, Prefs.class));
      break;
    case R.id.menuTimeline:
      startActivity(new Intent(this, Timeline.class));
      break;
    case R.id.menuStartService:
      startService(new Intent(this, UpdaterService.class));
      break;
    case R.id.menuStopService:
      stopService(new Intent(this, UpdaterService.class));
      break;
    }
    return true;
  }

  // Initializes twitter, if needed
  private Twitter getTwitter() {
    if (twitter == null) {
      String username = prefs.getString("username", "");
      String password = prefs.getString("password", "");

      if (username != null && password != null &&
          username.length()>0 && password.length()>0 ) {
        twitter = new Twitter(username, password);
      } else {
        Toast.makeText(this,
            "Twitter username and password not set in preferences.",
            Toast.LENGTH_LONG).show();
        twitter = new Twitter();
      }
    }
    return twitter;
  }
}