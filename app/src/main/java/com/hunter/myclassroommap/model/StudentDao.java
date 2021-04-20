package com.hunter.myclassroommap.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface StudentDao {

    @Insert
    long insertStudent(Student studentModel);

    @Update
    void updateStudents(Student studentM);

    @Delete
    void deleteOneRow(Student studentM);

    @Query("SELECT * FROM student_table")
    Single<List<Student>> getAllStudents();

    @Query("SELECT * FROM classroom_table")
    Single<List<ClassRoom>> getAllClassRoom();

    @Query("SELECT * FROM student_table WHERE classroomId = :classroomId")
    LiveData<List<Student>> getStudentsCurrentClass(int classroomId);

    @Query("SELECT COUNT(studentId) FROM student_table WHERE classroomId = :classroomId")
    Single<Integer> getCount(int classroomId);
}
