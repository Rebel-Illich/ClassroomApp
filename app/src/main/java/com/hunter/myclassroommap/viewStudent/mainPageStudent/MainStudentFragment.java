package com.hunter.myclassroommap.viewStudent.mainPageStudent;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.Student;
import com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.MainClassroomActivity;

import java.util.ArrayList;

public class MainStudentFragment extends Fragment implements StudentAndClassContract.View{

    private MainClassroomActivity.WorksWithAdd worksWithAdd;

    private TextView classId, currentClassName, currentClassRoom, currentClassFloor, currentClassStudents;
    private FloatingActionButton floatingActionButton;
    private StudentAdapter studentAdapter;
    private StudentPresenter studentPresenter;
    private RecyclerView recyclerViewStudents;
    private ArrayList<Student> currentClassModel = new ArrayList<>();
    private ClassRoom classRoom;
    private Integer classroomId;

    public static MainStudentFragment newInstance(MainClassroomActivity.WorksWithAdd worksWithAdd) {
        MainStudentFragment instance =  new MainStudentFragment();
        instance.worksWithAdd = worksWithAdd;
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_class, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        classId = view.findViewById(R.id.student_id);
        currentClassName = view.findViewById(R.id.show_classroom_name);
        currentClassRoom = view.findViewById(R.id.room_number);
        currentClassFloor = view.findViewById(R.id.show_floor_text);
        currentClassStudents = view.findViewById(R.id.count_students);
        floatingActionButton = view.findViewById(R.id.floatingActionButtonStudents);
        recyclerViewStudents = view.findViewById(R.id.recyclerViewStudents);
        recyclerViewStudents.setHasFixedSize(true);

        recyclerViewStudents = view.findViewById(R.id.recyclerViewStudents);

        floatingActionButton = view.findViewById(R.id.floatingActionButtonStudents);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                worksWithAdd.addStudent();
            }
        });

        studentPresenter = new StudentPresenter( this, getActivity().getApplicationContext());
        classroomId = getActivity().getIntent().getIntExtra("classroomId",0);
        studentAdapter = new StudentAdapter(getActivity().getApplicationContext(), studentPresenter.loadAllData(classroomId), recyclerViewStudents);
        recyclerViewStudents.setAdapter(studentAdapter);
        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(requireContext()));

        getInfoAboutCurrClassroom();
    }

    private void getInfoAboutCurrClassroom() {
        currentClassName.setText(classRoom.getClassroomName());
        currentClassRoom.setText(String.valueOf(classRoom.getClassroomRoomNumber()));
        currentClassFloor.setText(String.valueOf(classRoom.getClassroomFloor()));
        currentClassStudents.setText(String.valueOf(classRoom.getNumberOfStudents()));
    }

    @Override
    public void onSuccess(String messageAlert) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getInfoAboutCurrClassroom();
    }

    public void setData(ClassRoom item) {
        this.classRoom = item;
    }
}
