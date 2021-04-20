package com.hunter.myclassroommap.viewStudent.mainPageStudent.mainStudentViewModel;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hunter.myclassroommap.db.studentData.StudentRepository;
import com.hunter.myclassroommap.model.Student;
import java.util.List;

public class MainStudentViewModel extends ViewModel {

    private StudentRepository studentRepository;
    private MediatorLiveData<List<Student>> students = new MediatorLiveData<>();
    private final LiveData<List<Student>> studentLiveData;
    public MutableLiveData<String> errorCR = new MutableLiveData<>();
    private int classroomId;

    public MainStudentViewModel(@NonNull StudentRepository studentRepository, int classroomId) {
        super();
        this.classroomId = classroomId;
        this.studentRepository = studentRepository;

        studentLiveData = studentRepository.getStudentsFromCurrentClass(classroomId);
    }

    public LiveData<List<Student>> getStudents() {
        return studentLiveData;
    }

    @SuppressLint("CheckResult")
    public void updateStudents() {
        students.addSource(studentRepository.getStudentsFromCurrentClass(classroomId), list -> {
            students.setValue(list);
        });
    }
}

