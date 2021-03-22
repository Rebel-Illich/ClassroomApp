package com.hunter.myclassroommap.viewClassroom.mainPagesClassroom;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.viewClassroom.updateClassroom.UpdateClassroomContract;
import com.hunter.myclassroommap.viewClassroom.updateClassroom.UpdateClassroomPresenter;


public class UpdateClassroomFragment extends Fragment implements UpdateClassroomContract.View {

    private UpdateClassroomContract.Presenter editClassroomPresenter;

    private MainClassroomActivity.WorksWithAdd worksWithAdd;
    private ProgressDialog progressDialog;
    private Integer currentClassId;
    private EditText nameUpdate;
    private EditText roomUpdate;
    private EditText floorUpdate;
    private EditText countOfStudentsUpdate;
    Button updateButton, deleteButton;
    private String id;


    public static UpdateClassroomFragment newInstance(MainClassroomActivity.WorksWithAdd worksWithAdd) {
        UpdateClassroomFragment instance =  new UpdateClassroomFragment();
        instance.worksWithAdd = worksWithAdd;
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editClassroomPresenter = new UpdateClassroomPresenter(this, view.getContext());

        nameUpdate = view.findViewById(R.id.name_input2);
        roomUpdate = view.findViewById(R.id.room_input2);
        floorUpdate = view.findViewById(R.id.floor_input2);
        countOfStudentsUpdate = view.findViewById(R.id.countOfStudents_input2);
        updateButton = view.findViewById(R.id.update_button);
        deleteButton = view.findViewById(R.id.delete_button);

        getAndSetIntentData();
        
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(getActivity(),"Updating Classroom","updating...");
                classroomUpdateFields();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    @Override
    public void onSuccess(String messageAlert) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), messageAlert, Toast.LENGTH_LONG).show();
                        worksWithAdd.mainClass();
                    }
                },2000);
            }
        });
    }

    private void getAndSetIntentData(){
        currentClassId = getActivity().getIntent().getIntExtra("classroomId",0);
        nameUpdate.setText(String.valueOf(getActivity().getIntent().getStringExtra("classroomName")));
        roomUpdate.setText(String.valueOf(getActivity().getIntent().getIntExtra("classroomRoom",0)));
        floorUpdate.setText(String.valueOf(getActivity().getIntent().getIntExtra("classroomFloor",0)));
        countOfStudentsUpdate.setText(String.valueOf(getActivity().getIntent().getIntExtra("countOfStudents",0)));
    }

    public void classroomUpdateFields() {
        if (nameUpdate.getText().toString().length() == 0) {
            nameUpdate.setError("The First line is not filled!");
        } else if (roomUpdate.getText().toString().length() == 0) {
            roomUpdate.setError("The Second line is not filled!");
        } else if (floorUpdate.getText().toString().length() == 0) {
            floorUpdate.setError("The Third line is not filled!");
        } else {
            String classroomName = String.valueOf(nameUpdate.getText());
            int classroomRoomNumber = Integer.parseInt(countOfStudentsUpdate.getText().toString());
            int classroomFloor = Integer.parseInt(floorUpdate.getText().toString());
            int numberOfStudents = Integer.parseInt(countOfStudentsUpdate.getText().toString());
            editClassroomPresenter.editDataClassroom( classroomName,  classroomRoomNumber,  classroomFloor,  numberOfStudents);
        }
    }

    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete " + nameUpdate + " ?");
        builder.setMessage("Are you sure you want to delete " + nameUpdate + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ClassroomRepository classroomRepository = new ClassroomRepository(getActivity());
                classroomRepository.deleteOneRow(id);
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

    public void setData(String id, String name, String floor, String room, String countOfStudent) {
    }
}
