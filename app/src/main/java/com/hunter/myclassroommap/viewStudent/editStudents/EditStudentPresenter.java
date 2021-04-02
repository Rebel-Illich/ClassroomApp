package com.hunter.myclassroommap.viewStudent.editStudents;

import android.content.Context;

import com.hunter.myclassroommap.db.studentData.StudentRepository;
import com.hunter.myclassroommap.model.Student;

public class EditStudentPresenter implements EditStudentContract.Presenter {

    EditStudentContract.View view;
    private final StudentRepository repository;

    public EditStudentPresenter(EditStudentContract.View view, Context context) {
        this.view = view;
        this.repository = new StudentRepository(context);
    }


    @Override
    public void editDataClassroom(Student studentM) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                repository.updateData(studentM);

                view.onSuccess("Edit Classroom!");
            }
        }).start();
    }
}



