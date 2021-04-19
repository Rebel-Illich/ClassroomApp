package com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.mainClassViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hunter.myclassroommap.ClassApp;
import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;

public class MainClassroomViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private final ClassroomRepository classroomRepository;

    public MainClassroomViewModelFactory(@NonNull Application application) {
        super(application);
        classroomRepository = ((ClassApp) application).getClassRepository();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class < T > modelClass) {
        if (modelClass.isAssignableFrom(MainClassroomViewModel.class)) {
            return (T) new MainClassroomViewModel(classroomRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }


}
