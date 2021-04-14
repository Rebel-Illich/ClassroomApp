package com.hunter.myclassroommap.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface ClassRoomDao {

    @Insert
    long insertClassroom(ClassRoom classRoom);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateClassroom(ClassRoom classRoom);

    @Delete
    void deleteOneRow(ClassRoom classRoom);

    @Query("SELECT * FROM classroom_table")
     Single<List<ClassRoom>>  getListClassroom();

    @Query("UPDATE classroom_table SET numberOfStudents = :count WHERE id = :id")
    void updateCount(int id, int count);
}
