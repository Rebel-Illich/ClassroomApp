package com.hunter.myclassroommap.viewClassroom.mainClassroom;

import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.MainClassroomFragment;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;

public class MainPresenter implements MainContractPresenter {

    private final ClassroomRepository classroomRepository;

    public MainPresenter(ClassroomRepository classroomRepository, MainClassroomFragment mainClassroomFragment) {
        this.classroomRepository = classroomRepository;
    }

    public void detachView() {
    }

    public Single<List<ClassRoom>> loadAllClassRoom() {
        return classroomRepository.getListFromDataBase()
                .flatMap(list -> {
                    if (list.isEmpty()) {
                        throw new RuntimeException("There are no class");
                    } else return Single.just(list);
                });
    }
}
