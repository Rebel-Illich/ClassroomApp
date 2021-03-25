package com.hunter.myclassroommap.viewClassroom.mainPagesClassroom;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.viewClassroom.addClassroom.AddClassRoomFragment;
import com.hunter.myclassroommap.viewClassroom.updateClassroom.UpdateClassroomFragment;
import com.hunter.myclassroommap.viewStudent.addStudents.AddStudentFragment;
import com.hunter.myclassroommap.viewStudent.mainPageStudent.MainStudentFragment;

public class MainClassroomActivity extends AppCompatActivity implements FragmentController {

    private AddClassRoomFragment addClassRoomFragment;
    private MainClassroomFragment mainClassroomFragment;
    private UpdateClassroomFragment updateClassroomFragment;
    private AddStudentFragment addStudentFragment;
    private MainStudentFragment mainStudentFragment;
    private WorksWithAdd worksWithAdd;
    private ClassroomRepository classroomRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controller_activity);

        worksWithAdd= new WorksWithAdd();

        mainClassroomFragment = MainClassroomFragment.newInstance(worksWithAdd);

        worksWithAdd.mainClass();
    }

    @Override
    public void mainClass() {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.main_container, mainClassroomFragment)
            .addToBackStack(null)
            .commit();
    }

    @Override
    public void addClass() {
        if (addClassRoomFragment == null) {
            addClassRoomFragment = AddClassRoomFragment.newInstance(worksWithAdd);
        }
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.main_container, addClassRoomFragment)
            .addToBackStack(null)
            .commit();
    }

    @Override
    public void updateClass(ClassRoom item) {
        if (updateClassroomFragment == null) {
            updateClassroomFragment = UpdateClassroomFragment.newInstance();
        }
        updateClassroomFragment.setData(item);

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.main_container, updateClassroomFragment)
            .addToBackStack(null)
            .commit();
    }

    @Override
    public void mainStudent(ClassRoom item) {
        if (mainStudentFragment == null) {
            mainStudentFragment = MainStudentFragment.newInstance(worksWithAdd);
        }

        mainStudentFragment.setData(item);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, mainStudentFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addStudent() {
        if (addStudentFragment == null) {
            addStudentFragment = AddStudentFragment.newInstance(worksWithAdd);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, addStudentFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void returnBack() {
        getSupportFragmentManager().popBackStack();
    }


    public class WorksWithAdd implements FragmentController {
        public WorksWithAdd() {
            super();
        }

        public void addClass(){
            if (addClassRoomFragment == null) {
                addClassRoomFragment = AddClassRoomFragment.newInstance(worksWithAdd);
            }
               getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, addClassRoomFragment)
                   .addToBackStack(null)
                   .commit();
        }


        public void mainClass() {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, mainClassroomFragment)
                .addToBackStack(null)
                .commit();
        }

        public void returnBack() {
            getSupportFragmentManager().popBackStack();
        }

        public void updateClass(ClassRoom item) {
            if (updateClassroomFragment == null) {
                updateClassroomFragment = UpdateClassroomFragment.newInstance();
            }
            updateClassroomFragment.setData(item);

            getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, updateClassroomFragment)
                .addToBackStack(null)
                .commit();
        }

        public void mainStudent(ClassRoom item) {
            if (mainStudentFragment == null) {
                mainStudentFragment = MainStudentFragment.newInstance(worksWithAdd);
            }

            mainStudentFragment.setData(item);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, mainStudentFragment)
                    .addToBackStack(null)
                    .commit();
        }

        public void addStudent() {
            if (addStudentFragment == null) {
                addStudentFragment = AddStudentFragment.newInstance(worksWithAdd);
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, addStudentFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                classroomRepository = new ClassroomRepository(MainClassroomActivity.this);
                classroomRepository.deleteAllData();

                  // Refresh Fragment
                mainClassroomFragment = MainClassroomFragment.newInstance(worksWithAdd);

                worksWithAdd.mainClass();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }
}
