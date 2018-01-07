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
public class VacationDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wtp.db";
    private static final String TABLE_VACATION = "vacation";
    private static final int DATABASE_VERSION = 1;

    public VacationDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_VACATION +
                "(intime date, outtime date);");
        Log.d("db", "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long create(Date intime, Date outtime) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("intime", intime.getTime()) ;
        values.put("outtime", outtime.getTime()) ;
        return db.insert(TABLE_VACATION, null, values);
    }
}
