package com.hunter.myclassroommap.view;

import android.database.Cursor;
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
import com.hunter.myclassroommap.db.DatabaseAdapter;
import com.hunter.myclassroommap.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;



public class MainListFragment extends Fragment implements MainPresenter.MainView {
    private AddFragment addFragment;
    private ControllerActivity.WorksWithAdd worksWithAdd;

    private MainPresenter presenter;

    FloatingActionButton add_button;
    RecyclerView recyclerView;
    ImageView empty_imageView;
    TextView no_data;
    List<ClassRoom> dataList = new ArrayList<>();
    ControllerAdapter controllerAdapter;

    public static MainListFragment newInstance(ControllerActivity.WorksWithAdd worksWithAdd) {
        MainListFragment instance =  new MainListFragment();
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
        presenter = new MainPresenter(new DatabaseAdapter(requireContext()), this);
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

        controllerAdapter = new ControllerAdapter(worksWithAdd, getContext(), dataList);
        recyclerView.setAdapter(controllerAdapter);
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
        controllerAdapter.notifyDataSetChanged();
    }

    public static class ClassRoom {
        public String class_id;
        public String class_name;
        public String class_floor;
        public String class_roomnumber;
        public String class_numberOfStudents;
    }
}
