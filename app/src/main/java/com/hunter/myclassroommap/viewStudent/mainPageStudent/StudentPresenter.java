package com.hunter.myclassroommap.viewStudent.mainPageStudent;

import android.content.Context;

import com.hunter.myclassroommap.db.studentData.StudentRepository;
import com.hunter.myclassroommap.model.Student;

import java.util.List;

public class StudentPresenter implements StudentAndClassContract.Presenter  {
    StudentAndClassContract.View view;
    StudentAndClassContract.Repository studentRepository;

    public StudentPresenter(StudentAndClassContract.View view, Context context) {
        this.view = view;
        this.studentRepository = new StudentRepository(context);
    }

    @Override
    public List<Student> loadAllData(int classroomId) {
        return studentRepository.getStudentsFromCurrentClass(classroomId);
    }
}