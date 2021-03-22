package com.hunter.myclassroommap.view.addClassroom;

public interface AddClassRoomContract {

    interface View {

        void onSuccess(String messageAlert);
    }

    interface Presenter {

        void addNewClassRoom(String className, int classNumber, int floor, int classNumberOfStudents);

        void detachView();
    }
}
