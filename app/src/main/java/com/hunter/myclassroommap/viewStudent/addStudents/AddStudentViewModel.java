package com.hunter.myclassroommap.viewStudent.addStudents;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.hunter.myclassroommap.db.classroomData.ClassroomDb;
import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.db.studentData.StudentRepository;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.Student;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.schedulers.Schedulers;

public class AddStudentViewModel extends ViewModel implements AddStudentContract.Presenter {
    AddStudentContract.View view;
    AddStudentContract.Repository repository;
    Student studentModel;

    public AddStudentViewModel(AddStudentContract.View view, Context context) {
        this.view = view;
        this.repository = new StudentRepository(ClassroomDb.getDatabase(context, null));
    }

    @SuppressLint("CheckResult")
    @Override
    public void addButtonClicked(String firstName, String lastName, String middleName, String gender, int age, long classId) {
        studentModel = new Student(0, firstName, lastName, middleName, gender, age, classId);
        repository.addStudent(studentModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
                .flatMap(student -> repository.getNumFiles((int) classId))
       //         .flatMap(count -> repository.updateClassRoomStudentCount((int) classId, count))
            .subscribe(student -> {
                    view.onSuccess("New student is added!");
                },
                error -> {
                view.onError("New student is NOT added!");
                }
            );
    }
}
