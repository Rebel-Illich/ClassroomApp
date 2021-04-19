package com.hunter.myclassroommap.viewClassroom.mainPagesClassroom;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.mainClassViewModel.MainClassroomViewModel;
import com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.mainClassViewModel.MainClassroomViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainClassroomFragment extends Fragment{

    public FragmentsNavigator fragmentsNavigator;

    MainClassroomViewModel classroomViewModel;

    FloatingActionButton add_button;
    RecyclerView recyclerView;
    ImageView empty_imageView;
    TextView no_data;
    List<ClassRoom> dataList = new ArrayList<>();
    ClassroomAdapter classroomAdapter;

    public static MainClassroomFragment newInstance(FragmentsNavigator fragmentsNavigator) {
        MainClassroomFragment instance =  new MainClassroomFragment();
        instance.fragmentsNavigator = fragmentsNavigator;
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        classroomViewModel =
                new ViewModelProvider(this, new MainClassroomViewModelFactory(Objects.requireNonNull(getActivity()).getApplication())).get(MainClassroomViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerView);

        add_button = view.findViewById(R.id.add_button);
        empty_imageView = view.findViewById(R.id.empty_imageview);
        no_data = view.findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentsNavigator.addClass();
            }
        });

        classroomAdapter = new ClassroomAdapter(fragmentsNavigator, getContext(), dataList);
        recyclerView.setAdapter(classroomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initObserver();
    }

    private void initObserver() {
        classroomViewModel.getClassRoomLD().observe(getViewLifecycleOwner(), list -> {
            if (list.isEmpty()) {
                empty_imageView.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.VISIBLE);
            } else {
                empty_imageView.setVisibility(View.GONE);
                no_data.setVisibility(View.GONE);
            }
            dataList.addAll(list);
            classroomAdapter.notifyDataSetChanged();
        });
        classroomViewModel.errorCR.observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        dataList.clear();
        getStudentsData();
    }
        @SuppressLint("CheckResult")
        private void getStudentsData() {
            classroomViewModel.updateClassrooms();
        }
    }


