package com.hunter.myclassroommap.db.classroomData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.ClassRoomDao;
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
    private ClassroomDb db;
    private ClassRoomDao classRoomDao;

    public ClassroomRepository(Context context) {
        dbHelper = new ClassroomDatabase(context.getApplicationContext());
        db = ClassroomDb.getDatabase(context);
        classRoomDao = db.classRoomDao();
    }

    public Completable deleteOneRow(ClassRoom classRoom) {
       return Completable.create(emitter -> {
          classRoomDao.deleteOneRow(classRoom);
          emitter.onComplete();
       });
    }

    public Single<ClassRoom> insertData(ClassRoom classRoom) {
        return Single.create(emitter -> {
            long id = classRoomDao.insertClassroom(classRoom);
            classRoom.setId(id);
            emitter.onSuccess(classRoom);
        });
    }

    public Single<ClassRoom> updateData(ClassRoom classRoom) {
        return Single.create(emitter -> {
            classRoomDao.updateClassroom(classRoom);
            emitter.onSuccess(classRoom);
        });
    }

    public Single<List<ClassRoom>> getListFromDataBase() {
        return classRoomDao.getListClassroom();
        }
}
