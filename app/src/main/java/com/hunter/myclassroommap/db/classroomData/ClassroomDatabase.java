package com.hunter.myclassroommap.db.classroomData;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;



public class ClassroomDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="classrooms_database.db";

    public static final String TABLE_NAME="classroom_table";
    public static final String TABLE_STUDENT = "student_table";

//column class
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_ROOM_NUMBER = "Room_number";
    public static final String COLUMN_FLOOR_NUMBER = "Floor_number";
    public static final String COLUMN_STUDENTS_COUNT = "Students_count";

//column student
    public static final String COLUMN_ID_STUDENT = "ID";
    public static final String COLUMN_NAME_STUDENT = "First_name";
    public static final String COLUMN_LAST_NAME = "Last_name";
    public static final String COLUMN_MIDDLE_NAME = "Middle_name";
    public static final String COLUMN_STUDENT_GENDER ="Gender";
    public static final String COLUMN_STUDENT_AGE = "Age";
    public static final String COLUMN_CLASSROOM_ID = "Classroom_id";

    public ClassroomDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_ROOM_NUMBER + " INTEGER, " +
                COLUMN_FLOOR_NUMBER + " INTEGER, " +
                COLUMN_STUDENTS_COUNT + " INTEGER);");


        db.execSQL("CREATE TABLE " + TABLE_STUDENT +
                " (" + COLUMN_ID_STUDENT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_STUDENT + " TEXT, " +
                COLUMN_LAST_NAME+ " TEXT, " +
                COLUMN_MIDDLE_NAME + " TEXT, " +
                COLUMN_STUDENT_GENDER + " TEXT, " +
                COLUMN_STUDENT_AGE + " INTEGER, " +
                COLUMN_CLASSROOM_ID + " INTEGER);");

        db.execSQL("INSERT INTO "+ TABLE_STUDENT +" (" + COLUMN_NAME_STUDENT
                + ", " + COLUMN_LAST_NAME  + ","  + COLUMN_MIDDLE_NAME + ", " + COLUMN_STUDENT_GENDER + ", " + COLUMN_STUDENT_AGE
                + ", " + COLUMN_CLASSROOM_ID + ") VALUES ('Ivan','Ivanov', 'Looper', 'G', 1995, 1);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_STUDENT);
        onCreate(db);
    }
}
