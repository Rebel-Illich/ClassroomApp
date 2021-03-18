package com.hunter.myclassroommap.db;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;



public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ClassRoom.db";
    public static final int DATABASE_VERSION = 1;
    private Context context;

    public static final String TABLE_NAME = "classroom_table";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_ROOMNUMBER = "ROOMNUMBER";
    public static final String COLUMN_FLOOR = "FLOOR";
    public static final String COLUMN_NUMBEROFSTUDENTS = "NUMBEROFSTUDENTS";

     public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_ROOMNUMBER + " INTEGER, " +
                COLUMN_FLOOR + " INTEGER, " +
                COLUMN_NUMBEROFSTUDENTS + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }
}
