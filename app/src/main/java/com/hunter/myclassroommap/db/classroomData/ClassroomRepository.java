package com.hunter.myclassroommap.db.classroomData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

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
import io.reactivex.functions.Function;

public class ClassroomRepository {

    private static ClassroomRepository sInstance;
    private ClassroomDb db;
    private ClassRoomDao classRoomDao;
    private MediatorLiveData<List<ClassRoom>> mObservableProducts;

    public ClassroomRepository(ClassroomDb database) {
        db = database;
        classRoomDao = db.classRoomDao();
        mObservableProducts = new MediatorLiveData<>();

        mObservableProducts.addSource(db.classRoomDao().getListClassroomLD(),
                classrooms -> {
                    if (db.getDatabaseCreated().getValue() != null) {
                        mObservableProducts.postValue(classrooms);
                    }
                });
    }

    public static ClassroomRepository getInstance(final ClassroomDb database) {
        if (sInstance == null) {
            synchronized (ClassroomRepository.class) {
                if (sInstance == null) {
                    sInstance = new ClassroomRepository(database);
                }
            }
        }
        return sInstance;
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
        return classRoomDao.getListClassroom()
                .map(new Function<List<ClassRoom>, List<ClassRoom>>() {
                    @Override
                    public List<ClassRoom> apply(@NonNull List<ClassRoom> entityClassRooms) throws Exception {
                        List<ClassRoom> classRooms = new ArrayList<>();
                        for (int i = 0; i < entityClassRooms.size(); i++) {
                            classRooms.add(new ClassRoom(
                                    entityClassRooms.get(i).getClassroomName(),
                                    entityClassRooms.get(i).getClassroomRoomNumber(),
                                    entityClassRooms.get(i).getClassroomFloor(),
                                    entityClassRooms.get(i).getNumberOfStudents()
                            ));
                        }
                        return classRooms;
                    }
                });
    }

    public LiveData<List<ClassRoom>> getListFromDataBaseLD() {
        return classRoomDao.getListClassroomLD();
    }

    public Single<Boolean> updateClassroomStudentsCount(int classId, int countOfStudents) {
        return Single.create(emitter ->
        {
            classRoomDao.updateCount(classId, countOfStudents);
            emitter.onSuccess(true);
        });
    }
}
