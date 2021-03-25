package com.hunter.myclassroommap.viewStudent.addStudents;

import android.content.Context;

import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.db.studentData.StudentRepository;
import com.hunter.myclassroommap.model.Student;

public class AddStudentPresenter implements AddStudentContract.Presenter {
    AddStudentContract.View view;
    AddStudentContract.Repository repository;
    Student studentModel;

    public AddStudentPresenter(AddStudentContract.View view, Context context) {
        this.view = view;
        this.repository = new StudentRepository(context);
    }

    @Override
    public void addButtonClicked(String firstName, String lastName, String middleName, String gender, int age, int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                studentModel = new Student(0, firstName, lastName, middleName, gender, age, position);
                repository.addStudent(studentModel);
                view.onSuccess("New student is added!");
            }
        }).start();
    }
}