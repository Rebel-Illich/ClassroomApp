package com.hunter.myclassroommap.presenter;

import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.view.mainPageClassroom.MainClassroomActivity;
import com.hunter.myclassroommap.view.mainPageClassroom.UpdateFragment;

public class UpdatePresenter {

    private static final String TAG = "UPDATE PRESENTER" ;
    private final ClassroomRepository classroomRepository;
    private UpdateFragment updateFragment;
    private MainClassroomActivity mainClassroomActivity;

    String id, name, floor, room, countOfStudents;

    public UpdatePresenter(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }



    public void setData(String id, String name, String floor, String room, String countOfStudent) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.countOfStudents = countOfStudent;
    }

    public interface UpdateView {

    }


    public void detachView() {
        updateFragment = null;
    }


    public void updateData() {
        ClassroomRepository classroomRepository = new ClassroomRepository(mainClassroomActivity);
        name = updateFragment.nameUpdate.getText().toString().trim();
        room = updateFragment.roomUpdate.getText().toString().trim();
        floor = updateFragment.floorUpdate.getText().toString().trim();
        countOfStudents = updateFragment.countOfStudentsUpdate.getText().toString().trim();
        classroomRepository.updateData(id, name, Integer.parseInt(room), Integer.parseInt(floor), Integer.parseInt(countOfStudents));
    }

    public void getAndSetIntentData(){
        if(mainClassroomActivity.getIntent().hasExtra("id") && mainClassroomActivity.getIntent().hasExtra("name") &&
                mainClassroomActivity.getIntent().hasExtra("room") && mainClassroomActivity.getIntent().hasExtra("countOfStudents")){

            //Getting Data from Intent
            id = mainClassroomActivity.getIntent().getStringExtra("id");
            name = mainClassroomActivity.getIntent().getStringExtra("title");
            room = mainClassroomActivity.getIntent().getStringExtra("author");
            floor = mainClassroomActivity.getIntent().getStringExtra("pages");

            //Setting Intent Data
            updateFragment.nameUpdate.setText(name);
            updateFragment.roomUpdate.setText(room);
            updateFragment.countOfStudentsUpdate.setText(countOfStudents);
            Log.d("stev", name+" "+room+" "+countOfStudents);
        }else{
            updateFragment.nameUpdate.setText(name);
            updateFragment.roomUpdate.setText(room);
            updateFragment.countOfStudentsUpdate.setText(countOfStudents);
            Log.d(TAG, "getAndSetIntentData: " + name+" "+room+" "+countOfStudents);

        }
    }

    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mainClassroomActivity);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               ClassroomRepository classroomRepository = new ClassroomRepository(mainClassroomActivity);
                classroomRepository.deleteOneRow(id);
                mainClassroomActivity.finish();
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
