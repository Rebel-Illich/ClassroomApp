package com.hunter.myclassroommap.view.mainPageClassroom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.presenter.UpdatePresenter;


public class UpdateFragment extends Fragment {

    private static final String TAG = "UpdateFragment";

    private UpdatePresenter updatePresenter;

    private MainClassroomActivity.WorksWithAdd worksWithAdd;
    public EditText nameUpdate;
    public EditText roomUpdate;
    public EditText floorUpdate;
    public EditText countOfStudentsUpdate;
    Button updateButton, deleteButton;

    public static UpdateFragment newInstance(MainClassroomActivity.WorksWithAdd worksWithAdd) {
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

        nameUpdate = view.findViewById(R.id.name_input2);
        roomUpdate = view.findViewById(R.id.room_input2);
        floorUpdate = view.findViewById(R.id.floor_input2);
        countOfStudentsUpdate = view.findViewById(R.id.countOfStudents_input2);
        updateButton = view.findViewById(R.id.update_button);
        deleteButton = view.findViewById(R.id.delete_button);

        updatePresenter.getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
//        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
//        if (ab != null) {
//            ab.setTitle(name);
//        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              updatePresenter.updateData();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePresenter.confirmDialog();
            }
        });
    }

    public void setData(String id, String name, String floor, String room, String countOfStudent) {
    }
}
