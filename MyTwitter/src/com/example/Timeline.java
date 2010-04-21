package com.example;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

/** Displays the list of all timelines from the DB. */
public class Timeline extends Activity {
  static final String TAG = "Timeline";
  ListView listTimeline;
  DbHelper dbHelper;
  SQLiteDatabase db;
  Cursor cursor;
  TimelineAdapter adapter;
  BroadcastReceiver twitterStatusReceiver;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.timeline);

    // Find views by id
    listTimeline = (ListView) findViewById(R.id.listTimeline);

    // Initialize DB
    dbHelper = new DbHelper(this);
    db = dbHelper.getReadableDatabase();

    // Get the data from the DB
    cursor = db.query(DbHelper.TABLE, null, null, null, null, null,
        DbHelper.C_CREATED_AT + " DESC");
    startManagingCursor(cursor);
    Log.d(TAG, "cursor got count: " + cursor.getCount());

    // Setup the adapter
    adapter = new TimelineAdapter(this, cursor);
    listTimeline.setAdapter(adapter);

    // Register to get ACTION_NEW_TWITTER_STATUS broadcasts
    twitterStatusReceiver = new TwitterStatusReceiver();
    registerReceiver(twitterStatusReceiver, new IntentFilter(
        UpdaterService.ACTION_NEW_TWITTER_STATUS));
  }

  @Override
  public void onResume() {
    super.onResume();
    // Cancel notification
    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    notificationManager.cancel(UpdaterService.Updater.NOTIFICATION_ID);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    unregisterReceiver(twitterStatusReceiver);
  }

  class TwitterStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      Log.d(TAG, "onReceive got ACTION_NEW_TWITTER_STATUS broadcast");
      cursor.requery();
    }
  }
}
