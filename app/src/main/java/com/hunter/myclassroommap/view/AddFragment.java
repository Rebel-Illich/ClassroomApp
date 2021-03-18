package com.hunter.myclassroommap.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.db.DatabaseHelper;
import com.hunter.myclassroommap.presenter.AddContractPresenter;


public class AddFragment extends Fragment implements AddContractPresenter {

    private ControllerActivity.WorksWithAdd worksWithAdd;
    EditText title_input, author_input, pages_input, countOfStudents;
    Button add_button;

    public static AddFragment newInstance(ControllerActivity.WorksWithAdd worksWithAdd) {
        AddFragment instance =  new AddFragment();
        instance.worksWithAdd = worksWithAdd;
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title_input = view.findViewById(R.id.title_input);
        author_input = view.findViewById(R.id.author_input);
        pages_input = view.findViewById(R.id.pages_input);
        countOfStudents = view.findViewById(R.id.countOfStudents);
        add_button = view.findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(getActivity());
//                myDB.insertData(title_input.getText().toString().trim(),
//                        Integer.parseInt(author_input.getText().toString().trim()),
//                        Integer.parseInt(pages_input.getText().toString().trim()),
//                        Integer.parseInt(countOfStudents.getText().toString().trim()));
            }
        });
    }
    }
