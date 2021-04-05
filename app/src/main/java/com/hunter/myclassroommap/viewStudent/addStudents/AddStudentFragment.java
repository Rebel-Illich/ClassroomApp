package com.hunter.myclassroommap.viewStudent.addStudents;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.MainClassroomActivity;
import com.hunter.myclassroommap.viewStudent.mainPageStudent.MainStudentFragment;

public class AddStudentFragment extends Fragment implements AddStudentContract.View {

    private AddStudentContract.Presenter addStudentPresenter;
    private TextView firstNameStudent, lastNameStudent, middleNameStudent, ageStudent;
    private Spinner genderSpinner;
    private String[] spinnerValueGender;
    private GenderSpinnerAdapter genderSpinnerAdapter;
    private Button addStudentButton;
    private ProgressDialog progressDialog;
    Integer positionClass;
    private ClassRoom classRoom;
    private Context context;

    public static AddStudentFragment newInstance(ClassRoom classRoom) {
        AddStudentFragment instance =  new AddStudentFragment();
        instance.classRoom = classRoom;
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
                "Male"  ,
                "Female"
        };

        genderSpinnerAdapter = new GenderSpinnerAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item);
        genderSpinnerAdapter.addAll(spinnerValueGender);
        genderSpinnerAdapter.add("Gender");


        genderSpinner.setAdapter(genderSpinnerAdapter);
        addStudentPresenter = new AddStudentPresenter(this, getActivity().getApplicationContext());
        positionClass = getActivity().getIntent().getIntExtra("classroomPosition",0);
        addStudentButton.setOnClickListener(v -> {
            addStudentFields();
            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });
    }

    private void addStudentFields() {
        if (firstNameStudent.getText().toString().length() == 0) {
            firstNameStudent.setError("The First line is not filled!");
        } else if (lastNameStudent.getText().toString().length() == 0) {
            lastNameStudent.setError("The Second line is not filled!");
        } else if (middleNameStudent.getText().toString().length() == 0) {
            middleNameStudent.setError("The Third line is not filled!");
        } else if(ageStudent.getText().toString().length() == 0){
            ageStudent.setError("The Four line is not filled!");
        } else {
            try{
                String firstName = firstNameStudent.getText().toString().trim();
                String lastName = lastNameStudent.getText().toString().trim();
                String middleName = middleNameStudent.getText().toString().trim();
                String gender = genderSpinner.getSelectedItem().toString();
                int age = Integer.parseInt(ageStudent.getText().toString());

                addStudentPresenter.addButtonClicked(firstName, lastName, middleName, gender, age, classRoom.getId());

                progressDialog = ProgressDialog.show(getActivity(),"Adding new Student","loading...");

            } catch(NumberFormatException ex){
                Toast.makeText(getActivity(), "Do not write long numbers!", Toast.LENGTH_SHORT).show();
            }
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
                        requireActivity().onBackPressed();
                    }
                },1000);
            }
        });
    }

    @Override
    public void onError(String messageAlert) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), messageAlert, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        requireActivity().onBackPressed();
                    }
                },1000);
            }
        });
    }


    public void setData(ClassRoom item) {
        this.classRoom = item;
    }
}
