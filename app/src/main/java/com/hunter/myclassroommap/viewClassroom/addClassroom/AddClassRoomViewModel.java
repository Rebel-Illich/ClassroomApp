package com.hunter.myclassroommap.viewClassroom.addClassroom;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.hunter.myclassroommap.db.classroomData.ClassroomDb;
import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.model.ClassRoom;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddClassRoomViewModel extends ViewModel implements AddClassRoomContract.Presenter {


    private final ClassroomRepository repository;
    private AddClassRoomContract.View view;

    public AddClassRoomViewModel(AddClassRoomContract.View view, Context context) {
        this.repository = new ClassroomRepository(ClassroomDb.getDatabase(context, null));
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void addNewClassRoom(String classroomName, long classroomRoomNumber, long classroomFloor, long numberOfStudents) {
        repository.insertData(new ClassRoom(classroomName, classroomRoomNumber, classroomFloor, numberOfStudents))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(student -> {
                            view.onSuccess("New Class is added!");
                        },
                        error -> {
                            view.onError("New Class is NOT added!");
                        }
                );
    }

    @Override
    public void detachView() {
    }
}
