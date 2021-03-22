package com.hunter.myclassroommap.presenter;

import android.database.Cursor;
import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.view.mainPageClassroom.MainListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContractPresenter {

    private final ClassroomRepository classroomRepository;
    private MainView mainListFragment;

    public MainPresenter(ClassroomRepository classroomRepository, MainView mainListFragment) {
        this.classroomRepository = classroomRepository;
        this.mainListFragment = mainListFragment;
    }

    public void detachView() {
        mainListFragment = null;
    }

    public void restoreDataArrays() {
        Cursor cursor = classroomRepository.readAllData();
        if (cursor.getCount() != 0) {
            List<MainListFragment.ClassRoom> classesList = new ArrayList<>();
            while (cursor.moveToNext()) {
                MainListFragment.ClassRoom newClass = new MainListFragment.ClassRoom();
                newClass.class_id = cursor.getString(0);
                newClass.class_name = cursor.getString(1);
                newClass.class_floor = cursor.getString(2);
                newClass.class_roomnumber = cursor.getString(3);
                newClass.class_numberOfStudents = cursor.getString(4);
                classesList.add(newClass);
            }
            mainListFragment.showData(classesList);
        }
}

     public interface MainView {
        void showData(List<MainListFragment.ClassRoom> data);
    }
}
