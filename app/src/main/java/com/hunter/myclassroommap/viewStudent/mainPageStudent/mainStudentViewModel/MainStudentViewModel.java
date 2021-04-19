package com.hunter.myclassroommap.viewStudent.mainPageStudent.mainStudentViewModel;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.db.studentData.StudentRepository;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.Student;

import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainStudentViewModel extends ViewModel{


    private StudentRepository studentRepository;
    private MutableLiveData<List<Student>> students = new MutableLiveData<>();
    public MutableLiveData<String> errorCR = new MutableLiveData<>();
    private int classroomId;

    public MainStudentViewModel (@NonNull StudentRepository studentRepository, int classroomId) {
        super();
        this.classroomId = classroomId;
        this.studentRepository = studentRepository;
    }

    public LiveData<List<Student>> getStudent() {
        return students;
    }

    @SuppressLint("CheckResult")
    public void updateStudents() {
        studentRepository.getStudentsFromCurrentClass(classroomId)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Student>>() {
                    @Override
                    public void accept(List<Student> studentList) throws Exception {
                        students.postValue(studentList);
                    }
                });
    }

}
