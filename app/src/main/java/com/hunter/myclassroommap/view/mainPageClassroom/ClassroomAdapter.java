package com.hunter.myclassroommap.view.mainPageClassroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hunter.myclassroommap.R;

import java.util.List;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.MyViewHolder> {

    private MainClassroomActivity.WorksWithAdd worksWithAdd;
    private Context context;
    private List<MainListFragment.ClassRoom> classRoomList;

    public ClassroomAdapter(MainClassroomActivity.WorksWithAdd worksWithAdd, Context context, List<MainListFragment.ClassRoom> classRoomList) {
        this.worksWithAdd = worksWithAdd;
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
        MainListFragment.ClassRoom item = classRoomList.get(position);
        holder.class_id_txt.setText(String.valueOf(item.class_id));
        holder.class_name.setText(String.valueOf(item.class_name));
        holder.numberOfStudents.setText(String.valueOf(item.class_numberOfStudents));
       // holder.room_number.setText(String.valueOf(item.class_roomnumber));

        //Recyclerview onClickListener

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                worksWithAdd.updateClass(
                    String.valueOf(item.class_name),
                    String.valueOf(item.class_floor),
                    "",
                    String.valueOf(item.class_roomnumber),
                    String.valueOf(item.class_numberOfStudents)
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return classRoomList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView class_id_txt, class_name, numberOfStudents, room_number;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            class_id_txt = itemView.findViewById(R.id.class_id_txt);
            class_name = itemView.findViewById(R.id.class_name_txt);
            numberOfStudents = itemView.findViewById(R.id.numberOfStudents);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
