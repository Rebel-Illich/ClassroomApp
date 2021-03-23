package com.hunter.myclassroommap.viewClassroom.mainClassroom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.viewClassroom.addClassroom.AddClassRoomFragment;
import com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.ClassroomAdapter;
import com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.MainClassroomActivity;

import java.util.ArrayList;
import java.util.List;



public class MainClassroomFragment extends Fragment implements MainPresenter.MainView {
    private AddClassRoomFragment addClassRoomFragment;
    private MainClassroomActivity.WorksWithAdd worksWithAdd;

    private MainPresenter presenter;

    FloatingActionButton add_button;
    RecyclerView recyclerView;
    ImageView empty_imageView;
    TextView no_data;
    List<ClassRoom> dataList = new ArrayList<>();
    ClassroomAdapter classroomAdapter;

    public static MainClassroomFragment newInstance(MainClassroomActivity.WorksWithAdd worksWithAdd) {
        MainClassroomFragment instance =  new MainClassroomFragment();
        instance.worksWithAdd = worksWithAdd;
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

        presenter = new MainPresenter(new ClassroomRepository(requireContext()), this);
        recyclerView = view.findViewById(R.id.recyclerView);

        add_button = view.findViewById(R.id.add_button);
        empty_imageView = view.findViewById(R.id.empty_imageview);
        no_data = view.findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                worksWithAdd.addClass();
            }
        });

        classroomAdapter = new ClassroomAdapter(worksWithAdd, getContext(), dataList);
        recyclerView.setAdapter(classroomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.restoreDataArrays();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
    }

    @Override
    public void showData(List<ClassRoom> data) {
        if (data.isEmpty()) {
            empty_imageView.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else  {
            empty_imageView.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
        dataList.clear();
        dataList.addAll(data);
        classroomAdapter.notifyDataSetChanged();
    }

}
