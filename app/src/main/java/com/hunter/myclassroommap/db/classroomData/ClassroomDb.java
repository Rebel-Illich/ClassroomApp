package com.hunter.myclassroommap.db.classroomData;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.ClassRoomDao;
import com.hunter.myclassroommap.model.Student;
import com.hunter.myclassroommap.model.StudentDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {ClassRoom.class, Student.class}, version = 1, exportSchema = false)
    public abstract class ClassroomDb extends RoomDatabase {

        public abstract ClassRoomDao classRoomDao();

        public abstract StudentDao studentDao();

        private static volatile ClassroomDb INSTANCE;

        public static ClassroomDb getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (RoomDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                ClassroomDb.class, "classrooms_database.db")
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
    }


