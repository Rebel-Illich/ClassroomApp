package com.hunter.myclassroommap.viewStudent.mainPageStudent;

import com.hunter.myclassroommap.model.Student;

import java.util.List;

public interface StudentAndClassContract {
    interface View {
        void onSuccess(String messageAlert);
    }

    interface Presenter{
        List<Student> loadAllDataInRecyclerView(int position);

        void alertToDeleteClass(int position);
    }

    interface Repository{
        List<Student> getStudentsFromCurrentClass(int classroomId);

        void deleteStudentFromClass(int position);
    }
}
