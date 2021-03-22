package com.hunter.myclassroommap.viewClassroom.updateClassroom;

import android.content.Context;

import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.model.ClassRoom;

public class UpdateClassroomPresenter implements UpdateClassroomContract.Presenter {

    private final ClassroomRepository repository;
    UpdateClassroomContract.View view;
    ClassRoom classRoom;

    public UpdateClassroomPresenter(UpdateClassroomContract.View view, Context context) {
        this.view = view;
        this.repository = new ClassroomRepository(context);
    }

    @Override
    public void editDataClassroom( String classroomName, int classroomRoomNumber, int classroomFloor, int numberOfStudents) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                repository.updateData(new ClassRoom(classroomName, classroomRoomNumber, classroomFloor, numberOfStudents));

                view.onSuccess("Update Classroom!");
            }
        }).start();
    }
}
