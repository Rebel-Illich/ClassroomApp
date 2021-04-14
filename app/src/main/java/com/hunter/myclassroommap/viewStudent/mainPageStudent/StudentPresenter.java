package com.hunter.myclassroommap.viewStudent.mainPageStudent;

import android.content.Context;

import com.hunter.myclassroommap.db.studentData.StudentRepository;
import com.hunter.myclassroommap.model.Student;

import java.util.List;
import java.util.concurrent.Callable;

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
        return studentRepository.getStudentsFromCurrentClass(classroomId)
                .flatMap(list -> {
                    if (list.isEmpty()) {
                        throw new RuntimeException("There are no class");
                    } else
                        return Single.just(list);
                });
    }

        public Single<Integer> getNum (int classroomId){
            return studentRepository.getNumFiles(classroomId);
        }

//        return Single.fromPublisher(publisher -> {
//            try {
//                List<Student> studentList = studentRepository.getStudentsFromCurrentClass(classroomId);
//                if (studentList.isEmpty()) {
//                    publisher.onError(new RuntimeException("There are not students"));
//                } else {
//                    publisher.onNext(studentList);
//                }
//            } catch (Exception e) {
//                publisher.onError(e);
//
//            } finally {
//                publisher.onComplete();
//            }
//        });
//        return Single.fromCallable(new Callable<List<Student>>() {
//            @Override
//            public List<Student> call() throws Exception {
//                List<Student> studentList = studentRepository.getStudentsFromCurrentClass(classroomId);
//                if(studentList.isEmpty()) throw new RuntimeException("There are not student!!!");
//                return studentList;
//            }
//        });
}
