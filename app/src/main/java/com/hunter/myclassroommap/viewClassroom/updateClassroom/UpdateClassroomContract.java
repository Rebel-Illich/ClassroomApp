package com.hunter.myclassroommap.viewClassroom.updateClassroom;


import com.hunter.myclassroommap.model.ClassRoom;

public interface UpdateClassroomContract {

    interface View {
        void onSuccess(String messageAlert);

        void onError(String messageAlert);
    }

    interface Presenter {
        void editDataClassroom(ClassRoom classRoom);
    }

}
