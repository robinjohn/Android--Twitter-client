package com.example;

import winterwell.jtwitter.Twitter.Status;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
  Context context;
  static final String TAG = "DbHelper";
  static final String DB_NAME = "timeline.db";
  static final int    DB_VERSION = 5;
  static final String TABLE = "timeline";
  static final String C_ID = BaseColumns._ID;
  static final String C_CREATED_AT = "created_at";
  static final String C_TEXT = "status";
  static final String C_USER = "user";

  /** Constructor for DbHelper */
  public DbHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
    this.context = context;
  }

  /** Called only once, first time database is created */
  @Override
  public void onCreate(SQLiteDatabase db) {
    // String sql = String.format(
    // "CREATE table %s ( " +
    // "%s INTEGER NOT NULL PRIMARY KEY," +
    // "%s INTEGER, %s TEXT, %s TEXT)",
    // TABLE, C_ID, C_CREATED_AT, C_TEXT, C_USER);
    String sql = context.getResources().getString(R.string.sql);
    db.execSQL(sql); // execute the sql
    Log.d(TAG, "onCreate'd sql: " + sql);
  }

  /** Called every time DB version changes */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.d(TAG, String
            .format("onUpgrade from %s to %s", oldVersion, newVersion));
    // temporary
    db.execSQL("DROP TABLE IF EXISTS timeline;");
    this.onCreate(db);
  }
  
  /** Converts Twitter.Status to ContentValues */
  public static ContentValues statusToContentValues(Status status) {
    ContentValues ret = new ContentValues();
    ret.put(C_ID, status.id);
    ret.put(C_CREATED_AT, status.getCreatedAt().getTime());
    ret.put(C_TEXT, status.getText());
    ret.put(C_USER, status.getUser().getScreenName());
    return ret;
  }
}
