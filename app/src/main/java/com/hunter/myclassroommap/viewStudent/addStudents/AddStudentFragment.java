package com.hunter.myclassroommap.viewStudent.addStudents;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.MainClassroomActivity;
import com.hunter.myclassroommap.viewStudent.mainPageStudent.MainStudentFragment;

public class AddStudentFragment extends Fragment implements AddStudentContract.View {

    private MainClassroomActivity.WorksWithAdd worksWithAdd;

    private AddStudentContract.Presenter addStudentPresenter;
    private TextView firstNameStudent, lastNameStudent, middleNameStudent, ageStudent;
    private Spinner genderSpinner;
    private String[] spinnerValueGender;
    private GenderSpinnerAdapter genderSpinnerAdapter;
    private Button addStudentButton;
    private ProgressDialog progressDialog;
    Integer positionClass;

    public static AddStudentFragment newInstance(MainClassroomActivity.WorksWithAdd worksWithAdd) {
        AddStudentFragment instance =  new AddStudentFragment();
        instance.worksWithAdd = worksWithAdd;
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_student, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstNameStudent = view.findViewById(R.id.add_first_name_student);
        lastNameStudent = view.findViewById(R.id.add_last_name_student);
        middleNameStudent = view.findViewById(R.id.add_middle_name);
        ageStudent = view.findViewById(R.id.add_age_student);
        addStudentButton = view.findViewById(R.id.add_student_button);
        genderSpinner = view.findViewById(R.id.spinner_gender);

        spinnerValueGender = new String[]{
                "Female",
                "Male"
        };

        genderSpinnerAdapter = new GenderSpinnerAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item);
        genderSpinnerAdapter.addAll(spinnerValueGender);
        genderSpinnerAdapter.add("Gender");


        genderSpinner.setAdapter(genderSpinnerAdapter);
        addStudentPresenter = new AddStudentPresenter(this, getActivity().getApplicationContext());
        positionClass = getActivity().getIntent().getIntExtra("classroomPosition",0);
        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }
    @Override
    public void onSuccess(String messageAlert) {

    }
}
