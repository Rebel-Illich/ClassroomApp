package com.hunter.myclassroommap.searchBy;

import android.annotation.SuppressLint;
import android.content.Context;

import com.hunter.myclassroommap.db.studentData.StudentRepository;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.Student;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchByPresenter implements SearchByContract.Presenter {

    private SearchByContract.View view;
    private StudentRepository studentRepository;

    public SearchByPresenter(SearchByContract.View callBack, Context context) {
        this.view = callBack;
        studentRepository = new StudentRepository(context);
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateClassRooms() {
        studentRepository.getAllClassRoom()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ClassRoom>>() {
                    @Override
                    public void accept(List<ClassRoom> classRoomList) throws Exception {
                        view.updateClassRooms(classRoomList);
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateStudents() {
        studentRepository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Student>>() {
                    @Override
                    public void accept(List<Student> students) throws Exception {
                        view.updateStudents(students);
                    }
                });
    }

    @Override
    public void onItemClassRoomClicked(ClassRoom item) {
        view.openClassRoomFragment(item);
    }

    @Override
    public void onItemStudentClicked(Student student) {
        view.openStudentFragment(student);
    }

}
