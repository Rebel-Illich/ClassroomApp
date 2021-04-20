package com.hunter.myclassroommap.viewStudent.mainPageStudent;

import androidx.lifecycle.LiveData;

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
        LiveData<List<Student>> getStudentsFromCurrentClass(int classroomId);
        Single<Integer> getNumFiles(int classroomId);
    }
}
