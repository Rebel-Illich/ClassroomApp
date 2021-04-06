package com.hunter.myclassroommap.db.classroomData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.Student;

import io.reactivex.Single;

public class ClassroomRepository {

    private ClassroomDatabase dbHelper;
    private SQLiteDatabase database;

    public ClassroomRepository(Context context) {
        dbHelper = new ClassroomDatabase(context.getApplicationContext());
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + ClassroomDatabase.TABLE_NAME;
        database = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        if(database != null) {
             cursor = database.rawQuery(query, null);
        }
        return cursor;
    }

    public void deleteOneRow(long row_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(dbHelper.TABLE_NAME, "ID=?", new String[]{String.valueOf(row_id)});
    }

    public void deleteAllData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + dbHelper.TABLE_NAME);
    }

    public Single<ClassRoom> insertData(ClassRoom classRoom) {
        return Single.fromPublisher( publisher -> {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ClassroomDatabase.COLUMN_NAME, classRoom.getClassroomName());
        cv.put(ClassroomDatabase.COLUMN_ROOM_NUMBER, classRoom.getClassroomRoomNumber());
        cv.put(ClassroomDatabase.COLUMN_FLOOR_NUMBER, classRoom.getClassroomFloor());
        cv.put(ClassroomDatabase.COLUMN_STUDENTS_COUNT, classRoom.getNumberOfStudents());
            try {
                long studentId = db.insert(ClassroomDatabase.TABLE_NAME, null, cv);
                classRoom.setId((long) studentId);
                publisher.onNext(classRoom);
            } catch (Exception e) {
                publisher.onError(e);
            } finally {
                publisher.onComplete();
            }
        });
    }

    public long updateData(ClassRoom classRoom) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ClassroomDatabase.COLUMN_NAME, classRoom.getClassroomName());
        cv.put(ClassroomDatabase.COLUMN_ROOM_NUMBER, classRoom.getClassroomRoomNumber());
        cv.put(ClassroomDatabase.COLUMN_FLOOR_NUMBER, classRoom.getClassroomFloor());
        cv.put(ClassroomDatabase.COLUMN_STUDENTS_COUNT, classRoom.getNumberOfStudents());

        return db.update(ClassroomDatabase.TABLE_NAME, cv, "ID=?",  new String[]{ String.valueOf(classRoom.getId()) });
    }
}
