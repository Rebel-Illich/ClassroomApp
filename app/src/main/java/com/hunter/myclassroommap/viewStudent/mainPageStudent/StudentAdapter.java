package com.hunter.myclassroommap.viewStudent.mainPageStudent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.Student;
import com.hunter.myclassroommap.viewClassroom.mainPagesClassroom.MainClassroomActivity;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private static final String TAG = "StudentAdapter";
    private Context context;
    private RecyclerView recyclerView;
    private List<Student> studentModelList;
    private MainClassroomActivity.WorksWithAdd worksWithAdd;
    Integer positionIdStudent = 0;
    CallBackPosition callBackPosition;

    public interface CallBackPosition{
        void deleteStudentGetPosition(int position);
    }


    public StudentAdapter(MainClassroomActivity.WorksWithAdd worksWithAdd, Context context, List<Student> studentModelList, RecyclerView recyclerView) {
        Log.d(TAG, "StudentAdapter() called with: context = [" + context + "], studentModelList = [" + studentModelList + "], recyclerView = [" + recyclerView + "]");
        this.worksWithAdd = worksWithAdd;
        this.context = context;
        this.studentModelList = studentModelList;
        this.recyclerView = recyclerView;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.student_row, parent, false);
        return new StudentAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student studentModel = studentModelList.get(position);

        holder.studentFirstName.setText(studentModel.getFirstName());
        holder.studentId.setText(Integer.toString(position + 1));
        holder.studentSecondName.setText(studentModel.getLastName());
   //   holder.deleteStudent.setImageResource(R.drawable.ic_base_delete);
        holder.editStudent.setImageResource(R.drawable.ic_baseline_edit);
//        holder.deleteStudent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                positionIdStudent = studentModel.getStudentId();
//                callBackPosition.deleteStudentGetPosition(positionIdStudent);
//                studentModelList.remove(position);
//            }
//        });

        holder.editStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                worksWithAdd.editStudent(studentModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentModelList == null ? 0 : studentModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView studentId, studentFirstName, studentSecondName;
        ImageButton editStudent, deleteStudent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentId = itemView.findViewById(R.id.student_id);
            studentFirstName = itemView.findViewById(R.id.student_name);
            studentSecondName = itemView.findViewById(R.id.student_lastname);
            editStudent = itemView.findViewById(R.id.buttonEditStudent);
         //   deleteStudent = itemView.findViewById(R.id.buttonDeleteStudent);
        }
    }
}
