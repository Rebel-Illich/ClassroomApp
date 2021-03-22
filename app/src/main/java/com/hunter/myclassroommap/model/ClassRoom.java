package com.hunter.myclassroommap.model;

public class ClassRoom {

    private long id;
    private String classroomName;
    private int classroomRoomNumber;
    private int classroomFloor;
    private int numberOfStudents;

    public ClassRoom(long id, String classroomName, int classroomRoomNumber, int classroomFloor, int numberOfStudents) {
        this.id = id;
        this.classroomName = classroomName;
        this.classroomRoomNumber = classroomRoomNumber;
        this.classroomFloor = classroomFloor;
        this.numberOfStudents = numberOfStudents;
    }

    public ClassRoom(String classroomName, int classroomRoomNumber, int classroomFloor, int numberOfStudents) {
        this.classroomName = classroomName;
        this.classroomRoomNumber = classroomRoomNumber;
        this.classroomFloor = classroomFloor;
        this.numberOfStudents = numberOfStudents;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public int getClassroomRoomNumber() {
        return classroomRoomNumber;
    }

    public void setClassroomRoomNumber(int classroomRoomNumber) {
        this.classroomRoomNumber = classroomRoomNumber;
    }

    public int getClassroomFloor() {
        return classroomFloor;
    }

    public void setClassroomFloor(int classroomFloor) {
        this.classroomFloor = classroomFloor;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

  }
