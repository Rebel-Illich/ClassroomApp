package com.hunter.myclassroommap.viewClassroom.mainPagesClassroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.model.ClassRoom;

import java.util.List;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.MyViewHolder> {

    private FragmentsNavigator fragmentsNavigator;
    private Context context;
    private List<ClassRoom> classRoomList;

    public ClassroomAdapter(FragmentsNavigator fragmentsNavigator, Context context, List<ClassRoom> classRoomList) {
        this.fragmentsNavigator = fragmentsNavigator;
        this.context = context;
        this.classRoomList = classRoomList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ClassRoom item = classRoomList.get(position);
        holder.class_id_txt.setText(String.valueOf(item.getId()));
        holder.class_name.setText(String.valueOf(item.getClassroomName()));
        holder.numberOfStudents.setText("№ " + item.getNumberOfStudents());

        holder.editClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentsNavigator.updateClass(
                        item
                );
            }
        });

        //Recyclerview onClickListener

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentsNavigator.mainStudent(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classRoomList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageButton editClassroom;
        TextView class_id_txt, class_name, numberOfStudents, room_number;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            class_id_txt = itemView.findViewById(R.id.class_id_txt);
            class_name = itemView.findViewById(R.id.class_name_txt);
            numberOfStudents = itemView.findViewById(R.id.numberOfStudents);

            editClassroom = itemView.findViewById(R.id.buttonEditClass);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
