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

   public void insertData(String name, int roomNumber, int floor, int numberOfStudents){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new  ContentValues();


        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_ROOMNUMBER, roomNumber);
        contentValues.put(COLUMN_FLOOR, floor);
        contentValues.put(COLUMN_NUMBEROFSTUDENTS, numberOfStudents);
        long result = db.insert(TABLE_NAME,null, contentValues);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "ID=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateData(String row_id, String name, int room, int floor, int countOfStudents){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_ROOMNUMBER, room);
        cv.put(COLUMN_FLOOR, floor);
        cv.put(COLUMN_NUMBEROFSTUDENTS, countOfStudents);

        long result = db.update(TABLE_NAME, cv, "ID=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData(){
         SQLiteDatabase db = this.getWritableDatabase();
         db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
