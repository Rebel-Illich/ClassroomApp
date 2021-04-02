package com.hunter.myclassroommap.viewStudent.editStudents;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.db.studentData.StudentRepository;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.Student;
import com.hunter.myclassroommap.viewClassroom.addClassroom.AddClassRoomFragment;
import com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.MainClassroomActivity;
import com.hunter.myclassroommap.viewClassroom.updateClassroom.UpdateClassroomFragment;
import com.hunter.myclassroommap.viewClassroom.updateClassroom.UpdateClassroomPresenter;
import com.hunter.myclassroommap.viewStudent.addStudents.AddStudentFragment;
import com.hunter.myclassroommap.viewStudent.addStudents.GenderSpinnerAdapter;
import com.hunter.myclassroommap.viewStudent.mainPageStudent.MainStudentFragment;

public class EditStudentFragment extends Fragment implements EditStudentContract.View {
    private static final String TAG = "EditStudentFragment";
    private MainClassroomActivity.WorksWithAdd worksWithAdd;
    private EditStudentContract.Presenter editStudentPresenter;
    private EditText firstNameStudent, secondNameStudent, middleNameStudent, ageStudent;
    private Spinner genderStudent;
    private Button editStudentButton, deleteButton;
    private GenderSpinnerAdapter genderSpinnerAdapter;
    private String[] spinnerValueGender;
    private ProgressDialog progressDialog;
    private Student studentM;
    private Integer currentStudentId;

    public static EditStudentFragment newInstance() {
        EditStudentFragment instance =  new EditStudentFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_edit_student, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editStudentPresenter = new EditStudentPresenter(this, view.getContext());

        firstNameStudent = view.findViewById(R.id.edit_firstName_student);
        secondNameStudent = view.findViewById(R.id.edit_secondName_student);
        middleNameStudent = view.findViewById(R.id.edit_middleName_student);
        ageStudent = view.findViewById(R.id.edit_age_student);
        genderStudent = view.findViewById(R.id.edit_gender_studentSpinner);
        editStudentButton = view.findViewById(R.id.editStudentButton);
        deleteButton = view.findViewById(R.id.delete_editStudent_button);

        spinnerValueGender = new String[]{
                "Male",
                "Female"
        };

        genderSpinnerAdapter = new GenderSpinnerAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item);
        genderSpinnerAdapter.addAll(spinnerValueGender);
        genderSpinnerAdapter.add("Gender");
        genderStudent.setAdapter(genderSpinnerAdapter);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

        editStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

                studentM.setFirstName(firstNameStudent.getText().toString());
                studentM.setLastName(secondNameStudent.getText().toString());
                studentM.setMiddleName(middleNameStudent.getText().toString());
                studentM.setStudentGender(genderStudent.getSelectedItem().toString());
                studentM.setStudentAge(Integer.parseInt(ageStudent.getText().toString()));

                editStudentPresenter.editDataClassroom(studentM);

                progressDialog = ProgressDialog.show(getActivity(),"Updating Classroom","updating...");
            } catch(NumberFormatException ex){
                Toast.makeText(getActivity(), "Format is not correct!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataFromMain();
    }

    private void getDataFromMain() {
        firstNameStudent.setText(studentM.getFirstName());
        secondNameStudent.setText(studentM.getLastName());
        middleNameStudent.setText(studentM.getMiddleName());
        ageStudent.setText(String.valueOf(studentM.getStudentAge()));
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

    public void setDataStudent(Student student) {
        this.studentM = student;
    }

    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete " + firstNameStudent + " ?");
        builder.setMessage("Are you sure you want to delete " + firstNameStudent.getText().toString() + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StudentRepository studentRepository = new StudentRepository(getActivity());
                studentRepository.deleteOneRow(studentM.getStudentId());
                requireActivity().onBackPressed();
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
