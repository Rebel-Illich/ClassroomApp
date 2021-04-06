package com.hunter.myclassroommap.viewClassroom.addClassroom;

public interface AddClassRoomContract {

    interface View {

        void onSuccess(String messageAlert);

        void onError(String messageAlert);
    }

    interface Presenter {

        void addNewClassRoom(String className, long classNumber, long floor, long classNumberOfStudents);

        void detachView();
    }
}
