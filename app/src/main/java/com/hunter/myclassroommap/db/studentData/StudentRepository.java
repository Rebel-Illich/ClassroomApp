package com.hunter.myclassroommap.db.studentData;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.hunter.myclassroommap.db.classroomData.ClassroomDb;
import com.hunter.myclassroommap.db.classroomData.ClassroomRepository;
import com.hunter.myclassroommap.model.ClassRoom;
import com.hunter.myclassroommap.model.ClassRoomDao;
import com.hunter.myclassroommap.model.Student;
import com.hunter.myclassroommap.model.StudentDao;
import com.hunter.myclassroommap.viewStudent.addStudents.AddStudentContract;
import com.hunter.myclassroommap.viewStudent.mainPageStudent.StudentAndClassContract;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class StudentRepository implements StudentAndClassContract.Repository, AddStudentContract.Repository  {

    private ClassroomDb db;
    private StudentDao studentDao;
    private ClassRoomDao classRoomDao;
    private static StudentRepository sInstance;

    public StudentRepository(ClassroomDb database) {
        db = database;
        studentDao = db.studentDao();
        classRoomDao = db.classRoomDao();
    }

    public static StudentRepository getInstance(final ClassroomDb database) {
        if (sInstance == null) {
            synchronized (StudentRepository.class) {
                if (sInstance == null) {
                    sInstance = new StudentRepository(database);
                }
            }
        }
        return sInstance;
    }

    @Override
    public LiveData<List<Student>> getStudentsFromCurrentClass(int classroomId) {
        return studentDao.getStudentsCurrentClass(classroomId);
    }

    public Single<Integer> getNumFiles(int classroomId) {
        return studentDao.getCount(classroomId);
    }

    @Override
    public Single<Boolean> updateClassroomStudentsCount(int classId, Integer count) {
        return null;
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

    public Single<Boolean> updateClassRoomStudentCount(int classId, int countOfStudents) {
        return Single.create(emitter -> {
            classRoomDao.updateCount(classId, countOfStudents);
            emitter.onSuccess(true);
        });
    }
}
