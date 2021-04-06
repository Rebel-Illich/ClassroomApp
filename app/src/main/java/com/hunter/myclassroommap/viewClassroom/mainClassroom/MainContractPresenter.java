package com.hunter.myclassroommap.viewClassroom.mainClassroom;

import com.hunter.myclassroommap.model.ClassRoom;

import java.util.List;

import io.reactivex.Single;

public interface MainContractPresenter {

//    void restoreDataArrays();

    void detachView();

    Single<List<ClassRoom>> loadAllClassRoom();

}
