package com.hunter.myclassroommap.viewStudent.mainPageStudent.mainStudentViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hunter.myclassroommap.ClassApp;
import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.db.studentData.StudentRepository;
import com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.mainClassViewModel.MainClassroomViewModel;

public class MainStudentViewModelFactory extends ViewModelProvider.AndroidViewModelFactory{

    private final StudentRepository studentRepository;
    private int classroomId;

    public MainStudentViewModelFactory(@NonNull Application application, int classroomId) {
        super(application);

        this.classroomId = classroomId;

        studentRepository = ((ClassApp) application).getStudentRepository();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class < T > modelClass) {
            return (T) new MainStudentViewModel(studentRepository, classroomId);
    }

}
