package com.hunter.myclassroommap.viewStudent.mainPageStudent;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.util.ArrayList;

import android.widget.Toast;

import com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.FragmentsNavigator;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainStudentFragment extends Fragment implements StudentAndClassContract.View, StudentAdapter.CallBackPosition{

    private FragmentsNavigator fragmentsNavigator;

    private TextView classId, currentClassName, currentClassRoom, currentClassFloor, currentClassStudents;
    private FloatingActionButton floatingActionButton;
    private StudentAdapter studentAdapter;
    private StudentPresenter studentPresenter;
    private RecyclerView recyclerViewStudents;
    private ArrayList<Student> currentClassModel = new ArrayList<>();
    private ClassRoom classRoom;
    private ImageView empty_imageView;
    private Integer classroomId;
    private final List<Student> studentList = new ArrayList<Student>();
    private ProgressDialog progressDialog;

    public static MainStudentFragment newInstance(FragmentsNavigator fragmentsNavigator) {
        MainStudentFragment instance =  new MainStudentFragment();
        instance.fragmentsNavigator = fragmentsNavigator;
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
        empty_imageView = view.findViewById(R.id.empty_imageV);
        floatingActionButton = view.findViewById(R.id.floatingActionButtonStudents);
        recyclerViewStudents = view.findViewById(R.id.recyclerViewStudents);
        recyclerViewStudents.setHasFixedSize(true);

        getInfoAboutCurrClassroom();

        recyclerViewStudents = view.findViewById(R.id.recyclerViewStudents);

        floatingActionButton = view.findViewById(R.id.floatingActionButtonStudents);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentsNavigator.addStudent(classRoom);
            }
        });

        studentPresenter = new StudentPresenter( this, getActivity().getApplicationContext());
        classroomId = getActivity().getIntent().getIntExtra("classroomId",0);
        studentAdapter = new StudentAdapter(fragmentsNavigator, getActivity().getApplicationContext(), studentList, recyclerViewStudents);
        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewStudents.setAdapter(studentAdapter);
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
        studentList.clear();
        getStudentsData();
    }

    @SuppressLint("CheckResult")
    private void getStudentsData() {
        studentPresenter.loadAllData((int) classRoom.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        students -> {
                            if (students.isEmpty()) {
                                empty_imageView.setVisibility(View.VISIBLE);
                            } else  {
                                empty_imageView.setVisibility(View.GONE);
                            }
                            studentList.addAll(students);
                            studentAdapter.notifyDataSetChanged();
                        },
                        error ->{
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                );
    }

    public void setData(ClassRoom item) {
        this.classRoom = item;
    }

    @Override
    public void deleteStudentGetPosition(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure you want to delete this student from a classroom?")
                .setMessage("Please, select")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog = ProgressDialog.show(getActivity(),"Deleting student","deleting...");
 //                       studentPresenter.alertToDeleteClass(position);
                        studentAdapter.notifyItemRemoved(position);
                        studentAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Okay, your student in safe", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create().show();
    }
}
