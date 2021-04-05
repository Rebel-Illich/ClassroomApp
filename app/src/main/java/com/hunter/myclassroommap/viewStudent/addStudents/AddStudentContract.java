package com.hunter.myclassroommap.viewStudent.addStudents;

import com.hunter.myclassroommap.model.Student;

import io.reactivex.Single;


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
    }
}
