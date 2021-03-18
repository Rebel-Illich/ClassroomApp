package com.hunter.myclassroommap;

public class ClassRoom {

    private long id;
    private String name;
    private int roomNumber;
    private int floor;
    private int numberOfStudents;

    public ClassRoom(long id, String name, int roomNumber, int floor, int numberOfStudents) {
        this.id = id;
        this.name = name;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.numberOfStudents = numberOfStudents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
