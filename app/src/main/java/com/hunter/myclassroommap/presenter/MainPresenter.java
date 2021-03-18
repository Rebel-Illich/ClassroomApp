package com.hunter.myclassroommap.presenter;

import android.database.Cursor;
import com.hunter.myclassroommap.db.DatabaseAdapter;
import com.hunter.myclassroommap.db.DatabaseHelper;
import com.hunter.myclassroommap.view.MainListFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainPresenter {

    private final DatabaseAdapter databaseAdapter;
    private MainListFragment mainListFragment;
    private DatabaseHelper myDb;


    public MainPresenter( DatabaseAdapter databaseAdapter, MainListFragment mainListFragment) {
        this.databaseAdapter = databaseAdapter;
        this.mainListFragment = mainListFragment;
    }

    public void attachView(MainListFragment mainListFragment) {
        mainListFragment = mainListFragment;
    }

    public void detachView() {
        mainListFragment = null;
    }

    public List<MainListFragment.ClassRoom> restoreDataArrays(ArrayList<String> class_id, ArrayList<String> class_name, ArrayList<String> class_floor, ArrayList<String> class_roomnumber, ArrayList<String> class_numberOfStudents) {
        Cursor cursor = databaseAdapter.readAllData();
        if (cursor.getCount() == 0) {
            return Collections.EMPTY_LIST;
        } else {
            List<MainListFragment.ClassRoom> classesList = new ArrayList<>();
            while (cursor.moveToNext()) {
                MainListFragment.ClassRoom newClass = new MainListFragment.ClassRoom();
                newClass.class_id = cursor.getString(0);
                newClass.class_name = cursor.getString(1);
                newClass.class_floor= cursor.getString(2);
                newClass.class_roomnumber =cursor.getString(3);
                newClass.class_numberOfStudents= cursor.getString(4);
                classesList.add(newClass);
            }
            return classesList;
        }
}
}
