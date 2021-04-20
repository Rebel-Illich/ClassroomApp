package com.hunter.myclassroommap.viewStudent.editStudents;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.hunter.myclassroommap.db.classroomData.ClassroomDb;
import com.hunter.myclassroommap.db.studentData.StudentRepository;
import com.hunter.myclassroommap.model.Student;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class EditStudentViewModel extends ViewModel implements EditStudentContract.Presenter {

    EditStudentContract.View view;
    private final StudentRepository repository;

    public EditStudentViewModel(EditStudentContract.View view, Context context) {
        this.view = view;
        this.repository = new StudentRepository(ClassroomDb.getDatabase(context, null));
    }



    @SuppressLint("CheckResult")
    @Override
    public void editDataClassroom(Student studentM) {
        repository.updateData(studentM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(classRoom ->
                {
                    view.onSuccess("Edit Student!");
                },
                        error -> {
                    view.onError("It was not to edit student!");
                        });
    }
}
