package com.hunter.myclassroommap.viewStudent.addStudents;

import com.hunter.myclassroommap.model.Student;

import io.reactivex.Single;
import io.reactivex.SingleSource;


public interface AddStudentContract {
    interface View {
        void onSuccess(String messageAlert);

        void onError(String messageAlert);
    }

    interface Presenter{
        void addButtonClicked(String firstName, String lastName, String middleName, String gender, int age, long position);
    }

    interface Repository{
        Single<Student> addStudent(Student studentModel);

        Single<Integer> getNumFiles(int classroomId);

        Single<Boolean> updateClassroomStudentsCount(int classId, Integer count);
    }
}
