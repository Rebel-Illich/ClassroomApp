package com.hunter.myclassroommap.viewClassroom.updateClassroom;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.FragmentController;


public class UpdateClassroomFragment extends Fragment implements UpdateClassroomContract.View {

    private UpdateClassroomContract.Presenter editClassroomPresenter;

    private FragmentController worksWithAdd;
    private ProgressDialog progressDialog;
    private EditText nameUpdate;
    private EditText roomUpdate;
    private EditText floorUpdate;
    private EditText countOfStudentsUpdate;
    Button updateButton, deleteButton;
    private ClassRoom classRoom;

    public static UpdateClassroomFragment newInstance() {
        UpdateClassroomFragment instance =  new UpdateClassroomFragment();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentController) {
            worksWithAdd = (FragmentController) context;
        }
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

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        worksWithAdd.returnBack();
                    }
                },2000);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getAndSetIntentData();
    }

    private void getAndSetIntentData() {
        nameUpdate.setText(classRoom.getClassroomName());
        roomUpdate.setText(String.valueOf(classRoom.getClassroomRoomNumber()));
        floorUpdate.setText(String.valueOf(classRoom.getClassroomFloor()));
        countOfStudentsUpdate.setText(String.valueOf(classRoom.getNumberOfStudents()));
    }

    public void classroomUpdateFields() {
        if (nameUpdate.getText().toString().length() == 0) {
            nameUpdate.setError("The First line is not filled!");
        } else if (roomUpdate.getText().toString().length() == 0) {
            roomUpdate.setError("The Second line is not filled!");
        } else if (floorUpdate.getText().toString().length() == 0) {
            floorUpdate.setError("The Four line is not filled!");
        } else if (countOfStudentsUpdate.getText().toString().length() == 0) {
            countOfStudentsUpdate.setError("The Four line is not filled!");
        } else {
            try{
            classRoom.setClassroomName(nameUpdate.getText().toString());
            classRoom.setNumberOfStudents(Long.parseLong(countOfStudentsUpdate.getText().toString()));
            classRoom.setClassroomFloor(Long.parseLong(floorUpdate.getText().toString()));
            classRoom.setClassroomRoomNumber(Long.parseLong(roomUpdate.getText().toString()));

            editClassroomPresenter.editDataClassroom(classRoom);

                progressDialog = ProgressDialog.show(getActivity(),"Updating Classroom","updating...");
            } catch(NumberFormatException ex){
                Toast.makeText(getActivity(), "Do not write long numbers!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete " + nameUpdate + " ?");
        builder.setMessage("Are you sure you want to delete " + nameUpdate.getText().toString() + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ClassroomRepository classroomRepository = new ClassroomRepository(getActivity());
                classroomRepository.deleteOneRow(classRoom.getId());
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

    public void setData(ClassRoom item) {
        this.classRoom = item;
    }
}
