package com.hunter.myclassroommap.viewClassroom.mainClassroom;

import android.database.Cursor;

import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.model.ClassRoom;

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
            List<ClassRoom> classesList = new ArrayList<>();
            while (cursor.moveToNext()) {
                ClassRoom newClass = new ClassRoom();
                newClass.setId(Long.parseLong(cursor.getString(0)));
                newClass.setClassroomName(cursor.getString(1));
                newClass.setClassroomFloor(Long.parseLong(cursor.getString(2)));
                newClass.setClassroomRoomNumber(Long.parseLong(cursor.getString(3)));
                newClass.setNumberOfStudents(Long.parseLong(cursor.getString(4)));
                classesList.add(newClass);
            }
            mainListFragment.showData(classesList);
        }
    }

    public interface MainView {
        void showData(List<ClassRoom> data);
    }
}
