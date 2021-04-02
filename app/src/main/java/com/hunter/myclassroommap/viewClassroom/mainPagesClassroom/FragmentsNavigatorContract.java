package com.hunter.myclassroommap.viewClassroom.mainPagesClassroom;

import androidx.fragment.app.Fragment;

import com.hunter.myclassroommap.model.ClassRoom;

public interface FragmentsNavigatorContract {
    void mainClass();

    void addClass();

    void updateClass(ClassRoom item);

    void mainStudent(ClassRoom item);

    void addStudent(ClassRoom classRoom);

    void replaceFragment(Fragment fragment);
}
