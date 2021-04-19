package com.hunter.myclassroommap;

import android.app.Application;

import com.hunter.myclassroommap.db.classroomData.ClassroomDb;
import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.db.studentData.StudentRepository;

public class ClassApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public ClassroomDb getDatabase() {
        return ClassroomDb.getDatabase(this, mAppExecutors);
    }

    public ClassroomRepository getClassRepository() {
        return ClassroomRepository.getInstance(getDatabase());
    }

    public StudentRepository getStudentRepository(){
        return StudentRepository.getInstance(getDatabase());
    }
}
