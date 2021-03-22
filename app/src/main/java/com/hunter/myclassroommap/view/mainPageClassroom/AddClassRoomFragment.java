package com.hunter.myclassroommap.view.mainPageClassroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.view.addClassroom.AddClassRoomContract;
import com.hunter.myclassroommap.view.addClassroom.AddClassRoomPresenter;


public class AddClassRoomFragment extends Fragment implements AddClassRoomContract.View {

    private ProgressDialog progressDialog;
    private MainClassroomActivity.WorksWithAdd worksWithAdd;
    private AddClassRoomContract.Presenter addPresenter;
    private EditText nameAddInput, roomAddInput, floorAddInput, countOfStudentsAddInput;
    private Button add_button;

    public static AddClassRoomFragment newInstance(MainClassroomActivity.WorksWithAdd worksWithAdd) {
        AddClassRoomFragment instance =  new AddClassRoomFragment();
        instance.worksWithAdd = worksWithAdd;
        return instance;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        addPresenter.detachView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addPresenter = new AddClassRoomPresenter(this, view.getContext());

        nameAddInput = view.findViewById(R.id.name_input);
        roomAddInput = view.findViewById(R.id.room_input);
        floorAddInput = view.findViewById(R.id.floor_input);
        countOfStudentsAddInput = view.findViewById(R.id.countOfStudents);
        add_button = view.findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditFields();
            }
        });
    }

    public void addEditFields() {
        if (nameAddInput.getText().toString().length() == 0) {
            nameAddInput.setError("The First line is not filled!");
        } else if (roomAddInput.getText().toString().length() == 0) {
            nameAddInput.setError("The Second line is not filled!");
        } else if (floorAddInput.getText().toString().length() == 0) {
            floorAddInput.setError("The Third line is not filled!");
        } else if(floorAddInput.getText().toString().length() == 0){
              countOfStudentsAddInput.setError("The Four line is not filled!");
        } else {
            String className = String.valueOf(nameAddInput.getText());
            int classNumber = Integer.parseInt(roomAddInput.getText().toString());
            int floor = Integer.parseInt(floorAddInput.getText().toString());
            int classNumberOfStudents = Integer.parseInt(countOfStudentsAddInput.getText().toString());

            addPresenter.addNewClassRoom(className, classNumber, floor, classNumberOfStudents);

            progressDialog = ProgressDialog.show(getActivity(),"Adding new Classroom","loading...");

        }
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
                        Toast.makeText(getActivity(), messageAlert, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        worksWithAdd.mainClass();
                    }
                },2000);
            }
        });
    }
}
