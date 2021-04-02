package com.hunter.myclassroommap.searchBy;

import android.content.Context;

import com.hunter.myclassroommap.db.studentData.StudentRepository;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.Student;

public class SearchByPresenter implements SearchByContract.Presenter {

    private SearchByContract.View view;
    private StudentRepository studentRepository;

    public SearchByPresenter(SearchByContract.View callBack, Context context) {
        this.view = callBack;
        studentRepository = new StudentRepository(context);
    }

    @Override
    public void updateClassRooms() {
        view.updateClassRooms(studentRepository.getAllClassRoom());
    }

    @Override
    public void updateStudents() {
        view.updateStudents(studentRepository.getAll());
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
