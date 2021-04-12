package com.hunter.myclassroommap.db.studentData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hunter.myclassroommap.db.classroomData.ClassroomDatabase;
import com.hunter.myclassroommap.db.classroomData.ClassroomDb;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.ClassRoomDao;
import com.hunter.myclassroommap.model.Student;
import com.hunter.myclassroommap.model.StudentDao;
import com.hunter.myclassroommap.viewStudent.addStudents.AddStudentContract;
import com.hunter.myclassroommap.viewStudent.mainPageStudent.StudentAndClassContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class StudentRepository implements StudentAndClassContract.Repository, AddStudentContract.Repository  {
    private ClassroomDatabase dataBaseStudent;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<Student> studentModelArrayList;
    private ClassroomDb db;
    private StudentDao studentDao;
    private ClassRoomDao classRoomDao;

    public StudentRepository(Context context) {
        dataBaseStudent = new ClassroomDatabase(context.getApplicationContext());
        db = ClassroomDb.getDatabase(context);
        studentDao = db.studentDao();
        classRoomDao = db.classRoomDao();
    }

    @Override
    public Single<List<Student>> getStudentsFromCurrentClass(int classroomId) {
        return studentDao.getStudentsCurrentClass(classroomId);
    }

    @Override
    public Single<Student> addStudent(Student studentModel) {
        return Single.create(emitter -> {
            int id = (int)studentDao.insertStudent(studentModel);
            studentModel.setStudentId(id);
            emitter.onSuccess(studentModel);
        });
    }

    public Completable deleteOneRow(Student studentM) {
        return Completable.create(emitter -> {
            studentDao.deleteOneRow(studentM);
            emitter.onComplete();
        });
    }

    public  Single<Student> updateData(Student studentM) {
        return Single.create(emitter -> {
            studentDao.updateStudents(studentM);
            emitter.onSuccess(studentM);
        });
    }

    public Single<List<Student>> getAll() {
        return studentDao.getAllStudents();
    }

    public Single<List<ClassRoom>> getAllClassRoom() {
        return classRoomDao.getListClassroom();
    }
}