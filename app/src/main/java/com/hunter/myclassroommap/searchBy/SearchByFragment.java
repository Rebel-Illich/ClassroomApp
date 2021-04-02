package com.hunter.myclassroommap.searchBy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.Student;
import com.hunter.myclassroommap.viewClassroom.updateClassroom.UpdateClassroomFragment;
import com.hunter.myclassroommap.viewStudent.editStudents.EditStudentFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchByFragment extends Fragment implements SearchByContract.View, SearchByStudentsRecycler.CallBackAdapter, SearchByClassRoomsRecyclerAdapter.CallBackClassRoomAdapter {

    private SearchByClassRoomsRecyclerAdapter searchByClassRoomsRecyclerAdapter;
    private SearchByStudentsRecycler searchByStudentsRecycler;
    private SearchByContract.Presenter presenter;

    private List<ClassRoom> classRoomList = new ArrayList<>();
    private List<Student> studentList = new ArrayList<>();

    private EditStudentFragment editStudentFragment;
    private UpdateClassroomFragment updateClassroomFragment;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private Spinner spinner;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_searchby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.edit_search_view_query);

        presenter = new SearchByPresenter(this, requireContext());

        recyclerView = view.findViewById(R.id.search_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spinner = view.findViewById(R.id.spinner_search_by);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){

                    recyclerView.setAdapter(searchByClassRoomsRecyclerAdapter);

                   searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                       @Override
                       public boolean onQueryTextSubmit(String query) {
                           return false;
                       }

                       @Override
                       public boolean onQueryTextChange(String newText) {
                           searchByClassRoomsRecyclerAdapter.getFilter().filter(newText);
                           return false;
                       }
                   });
                } else {

                    recyclerView.setAdapter(searchByStudentsRecycler);

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            searchByStudentsRecycler.getFilter().filter(newText);
                            return false;
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.updateStudents();
        presenter.updateClassRooms();
    }

    @Override
    public void updateClassRooms(List<ClassRoom> allClassRoom) {
        classRoomList.clear();
        classRoomList.addAll(allClassRoom);
        searchByClassRoomsRecyclerAdapter = new SearchByClassRoomsRecyclerAdapter(classRoomList, this);
        recyclerView.setAdapter(searchByClassRoomsRecyclerAdapter);
        searchByClassRoomsRecyclerAdapter.notifyDataSetChanged();    }

    @Override
    public void updateStudents(List<Student> allStudents) {
        studentList.clear();
        studentList.addAll(allStudents);
        searchByStudentsRecycler = new SearchByStudentsRecycler(studentList, this);
        recyclerView.setAdapter(searchByStudentsRecycler);
        recyclerView.setAdapter(searchByStudentsRecycler);
        searchByStudentsRecycler.notifyDataSetChanged();
    }

    @Override
    public void openStudentFragment(Student student) {
        if (editStudentFragment == null) {
            editStudentFragment = EditStudentFragment.newInstance();
        }
        editStudentFragment.setDataStudent(student);

                 getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, editStudentFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openClassRoomFragment(ClassRoom item) {
        if (updateClassroomFragment == null) {
            updateClassroomFragment = UpdateClassroomFragment.newInstance();
        }
        updateClassroomFragment.setData(item);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, updateClassroomFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onItemStudentClicked(Student position) {
        presenter.onItemStudentClicked(position);
    }

    @Override
    public void onItemClassRoomClicked(ClassRoom classRoom) {
        presenter.onItemClassRoomClicked(classRoom);
    }
}
