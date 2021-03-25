package com.hunter.myclassroommap.db.studentData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hunter.myclassroommap.db.classroomData.ClassroomDatabase;
import com.hunter.myclassroommap.model.Student;
import com.hunter.myclassroommap.viewStudent.addStudents.AddStudentContract;
import com.hunter.myclassroommap.viewStudent.mainPageStudent.StudentAndClassContract;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository implements StudentAndClassContract.Repository, AddStudentContract.Repository  {
    private ClassroomDatabase dataBaseStudent;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<Student> studentModelArrayList;

    public StudentRepository(Context context) {
        dataBaseStudent = new ClassroomDatabase(context.getApplicationContext());
    }


    @Override
    public List<Student> getStudentsFromCurrentClass(int classroomId) {
        studentModelArrayList = new ArrayList<>();

        String query = "SELECT * FROM " + ClassroomDatabase.TABLE_STUDENT +
                " INNER JOIN " + ClassroomDatabase.TABLE_NAME +
                " ON " + ClassroomDatabase.TABLE_NAME + "." + ClassroomDatabase.COLUMN_ID + "=" + ClassroomDatabase.TABLE_STUDENT + "." + ClassroomDatabase.COLUMN_CLASSROOM_ID +
                " WHERE " + ClassroomDatabase.TABLE_STUDENT + "." + ClassroomDatabase.COLUMN_CLASSROOM_ID + "=" + classroomId;
        String countQuery = "SELECT "+ ClassroomDatabase.COLUMN_ID_STUDENT +" FROM " + ClassroomDatabase.TABLE_STUDENT + " WHERE " + ClassroomDatabase.TABLE_STUDENT + "." + ClassroomDatabase.COLUMN_CLASSROOM_ID + "=" + classroomId ;
        SQLiteDatabase db = dataBaseStudent.getReadableDatabase();
        Cursor cursorCount = db.rawQuery(countQuery, null);
        int count = cursorCount.getCount();

        sqLiteDatabase = dataBaseStudent.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ClassroomDatabase.COLUMN_ID_STUDENT));
            String firstName = cursor.getString(cursor.getColumnIndex(ClassroomDatabase.COLUMN_NAME_STUDENT));
            String lastName = cursor.getString(cursor.getColumnIndex(ClassroomDatabase.COLUMN_LAST_NAME));
            String middleName = cursor.getString(cursor.getColumnIndex(ClassroomDatabase.COLUMN_MIDDLE_NAME));
            String gender = cursor.getString(cursor.getColumnIndex(ClassroomDatabase.COLUMN_STUDENT_GENDER));
            int ageStudent = cursor.getInt(cursor.getColumnIndex(ClassroomDatabase.COLUMN_STUDENT_AGE));
            int classroomID = cursor.getInt(cursor.getColumnIndex(ClassroomDatabase.COLUMN_ID));
            studentModelArrayList.add(new Student(id, firstName, lastName, middleName, gender, ageStudent, classroomID));
        }
        sqLiteDatabase.execSQL("UPDATE " + ClassroomDatabase.TABLE_NAME + " SET " + ClassroomDatabase.COLUMN_STUDENTS_COUNT + " = " +
                count + ";");
        cursor.close();
        cursorCount.close();
        return studentModelArrayList;
    }

    @Override
    public long addStudent(Student studentModel) {
        sqLiteDatabase = dataBaseStudent.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ClassroomDatabase.COLUMN_NAME_STUDENT, studentModel.getFirstName());
        contentValues.put(ClassroomDatabase.COLUMN_LAST_NAME, studentModel.getLastName());
        contentValues.put(ClassroomDatabase.COLUMN_MIDDLE_NAME, studentModel.getMiddleName());
        contentValues.put(ClassroomDatabase.COLUMN_STUDENT_GENDER, studentModel.getStudentGender());
        contentValues.put(ClassroomDatabase.COLUMN_STUDENT_AGE, studentModel.getStudentAge());
        contentValues.put(ClassroomDatabase.COLUMN_CLASSROOM_ID, studentModel.getClassroomId());

        return sqLiteDatabase.insert(ClassroomDatabase.TABLE_STUDENT, null, contentValues);
    }
//
//    @Override
//    public long updateStudent(Student studentModel) {
//        sqLiteDatabase = dataBaseStudent.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(ClassroomDatabase.COLUMN_NAME_STUDENT, studentModel.getFirstName());
//        contentValues.put(ClassroomDatabase.COLUMN_LAST_NAME, studentModel.getLastName());
//        contentValues.put(ClassroomDatabase.COLUMN_MIDDLE_NAME, studentModel.getMiddleName());
//        contentValues.put(ClassroomDatabase.COLUMN_STUDENT_GENDER, studentModel.getStudentGender());
//        contentValues.put(ClassroomDatabase.COLUMN_STUDENT_AGE, studentModel.getStudentAge());
//        return sqLiteDatabase.update(ClassroomDatabase.TABLE_STUDENT, contentValues, ClassroomDatabase.COLUMN_ID_STUDENT + " = " +  studentModel.getStudentId(),null);
//
//    }

//    @Override
//    public void deleteStudentFromClass(int position) {
//        sqLiteDatabase = dataBaseStudent.getWritableDatabase();
//
//        sqLiteDatabase.execSQL("DELETE FROM " + ClassroomDatabase.TABLE_STUDENT + " WHERE " + ClassroomDatabase.TABLE_STUDENT + "." +
//                ClassroomDatabase.COLUMN_ID_STUDENT + " = " + position + ";");
//        sqLiteDatabase.execSQL("UPDATE " + ClassroomDatabase.TABLE_STUDENT + " SET " +  ClassroomDatabase.COLUMN_ID_STUDENT + " = " +
//                ClassroomDatabase.COLUMN_ID_STUDENT + " -1 " + " WHERE " + ClassroomDatabase.COLUMN_ID_STUDENT + " > " + position + ";");
//        sqLiteDatabase.close();
//    }

}
