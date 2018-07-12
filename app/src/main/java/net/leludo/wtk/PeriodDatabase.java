package net.leludo.wtk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Ludovic on 06/01/2018.
 */
public class PeriodDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wtp.db";
    private static final String TABLE_PERIOD = "period";
    private static final int DATABASE_VERSION = 1;

    public PeriodDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_PERIOD +
                "(start date, stop date);");
        Log.d("db", "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long create(Date intime, Date outtime) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("start", intime.getTime() / 1000);
        values.put("stop", outtime.getTime() / 1000);
        return db.insert(TABLE_PERIOD, null, values);
    }
    public long create(Period period) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("start", period.getStart().getTime() / 1000);
        values.put("stop", period.getEnd().getTime() / 1000);
        return db.insert(TABLE_PERIOD, null, values);
    }

    public List<Period> find(Date date) {

        Calendar from = Calendar.getInstance();
        from.setTime(date);
        Log.d("date", from.getTime().toString());
        from.clear(Calendar.HOUR_OF_DAY);
        from.clear(Calendar.AM_PM);
        from.clear(Calendar.HOUR);
        from.clear(Calendar.MINUTE);
        from.clear(Calendar.SECOND);

        Log.d("date", from.getTime().toString());

        Calendar to = Calendar.getInstance();
        to.setTime(from.getTime());
        to.add(Calendar.DAY_OF_MONTH, 1);
        Log.d("date", to.getTime().toString());

        List<Period> periods = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
//        String query = "SELECT *, datetime(start, 'unixepoch'), strftime('%s','now'), time('now') FROM " + TABLE_PERIOD;
//        Cursor cursor = db.rawQuery(query, new String[]{});
        String query = "SELECT *, datetime(start, 'unixepoch'), strftime('%s','now'), time('now') FROM "+TABLE_PERIOD+" WHERE start >= ? AND stop < ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(from.getTimeInMillis()/1000), String.valueOf(to.getTimeInMillis()/1000)} );
        Log.d("db", "find between " + String.valueOf(from.getTimeInMillis() / 1000) + " and " + String.valueOf(to.getTimeInMillis() / 1000));
        while (cursor.moveToNext()) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(cursor.getLong(0) * 1000);
            Date start = cal.getTime();
            cal.setTimeInMillis(cursor.getLong(1) * 1000);
            Date stop = cal.getTime();
            this.debug(cursor);
            Period period = new Period(start, stop);
            periods.add(period);
        }
        Log.d("db", periods.toString());
        return periods;

    }

    private void debug(Cursor cursor) {
        Log.d("db", "raw");
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.d("db", cursor.getColumnName(i) + ":" + cursor.getString(i));
        }
    }
}
