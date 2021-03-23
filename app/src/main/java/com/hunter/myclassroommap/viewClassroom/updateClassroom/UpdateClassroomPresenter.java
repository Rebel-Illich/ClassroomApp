package com.hunter.myclassroommap.viewClassroom.updateClassroom;

import android.content.Context;

import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.model.ClassRoom;

public class UpdateClassroomPresenter implements UpdateClassroomContract.Presenter {

    private final ClassroomRepository repository;
    UpdateClassroomContract.View view;

    public UpdateClassroomPresenter(UpdateClassroomContract.View view, Context context) {
        this.view = view;
        this.repository = new ClassroomRepository(context);
    }

    @Override
    public void editDataClassroom(ClassRoom classRoom) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                repository.updateData(classRoom);

                view.onSuccess("Update Classroom!");
            }
        }).start();
    }
}
