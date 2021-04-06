package com.hunter.myclassroommap.viewClassroom.mainClassroom;

import android.database.Cursor;

import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.MainClassroomFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class MainPresenter implements MainContractPresenter {

    private final ClassroomRepository classroomRepository;


    public MainPresenter(ClassroomRepository classroomRepository, MainClassroomFragment mainClassroomFragment) {
        this.classroomRepository = classroomRepository;


    }

    public void detachView() {
    }

    public Single<List<ClassRoom>> loadAllClassRoom() {
        return Single.fromPublisher(publisher -> {
            try {
                List<ClassRoom> classRoomList = classroomRepository.getListFromDataBase();
                if (classRoomList.isEmpty()) {
                    publisher.onError(new RuntimeException("There are not class"));
                } else {
                    publisher.onNext(classRoomList);
                }
            } catch (Exception e) {
                publisher.onError(e);

            } finally {
                publisher.onComplete();
            }
        });
    }
}
