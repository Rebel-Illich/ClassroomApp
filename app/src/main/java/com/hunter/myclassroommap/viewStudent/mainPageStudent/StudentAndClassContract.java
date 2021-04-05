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

        void alertToDeleteClass(int position);
    }

    interface Repository{
        List<Student> getStudentsFromCurrentClass(int classroomId);

        void deleteStudentFromClass(int position);
    }
}
