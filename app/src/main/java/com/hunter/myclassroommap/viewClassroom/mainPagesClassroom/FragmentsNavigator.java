package com.hunter.myclassroommap.viewClassroom.mainPagesClassroom;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.Student;
import com.hunter.myclassroommap.searchBy.SearchByFragment;
import com.hunter.myclassroommap.viewClassroom.addClassroom.AddClassRoomFragment;
import com.hunter.myclassroommap.viewClassroom.updateClassroom.UpdateClassroomFragment;
import com.hunter.myclassroommap.viewStudent.addStudents.AddStudentFragment;
import com.hunter.myclassroommap.viewStudent.editStudents.EditStudentFragment;
import com.hunter.myclassroommap.viewStudent.mainPageStudent.MainStudentFragment;

public class FragmentsNavigator implements FragmentsNavigatorContract {

    private final FragmentManager manager;
    private AddClassRoomFragment addClassRoomFragment;
    private MainClassroomFragment mainClassroomFragment;
    private UpdateClassroomFragment updateClassroomFragment;
    private AddStudentFragment addStudentFragment;
    private MainStudentFragment mainStudentFragment;
    private EditStudentFragment editStudentFragment;


    public FragmentsNavigator(FragmentManager manager) {
        super();
        this.manager = manager;
    }

    public void addClass(){
        addClassRoomFragment = AddClassRoomFragment.newInstance(this);
        manager.beginTransaction()
                .replace(R.id.main_container, addClassRoomFragment)
                .addToBackStack(null)
                .commit();
    }


    public void mainClass() {
        mainClassroomFragment = MainClassroomFragment.newInstance(this);
        manager.beginTransaction()
                .replace(R.id.main_container, mainClassroomFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = manager;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    public void updateClass(ClassRoom item) {
        if (updateClassroomFragment == null) {
            updateClassroomFragment = UpdateClassroomFragment.newInstance();
        }
        updateClassroomFragment.setData(item);

        manager.beginTransaction()
                .replace(R.id.main_container, updateClassroomFragment)
                .addToBackStack(null)
                .commit();
    }

    public void mainStudent(ClassRoom item) {
        if (mainStudentFragment == null) {
            mainStudentFragment = MainStudentFragment.newInstance(this);
        }

        mainStudentFragment.setData(item);

        manager.beginTransaction()
                .replace(R.id.main_container, mainStudentFragment)
                .addToBackStack(null)
                .commit();
    }

    public void addStudent(ClassRoom classRoom) {
        addStudentFragment = AddStudentFragment.newInstance(classRoom);
        manager.beginTransaction()
                .replace(R.id.main_container, addStudentFragment)
                .addToBackStack(null)
                .commit();
    }

    public void editStudent(Student student) {
        if (editStudentFragment == null) {
            editStudentFragment = EditStudentFragment.newInstance();
        }
        editStudentFragment.setDataStudent(student);

        manager.beginTransaction()
                .replace(R.id.main_container, editStudentFragment)
                .addToBackStack(null)
                .commit();
    }

    public void showSearchFragment(){
        Fragment fragment = new SearchByFragment();
        FragmentsNavigatorContract fragmentsNavigatorContract = this;
        fragmentsNavigatorContract.replaceFragment(fragment);
    }
}