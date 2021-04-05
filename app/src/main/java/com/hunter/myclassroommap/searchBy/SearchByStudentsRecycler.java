package com.hunter.myclassroommap.searchBy;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.model.Student;

import java.util.ArrayList;
import java.util.List;

public class SearchByStudentsRecycler extends RecyclerView.Adapter<SearchByStudentsRecycler.ViewHolder> implements Filterable {

    private final List<Student> studentList;
    private final List<Student> filterStudentList;
    private CallBackAdapter callBackAdapter;

    public SearchByStudentsRecycler(List<Student> studentList, SearchByStudentsRecycler.CallBackAdapter callBackAdapter) {
        this.studentList = studentList;
        this.filterStudentList = new ArrayList<>(studentList);
        this.callBackAdapter = callBackAdapter;
    }

    public interface CallBackAdapter{
        void onItemStudentClicked(Student position);
    }

        @NonNull
    @Override
    public SearchByStudentsRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.student_card_search, parent, false), filterStudentList, callBackAdapter);
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull SearchByStudentsRecycler.ViewHolder holder, int position) {
        Student student = studentList.get(position);

        holder.studentName.setText( student.getLastName() + " " + student.getFirstName());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
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
            studentList.clear();
            studentList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView studentName;

        public ViewHolder(@NonNull View itemView, List<Student> filterStudentList, CallBackAdapter callBackAdapter) {
            super(itemView);

            studentName = itemView.findViewById(R.id.students_full_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBackAdapter.onItemStudentClicked(filterStudentList.get(getAdapterPosition()));
                }
            });
        }
    }
}
