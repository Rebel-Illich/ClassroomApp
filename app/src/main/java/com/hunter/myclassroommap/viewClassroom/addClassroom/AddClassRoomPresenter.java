package com.hunter.myclassroommap.viewClassroom.addClassroom;

import android.content.Context;

import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.model.ClassRoom;

public class AddClassRoomPresenter implements AddClassRoomContract.Presenter {

    private final ClassroomRepository repository;
    private AddClassRoomContract.View view;

    public AddClassRoomPresenter(AddClassRoomContract.View view, Context context) {
        this.repository = new ClassroomRepository(context);
        this.view = view;
    }

    @Override
    public void addNewClassRoom(String classroomName, long classroomRoomNumber, long classroomFloor, long numberOfStudents) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                repository.insertData(new ClassRoom(classroomName, classroomRoomNumber, classroomFloor, numberOfStudents));
                view.onSuccess("New class is added!");
            }
        }).start();
    }

    @Override
    public void detachView() {

    }
}
