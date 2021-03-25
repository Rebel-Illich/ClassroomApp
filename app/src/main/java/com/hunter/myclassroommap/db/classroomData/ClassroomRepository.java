package com.hunter.myclassroommap.db.classroomData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.hunter.myclassroommap.model.ClassRoom;

public class ClassroomRepository {

    private ClassroomDatabase dbHelper;
    private SQLiteDatabase database;

    public ClassroomRepository(Context context) {
        dbHelper = new ClassroomDatabase(context.getApplicationContext());
    }

    public ClassroomRepository open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private Cursor getAllEntries() {
        String[] columns = new String[]{ClassroomDatabase.COLUMN_ID, ClassroomDatabase.COLUMN_NAME,
            ClassroomDatabase.COLUMN_ROOM_NUMBER, ClassroomDatabase.COLUMN_FLOOR_NUMBER,
            ClassroomDatabase.COLUMN_STUDENTS_COUNT};
        return database.query(ClassroomDatabase.TABLE_NAME, columns, null, null, null, null, null);
    }

    public long getCount() {
        return DatabaseUtils.queryNumEntries(database, ClassroomDatabase.TABLE_NAME);
    }

    public long delete(long userId) {

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(userId)};
        return database.delete(ClassroomDatabase.TABLE_NAME, whereClause, whereArgs);
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
        long result = db.delete(dbHelper.TABLE_NAME, "ID=?", new String[]{String.valueOf(row_id)});
    }

    public void deleteAllData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + dbHelper.TABLE_NAME);
    }

    public long insertData(ClassRoom classRoom) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ClassroomDatabase.COLUMN_NAME, classRoom.getClassroomName());
        cv.put(ClassroomDatabase.COLUMN_ROOM_NUMBER, classRoom.getClassroomRoomNumber());
        cv.put(ClassroomDatabase.COLUMN_FLOOR_NUMBER, classRoom.getClassroomFloor());
        cv.put(ClassroomDatabase.COLUMN_STUDENTS_COUNT, classRoom.getNumberOfStudents());

        return db.insert(ClassroomDatabase.TABLE_NAME, null, cv);
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
