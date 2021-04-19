package com.hunter.myclassroommap.db.classroomData;

import android.content.Context;


import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.hunter.myclassroommap.AppExecutors;
import com.hunter.myclassroommap.db.converter.DateConverter;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.ClassRoomDao;
import com.hunter.myclassroommap.model.Student;
import com.hunter.myclassroommap.model.StudentDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {ClassRoom.class, Student.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
  public abstract class ClassroomDb extends RoomDatabase {

    private static volatile ClassroomDb INSTANCE;

    @VisibleForTesting
    public static final String DATABASE_NAME = "classrooms_database.db";

        public abstract ClassRoomDao classRoomDao();

        public abstract StudentDao studentDao();

        private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

        public static ClassroomDb getDatabase(final Context context, final AppExecutors executors) {
            if (INSTANCE == null) {
                synchronized (ClassroomDb.class) {
                    if (INSTANCE == null) {
                            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                ClassroomDb.class, DATABASE_NAME)
                                .build();
                    }
                }
            }
            return INSTANCE;
        }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
    }


