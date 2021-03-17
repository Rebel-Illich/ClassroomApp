package com.hunter.myclassroommap.view;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.db.DatabaseHelper;


public class UpdateFragment extends Fragment {
    private static final String TAG = "UpdateFragment";
    private ControllerActivity.WorksWithAdd worksWithAdd;
    EditText name_input, room_input, floor_input, countOfStudents_input;
    Button update_button, delete_button;

    String id, name, floor, room, countOfStudents;

    public void setData(String id, String name, String floor, String room, String countOfStudent) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.countOfStudents = countOfStudent;
    }

    public static UpdateFragment newInstance(ControllerActivity.WorksWithAdd worksWithAdd) {
        UpdateFragment instance =  new UpdateFragment();
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

        name_input = view.findViewById(R.id.name_input2);
        room_input = view.findViewById(R.id.room_input2);
        floor_input = view.findViewById(R.id.floor_input2);
        countOfStudents_input = view.findViewById(R.id.countOfStudents_input2);
        update_button = view.findViewById(R.id.update_button);
        delete_button = view.findViewById(R.id.delete_button);

        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHelper myDB = new DatabaseHelper(getActivity());
                name = name_input.getText().toString().trim();
                room = room_input.getText().toString().trim();
                floor = floor_input.getText().toString().trim();
                countOfStudents = countOfStudents_input.getText().toString().trim();
                myDB.updateData(id, name, Integer.parseInt(room), Integer.parseInt(floor), Integer.parseInt(countOfStudents));
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData(){
        if(getActivity().getIntent().hasExtra("id") && getActivity().getIntent().hasExtra("name") &&
                getActivity().getIntent().hasExtra("room") && getActivity().getIntent().hasExtra("countOfStudents")){

            //Getting Data from Intent
            id = getActivity().getIntent().getStringExtra("id");
            name = getActivity().getIntent().getStringExtra("title");
            room = getActivity().getIntent().getStringExtra("author");
            floor = getActivity().getIntent().getStringExtra("pages");

            //Setting Intent Data
            name_input.setText(name);
            room_input.setText(room);
            countOfStudents_input.setText(countOfStudents);
            Log.d("stev", name+" "+room+" "+countOfStudents);
        }else{
            name_input.setText(name);
            room_input.setText(room);
            countOfStudents_input.setText(countOfStudents);
            Log.d(TAG, "getAndSetIntentData: " + name+" "+room+" "+countOfStudents);

        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper myDB = new DatabaseHelper(getActivity());
                myDB.deleteOneRow(id);
                getActivity().finish();
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
