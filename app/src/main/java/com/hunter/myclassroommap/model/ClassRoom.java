package com.hunter.myclassroommap.model;

public class ClassRoom {

    private long id;
    private String classroomName;
    private long classroomRoomNumber;
    private long classroomFloor;
    private long numberOfStudents;


    public ClassRoom(String classroomName, long classroomRoomNumber, long classroomFloor, long numberOfStudents) {
        this.classroomName = classroomName;
        this.classroomRoomNumber = classroomRoomNumber;
        this.classroomFloor = classroomFloor;
        this.numberOfStudents = numberOfStudents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public long getClassroomRoomNumber() {
        return classroomRoomNumber;
    }

    public void setClassroomRoomNumber(long classroomRoomNumber) {
        this.classroomRoomNumber = classroomRoomNumber;
    }

    public long getClassroomFloor() {
        return classroomFloor;
    }

    public void setClassroomFloor(long classroomFloor) {
        this.classroomFloor = classroomFloor;
    }

    public long getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(long numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
