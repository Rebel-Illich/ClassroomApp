package com.hunter.myclassroommap.viewStudent.editStudents;

import android.content.Context;


import com.hunter.myclassroommap.db.studentData.StudentRepository;



public class EditStudentPresenter implements EditStudentContract.Presenter {

    EditStudentContract.View view;
    StudentRepository repository;

    public EditStudentPresenter(EditStudentContract.View view, Context context) {
        this.view = view;
        this.repository = new StudentRepository(context);
    }

}
