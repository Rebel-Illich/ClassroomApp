package com.hunter.myclassroommap.db.studentData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseStudent extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="classrooms_database.db";
    static final String TABLE_NAME="students";


    // names
    public static final String COLUMN_ID="ID";
    public static final String COLUMN_NAME="First_name";
    public static final String COLUMN_LAST_NAME="Last_name";
    public static final String COLUMN_MIDDLE_NAME="Middle_name";
    public static final String COLUMN_STUDENT_GENDER="Gender";
    public static final String COLUMN_STUDENT_AGE="Age";
    public static final String COLUMN_CLASSROOM_ID="Classroom_id";

    public DatabaseStudent(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_LAST_NAME+ " TEXT, " +
                COLUMN_MIDDLE_NAME + " TEXT, " +
                COLUMN_STUDENT_GENDER + " TEXT, " +
                COLUMN_STUDENT_AGE + " INTEGER, " +
                COLUMN_CLASSROOM_ID + " INTEGER);");

        db.execSQL("INSERT INTO "+ TABLE_NAME +" (" + COLUMN_NAME
                + ", " + COLUMN_LAST_NAME  + ","  + COLUMN_MIDDLE_NAME + ", " + COLUMN_STUDENT_GENDER + ", " + COLUMN_STUDENT_AGE
                + ", " + COLUMN_CLASSROOM_ID + ") VALUES ('SS','sss', 'sss', 's', 1995, 1);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}
