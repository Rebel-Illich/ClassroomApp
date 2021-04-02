package com.hunter.myclassroommap.db.studentData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hunter.myclassroommap.db.classroomData.ClassroomDatabase;
import com.hunter.myclassroommap.model.ClassRoom;
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
        Log.d(TAG, "getStudentsFromCurrentClass() called with: classroomId = [" + classroomId + "]");
        String query = "SELECT * FROM " + ClassroomDatabase.TABLE_STUDENT +
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
            long classroomID = cursor.getLong(cursor.getColumnIndex(ClassroomDatabase.COLUMN_CLASSROOM_ID));
            studentModelArrayList.add(new Student(id, firstName, lastName, middleName, gender, ageStudent, classroomID));
        }
        sqLiteDatabase.execSQL("UPDATE " + ClassroomDatabase.TABLE_NAME + " SET " + ClassroomDatabase.COLUMN_STUDENTS_COUNT + " = " +
                count + " WHERE " + ClassroomDatabase.COLUMN_ID + "=" + classroomId + ";");
        cursor.close();
        cursorCount.close();
        Log.d(TAG, "getStudentsFromCurrentClass: " + studentModelArrayList);
        return studentModelArrayList;
    }

    private static final String TAG = "StudentRepository";
    @Override
    public void deleteStudentFromClass(int position) {
        sqLiteDatabase = dataBaseStudent.getWritableDatabase();

        sqLiteDatabase.execSQL("DELETE FROM " + ClassroomDatabase.TABLE_STUDENT + " WHERE " + ClassroomDatabase.TABLE_STUDENT + "." +
                ClassroomDatabase.COLUMN_ID_STUDENT + " = " + position + ";");
        sqLiteDatabase.execSQL("UPDATE " + ClassroomDatabase.TABLE_STUDENT + " SET " +  ClassroomDatabase.COLUMN_ID_STUDENT + " = " +
                ClassroomDatabase.COLUMN_ID_STUDENT + " -1 " + " WHERE " + ClassroomDatabase.COLUMN_ID_STUDENT + " > " + position + ";");
        sqLiteDatabase.close();
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

    public void deleteOneRow(Integer row_id) {
        sqLiteDatabase = dataBaseStudent.getWritableDatabase();
        sqLiteDatabase.delete(dataBaseStudent.TABLE_STUDENT, "ID=?", new String[]{String.valueOf(row_id)});
    }

    public long updateData(Student studentM) {
        SQLiteDatabase db = dataBaseStudent.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ClassroomDatabase.COLUMN_NAME_STUDENT, studentM.getFirstName());
        cv.put(ClassroomDatabase.COLUMN_LAST_NAME, studentM.getLastName());
        cv.put(ClassroomDatabase.COLUMN_MIDDLE_NAME, studentM.getMiddleName());
        cv.put(ClassroomDatabase.COLUMN_STUDENT_AGE, studentM.getStudentAge());
        cv.put(ClassroomDatabase.COLUMN_STUDENT_GENDER, studentM.getStudentGender());

        return db.update(ClassroomDatabase.TABLE_STUDENT, cv, "ID=?",  new String[]{ String.valueOf(studentM.getStudentId()) });
    }


    public List<Student> getAll() {
        dataBaseStudent.getWritableDatabase();
        List<Student> students = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ClassroomDatabase.COLUMN_ID_STUDENT));
            String firstName = cursor.getString(cursor.getColumnIndex(ClassroomDatabase.COLUMN_NAME_STUDENT));
            String lastName = cursor.getString(cursor.getColumnIndex(ClassroomDatabase.COLUMN_LAST_NAME));
            String middleName = cursor.getString(cursor.getColumnIndex(ClassroomDatabase.COLUMN_MIDDLE_NAME));
            String gender = cursor.getString(cursor.getColumnIndex(ClassroomDatabase.COLUMN_STUDENT_GENDER));
            int ageStudent = cursor.getInt(cursor.getColumnIndex(ClassroomDatabase.COLUMN_STUDENT_AGE));
            long classroomID = cursor.getLong(cursor.getColumnIndex(ClassroomDatabase.COLUMN_CLASSROOM_ID));
            students.add(new Student(id, firstName, lastName, middleName, gender, ageStudent, classroomID));
        }
        cursor.close();
        return students;
    }

    public Cursor getAllEntries() {
        SQLiteDatabase db = dataBaseStudent.getWritableDatabase();
        String[] columns = new String[]{ClassroomDatabase.COLUMN_ID_STUDENT, ClassroomDatabase.COLUMN_NAME_STUDENT,
                ClassroomDatabase.COLUMN_LAST_NAME, ClassroomDatabase.COLUMN_MIDDLE_NAME, ClassroomDatabase.COLUMN_CLASSROOM_ID, ClassroomDatabase.COLUMN_STUDENT_GENDER, ClassroomDatabase.COLUMN_STUDENT_AGE};
        return db.query(ClassroomDatabase.TABLE_STUDENT, columns, null, null, null, null, null, null);
    }

    public List<ClassRoom> getAllClassRoom() {
        dataBaseStudent.getWritableDatabase();
        List<ClassRoom> classrooms = new ArrayList<>();
        Cursor cursor = getClassRoom();
        while (cursor.moveToNext()) {
            String classroomName = cursor.getString(cursor.getColumnIndex(ClassroomDatabase.COLUMN_NAME));
            long classroomRoomNumber = cursor.getLong(cursor.getColumnIndex(ClassroomDatabase.COLUMN_ROOM_NUMBER));
            long numberOfStudents = cursor.getLong(cursor.getColumnIndex(ClassroomDatabase.COLUMN_STUDENTS_COUNT));
            long classroomFloor = cursor.getLong(cursor.getColumnIndex(ClassroomDatabase.COLUMN_FLOOR_NUMBER));
            classrooms.add(new ClassRoom(classroomName, classroomRoomNumber, numberOfStudents, classroomFloor));
        }
        cursor.close();
        return classrooms;
    }

    public Cursor getClassRoom() {
        SQLiteDatabase db = dataBaseStudent.getWritableDatabase();
        String[] columns = new String[]{ClassroomDatabase.COLUMN_ID, ClassroomDatabase.COLUMN_NAME,
                ClassroomDatabase.COLUMN_ROOM_NUMBER, ClassroomDatabase.COLUMN_STUDENTS_COUNT, ClassroomDatabase.COLUMN_FLOOR_NUMBER};
        return db.query(ClassroomDatabase.TABLE_NAME, columns, null, null, null, null, null);
    }
}
