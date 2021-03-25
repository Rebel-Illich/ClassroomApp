package com.hunter.myclassroommap.viewStudent.editStudents;

import com.hunter.myclassroommap.model.Student;

public interface EditStudentContract {
    interface View {
        void onSuccess(String messageAlert);
    }

    interface Repository{
        long editStudent(Student studentModel);
    }

    interface Presenter {

    }
}
