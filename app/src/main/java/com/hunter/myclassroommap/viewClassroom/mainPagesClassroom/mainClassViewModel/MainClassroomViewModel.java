package com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.mainClassViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.hunter.myclassroommap.ClassApp;

import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.model.ClassRoom;

import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainClassroomViewModel extends ViewModel {

    private ClassroomRepository classroomRepository;
 // private MediatorLiveData<List<ClassRoom>> classRooms = new MediatorLiveData<>();
    private MutableLiveData<List<ClassRoom>> classRooms = new MutableLiveData<>();
    public MutableLiveData<String> errorCR = new MutableLiveData<>();

    public MainClassroomViewModel (@NonNull ClassroomRepository classroomRepository) {
        super();
        this.classroomRepository = classroomRepository;
    }

    public LiveData<List<ClassRoom>> getClassRoomLD() {
        return classRooms;
    }

    @SuppressLint("CheckResult")
    public void updateClassrooms() {
        classroomRepository.getListFromDataBase()
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<ClassRoom>>() {
                    @Override
                    public void accept(List<ClassRoom> classRoomList) throws Exception {
                        classRooms.postValue(classRoomList);
                    }
                });
    }

//    public void getClassrooms() {
//        classRooms.addSource(classroomRepository.getListFromDataBaseLD(), list ->
//        {
//            if (list.isEmpty()) {
//                errorCR.setValue("List is Empty");
//            } else {
//                classRooms.setValue(list);
//            }
//        });
//    }
}

