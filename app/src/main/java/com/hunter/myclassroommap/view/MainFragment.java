package com.hunter.myclassroommap.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.db.DatabaseHelper;

import java.util.ArrayList;


public class MainFragment extends Fragment {
    private AddFragment addFragment;
    private ControllerActivity.WorksWithAdd worksWithAdd;

    FloatingActionButton add_button;
    RecyclerView recyclerView;
    ImageView empty_imageView;
    TextView no_data;
    DatabaseHelper myDb;
    ArrayList<String> class_id, class_name, class_floor, class_roomnumber, class_numberOfStudents;
    CustomAdapter customAdapter;

    public static MainFragment newInstance(ControllerActivity.WorksWithAdd worksWithAdd) {
        MainFragment instance =  new MainFragment();
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

        myDb = new DatabaseHelper(getActivity());
        class_id = new ArrayList<>();
        class_name = new ArrayList<>();
        class_floor = new ArrayList<>();
        class_roomnumber = new ArrayList<>();
        class_numberOfStudents = new ArrayList<>();

        storeDataArrays();

        customAdapter = new CustomAdapter(worksWithAdd, getContext(), class_id, class_name, class_roomnumber, class_roomnumber,
                class_numberOfStudents);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void storeDataArrays() {
        Cursor cursor = myDb.readAllData();
        if (cursor.getCount() == 0) {
            empty_imageView.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                class_id.add(cursor.getString(0));
                class_name.add(cursor.getString(1));
                class_floor.add(cursor.getString(2));
                class_roomnumber.add(cursor.getString(3));
                class_numberOfStudents.add(cursor.getString(4));
            }
            empty_imageView.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }
}
