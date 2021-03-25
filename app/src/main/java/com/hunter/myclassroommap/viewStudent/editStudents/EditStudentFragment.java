package com.hunter.myclassroommap.viewStudent.editStudents;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.model.Student;
import com.hunter.myclassroommap.viewStudent.addStudents.GenderSpinnerAdapter;

public class EditStudentFragment extends Fragment implements EditStudentContract.View {

    private EditStudentPresenter editStudentPresenter;
    private EditText firstNameStudent, secondNameStudent, middleNameStudent, ageStudent;
    private Spinner genderStudent;
    private Button editStudentButton;
    private GenderSpinnerAdapter genderSpinnerAdapter;
    private String[] spinnerValueGender;
    private Integer currentStudentId;
    private ProgressDialog progressDialog;
    private Student student;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_edit_student, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editStudentPresenter = new EditStudentPresenter(this, getActivity().getApplicationContext());

        firstNameStudent = view.findViewById(R.id.edit_firstName_student);
        secondNameStudent = view.findViewById(R.id.edit_secondName_student);
        middleNameStudent = view.findViewById(R.id.edit_middleName_student);
        ageStudent = view.findViewById(R.id.edit_age_student);
        genderStudent = view.findViewById(R.id.edit_gender_studentSpinner);
        editStudentButton = view.findViewById(R.id.editStudentButton);

        spinnerValueGender = new String[]{
                "Male",
                "Female"
        };

        genderSpinnerAdapter = new GenderSpinnerAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item);
        genderSpinnerAdapter.addAll(spinnerValueGender);
        genderSpinnerAdapter.add("Gender");
        genderStudent.setAdapter(genderSpinnerAdapter);

        getDataFromMain();

        editStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentEditFields();
            }
        });
    }

    private void studentEditFields() {
            if (firstNameStudent.getText().toString().length() == 0) {
                firstNameStudent.setError("The First line is not filled!");
            } else if (secondNameStudent.getText().toString().length() == 0) {
                secondNameStudent.setError("The Second line is not filled!");
            } else if (middleNameStudent.getText().toString().length() == 0) {
                middleNameStudent.setError("The Four line is not filled!");
            } else if (ageStudent.getText().toString().length() == 0) {
                ageStudent.setError("The Four line is not filled!");
            } else {
                try{
                    student.setFirstName(firstNameStudent.getText().toString());
                    student.setLastName(secondNameStudent.getText().toString());
                    student.setMiddleName(middleNameStudent.getText().toString());
                    student.setStudentAge(Integer.parseInt(ageStudent.getText().toString()));

                 // editStudentPresenter.editDataClassroom(student);

                    progressDialog = ProgressDialog.show(getActivity(),"Updating Classroom","updating...");
                } catch(NumberFormatException ex){
                    Toast.makeText(getActivity(), "Format is not correct!", Toast.LENGTH_SHORT).show();
                }
            }
        }

    private void getDataFromMain() {
        firstNameStudent.setText(student.getFirstName());
        secondNameStudent.setText(student.getLastName());
        middleNameStudent.setText(student.getMiddleName());
        ageStudent.setText(String.valueOf(student.getStudentAge()));
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
                        requireActivity().onBackPressed();
                    }
                },2000);
            }
        });
    }
}
