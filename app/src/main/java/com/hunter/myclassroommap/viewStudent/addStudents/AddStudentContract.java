package com.hunter.myclassroommap.viewStudent.addStudents;

import com.hunter.myclassroommap.model.Student;

public interface AddStudentContract {
    interface View {
        void onSuccess(String messageAlert);
    }

    interface Presenter{
        void addButtonClicked(String name, String lastName, String middleName, String gender, int age, int position);
    }

    interface Repository{
        long addStudent(Student studentModel);
    }
}
