package com.hunter.myclassroommap;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "ClassRoom.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "classroom_table";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_ROOM = "ROOMNUMBER";
    public static final String COLUMN_FLOOR = "FLOOR";
    public static final String COLUMN_NUMBEROFSTUDENTS = "NUMBEROFSTUDENTS";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, ROOMNUMBER INTEGER, FLOOR INTEGER, NUMBEROFSTUDENTS INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);

    }

    public boolean insertData(String name, int roomNumber, int floor, int numberOfStudents){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new  ContentValues();
        contentValues.put(COLUMN_NAME , name);
        contentValues.put(COLUMN_ROOM, roomNumber);
        contentValues.put(COLUMN_FLOOR, floor);
        contentValues.put(COLUMN_NUMBEROFSTUDENTS, numberOfStudents);
        long result = db.insert(TABLE_NAME,null, contentValues);
        if (result == -1){
            return false;
        } else{
            return true;
        }
    }
}
