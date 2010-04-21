package com.example;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/** Custom adapter so we can apply transformations on the data */
public class TimelineAdapter extends SimpleCursorAdapter {
  static final String[] from = { DbHelper.C_CREATED_AT, DbHelper.C_USER,
      DbHelper.C_TEXT };
  static final int[] to = { R.id.textCreatedAt, R.id.textUser, R.id.textText };

  /** Constructor */
  public TimelineAdapter(Context context, Cursor c) {
    super(context, R.layout.row, c, from, to);
  }

  /** This is where data is mapped to its view */
  @Override
  public void bindView(View row, Context context, Cursor cursor) {
    super.bindView(row, context, cursor);

    // Get the individual pieces of data
    long createdAt = cursor.getLong(cursor
        .getColumnIndex(DbHelper.C_CREATED_AT));

    // Find views by id
    TextView textCreatedAt = (TextView) row.findViewById(R.id.textCreatedAt);

    // Apply custom transformations
    textCreatedAt.setText(DateUtils.getRelativeTimeSpanString(createdAt));
  }

}
