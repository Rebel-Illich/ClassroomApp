package com.hunter.myclassroommap.viewStudent.mainPageStudent;

import android.content.Context;

import com.hunter.myclassroommap.db.studentData.StudentRepository;
import com.hunter.myclassroommap.model.Student;

import java.util.List;

import io.reactivex.Single;


public class StudentPresenter implements StudentAndClassContract.Presenter  {
    StudentAndClassContract.View view;
    StudentAndClassContract.Repository studentRepository;

    public StudentPresenter(StudentAndClassContract.View view, Context context) {
        this.view = view;
        this.studentRepository = new StudentRepository(context);
    }

    @Override
    public Single<List<Student>> loadAllData(int classroomId) {
        return Single.fromPublisher(publisher -> {
            try {
                List<Student> studentList = studentRepository.getStudentsFromCurrentClass(classroomId);
                if (studentList.isEmpty()) {
                    publisher.onError(new RuntimeException("There are not students"));
                } else {
                    publisher.onNext(studentList);
                }
            } catch (Exception e) {
                publisher.onError(e);

            } finally {
                publisher.onComplete();
            }
        });
    }

    @Override
    public void alertToDeleteClass(int position) {
    }
}
