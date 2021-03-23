package com.hunter.myclassroommap.viewClassroom.classRoomInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.viewClassroom.classRoomInfo.ClassRoomDetailsContract;
import com.hunter.myclassroommap.viewClassroom.classRoomInfo.ClassRoomDetailsPresenter;

public class ClassroomDetailsFragment  implements ClassRoomDetailsContract {

    private TextView className;
    private TextView classNumber;
    private TextView studentsCount;
    private TextView floor;
    private ClassRoom classRoom;
    private ClassRoomDetailsPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}

