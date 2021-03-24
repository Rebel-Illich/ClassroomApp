package com.hunter.myclassroommap.db.studentData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hunter.myclassroommap.model.Student;

import java.util.ArrayList;

public class StudentRepository   {
    private DatabaseStudent dataBaseStudent;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<Student> studentModelArrayList;

    public StudentRepository(Context context) {
        dataBaseStudent = new DatabaseStudent(context.getApplicationContext());
    }

    private Cursor getAllEntries(){
        String query = "SELECT * FROM " + DatabaseStudent.TABLE_NAME;
        sqLiteDatabase = dataBaseStudent.getReadableDatabase();

        Cursor cursor = null;
        if(sqLiteDatabase != null) {
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }



}
