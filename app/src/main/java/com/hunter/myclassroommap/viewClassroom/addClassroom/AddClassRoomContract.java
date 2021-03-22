package com.hunter.myclassroommap.viewClassroom.addClassroom;

public interface AddClassRoomContract {

    interface View {

        void onSuccess(String messageAlert);
    }

    interface Presenter {

        void addNewClassRoom(String className, int classNumber, int floor, int classNumberOfStudents);

        void detachView();
    }
}
