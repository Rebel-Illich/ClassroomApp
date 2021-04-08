package com.hunter.myclassroommap.db.classroomData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.Student;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

public class ClassroomRepository {

    private ClassroomDatabase dbHelper;
    private SQLiteDatabase database;
    private ArrayList<ClassRoom> classrooms;

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

    public Completable deleteOneRow(long row_id) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull CompletableEmitter emitter) throws Exception {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String whereClause = "ID=?";
                String[] whereArgs = new String[]{String.valueOf(row_id)};
                try {
                    db.delete(dbHelper.TABLE_NAME, whereClause,  whereArgs);
                } catch (Exception e) {
                    emitter.onError(e);
                } finally {
                    emitter.onComplete();
                }
            }
        });
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

    public Single<ClassRoom> updateData(ClassRoom classRoom) {
        return Single.fromPublisher(publisher ->{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(ClassroomDatabase.COLUMN_NAME, classRoom.getClassroomName());
            cv.put(ClassroomDatabase.COLUMN_ROOM_NUMBER, classRoom.getClassroomRoomNumber());
            cv.put(ClassroomDatabase.COLUMN_FLOOR_NUMBER, classRoom.getClassroomFloor());
            cv.put(ClassroomDatabase.COLUMN_STUDENTS_COUNT, classRoom.getNumberOfStudents());

            try {
                long classId = db.update(ClassroomDatabase.TABLE_NAME, cv, "ID=?",  new String[]{ String.valueOf(classRoom.getId())});
                classRoom.setId((long) classId);
                publisher.onNext(classRoom);
            } catch (Exception e) {
                publisher.onError(e);
            } finally {
                publisher.onComplete();
            }
        });
    }

    public List<ClassRoom> getListFromDataBase() {

        Cursor cursor = readAllData();
        if (cursor.getCount() != 0) {
            classrooms = new ArrayList<>();
            while (cursor.moveToNext()) {
                ClassRoom newClass = new ClassRoom();
                newClass.setId(Long.parseLong(cursor.getString(0)));
                newClass.setClassroomName(cursor.getString(1));
                newClass.setClassroomFloor(Long.parseLong(cursor.getString(2)));
                newClass.setClassroomRoomNumber(Long.parseLong(cursor.getString(3)));
                newClass.setNumberOfStudents(Long.parseLong(cursor.getString(4)));
                classrooms.add(newClass);
            }
        }
            cursor.close();
            return classrooms;
        }

}
