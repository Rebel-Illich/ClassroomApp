package com.hunter.myclassroommap.viewStudent.mainPageStudent;

import com.hunter.myclassroommap.model.Student;

import java.util.List;

import io.reactivex.Single;


public interface StudentAndClassContract {
    interface View {
        void onSuccess(String messageAlert);
    }

    interface Presenter{
        Single<List<Student>> loadAllData(int position);
    }

    interface Repository{
        Single<List<Student>> getStudentsFromCurrentClass(int classroomId);
    }
}
