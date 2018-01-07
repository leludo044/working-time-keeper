package net.leludo.wtk;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

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
        values.put("start", intime.getTime()) ;
        values.put("stop", outtime.getTime()) ;
        return db.insert(TABLE_PERIOD, null, values);
    }
}
