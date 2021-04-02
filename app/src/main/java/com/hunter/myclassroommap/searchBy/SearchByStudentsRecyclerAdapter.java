package com.hunter.myclassroommap.searchBy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.model.Student;

import java.util.ArrayList;
import java.util.List;

public class SearchByStudentsRecyclerAdapter extends RecyclerView.Adapter<SearchByStudentsRecyclerAdapter.ViewHolder> implements Filterable {

    private final List<Student> studentList;
    private final List<Student> filterStudentList;
    private SearchByContract.Presenter presenter;

    public SearchByStudentsRecyclerAdapter(List<Student> studentList) {
        this.studentList = studentList;
        this.filterStudentList = new ArrayList<>(studentList);
    }

        @NonNull
    @Override
    public SearchByStudentsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.student_card_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchByStudentsRecyclerAdapter.ViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.studentName.setText( student.getLastName() + " " + student.getFirstName());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void registerSearchByListener(SearchByContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Filter getFilter() {
        return studentFilter;
    }

    private final Filter studentFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Student> resultList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                resultList.addAll(filterStudentList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Student student : filterStudentList) {
                    if (student.getFirstName().toLowerCase().contains(filterPattern)) {
                        resultList.add(student);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = resultList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView studentName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.students_full_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onItemStudentClicked(filterStudentList.get(getAdapterPosition()));
                }
            });
        }
    }
}
