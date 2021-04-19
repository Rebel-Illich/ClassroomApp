package com.hunter.myclassroommap.viewClassroom.updateClassroom;

import android.annotation.SuppressLint;
import android.content.Context;

import com.hunter.myclassroommap.db.classroomData.ClassroomDb;
import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.model.ClassRoom;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdateClassroomPresenter implements UpdateClassroomContract.Presenter {

    private final ClassroomRepository repository;
    UpdateClassroomContract.View view;

    public UpdateClassroomPresenter(UpdateClassroomContract.View view, Context context) {
        this.view = view;
        this.repository = new ClassroomRepository(ClassroomDb.getDatabase(context, null));
    }

    @SuppressLint("CheckResult")
    @Override
    public void editDataClassroom(ClassRoom classRoom) {
                repository.updateData(classRoom)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(classRoom1 -> {
                                    view.onSuccess("New Class was edit!");
                                },
                                error -> {
                                    view.onError("New Class was NOT edit!");
                                }
                        );
    }
}
