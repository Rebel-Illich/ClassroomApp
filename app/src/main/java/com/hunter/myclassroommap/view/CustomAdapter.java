package com.hunter.myclassroommap.view;

import android.app.Activity;
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

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ControllerActivity.WorksWithAdd worksWithAdd;
    private Context context;
    private Activity activity;
    private ArrayList class_id, class_name, class_floor, class_roomnumber, class_numberOfStudents;

    public CustomAdapter(Activity activity, Context context, ArrayList class_id, ArrayList class_name, ArrayList class_floor,
                         ArrayList class_roomnumber, ArrayList class_numberOfStudents) {
        this.activity = activity;
        this.context = context;
        this.class_id = class_id;
        this.class_name = class_name;
        this.class_floor = class_floor;
        this.class_roomnumber = class_roomnumber;
        this.class_numberOfStudents = class_numberOfStudents;
    }

    public CustomAdapter(ControllerActivity.WorksWithAdd worksWithAdd, Context context, ArrayList<String> class_id, ArrayList<String> class_name, ArrayList<String> class_roomnumber, ArrayList<String> class_floor , ArrayList<String> class_numberOfStudents) {
        this.worksWithAdd = worksWithAdd;
        this.context = context;
        this.class_id = class_id;
        this.class_name = class_name;
        this.class_floor = class_floor;
        this.class_roomnumber = class_roomnumber;
        this.class_numberOfStudents = class_numberOfStudents;
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
        holder.class_id_txt.setText(String.valueOf(class_id.get(position)));
        holder.class_name.setText(String.valueOf(class_name.get(position)));
        holder.numberOfStudents.setText(String.valueOf(class_numberOfStudents.get(position)));
        holder.room_number.setText(String.valueOf(class_roomnumber.get(position)));

        //Recyclerview onClickListener

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                worksWithAdd.updateClass(
                    String.valueOf(class_id.get(position)),
                    String.valueOf(class_name.get(position)),
                    "",
                    String.valueOf(class_roomnumber.get(position)),
                    String.valueOf(class_numberOfStudents.get(position))
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return class_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView class_id_txt, class_name, numberOfStudents, room_number;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            class_id_txt = itemView.findViewById(R.id.class_id_txt);
            class_name = itemView.findViewById(R.id.class_name_txt);
            numberOfStudents = itemView.findViewById(R.id.numberOfStudents);
            room_number = itemView.findViewById(R.id.room_number);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
