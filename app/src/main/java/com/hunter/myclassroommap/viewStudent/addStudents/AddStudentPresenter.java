package com.hunter.myclassroommap.viewStudent.addStudents;

import android.annotation.SuppressLint;
import android.content.Context;

import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.db.studentData.StudentRepository;
import com.hunter.myclassroommap.model.Student;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddStudentPresenter implements AddStudentContract.Presenter {
    AddStudentContract.View view;
    AddStudentContract.Repository repository;
    Student studentModel;

    public AddStudentPresenter(AddStudentContract.View view, Context context) {
        this.view = view;
        this.repository = new StudentRepository(context);
    }

    @SuppressLint("CheckResult")
    @Override
    public void addButtonClicked(String firstName, String lastName, String middleName, String gender, int age, long classId) {
        studentModel = new Student(0, firstName, lastName, middleName, gender, age, classId);
        repository.addStudent(studentModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(student -> {
                    view.onSuccess("New student is added!");
                },
                error -> {
                view.onError("New student is NOT added!");
                }
            );
    }
}
