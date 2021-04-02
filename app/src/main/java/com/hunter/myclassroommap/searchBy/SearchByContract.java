package com.hunter.myclassroommap.searchBy;

import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.Student;

import java.util.List;

public interface SearchByContract {
    interface View {
        void updateClassRooms(List<ClassRoom> allClassRoom);

        void updateStudents(List<Student> all);

        void openStudentFragment(Student student);

        void openClassRoomFragment(ClassRoom item);
    }

    interface Presenter {
        void updateClassRooms();

        void updateStudents();

        void onItemClassRoomClicked(ClassRoom classRoom);

        void onItemStudentClicked(Student student);
    }
}
