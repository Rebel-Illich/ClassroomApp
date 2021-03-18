package com.hunter.myclassroommap.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.hunter.myclassroommap.ClassRoom;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter  {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_ROOMNUMBER, DatabaseHelper.COLUMN_FLOOR,
                DatabaseHelper.COLUMN_NUMBEROFSTUDENTS };
        return  database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
    }

    public List<ClassRoom> getUsers(){
        ArrayList<ClassRoom> users = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            int roomNumber = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROOMNUMBER));
            int floor = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROOMNUMBER));
            int numberOfStudents = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROOMNUMBER));
            users.add(new ClassRoom(id, name, roomNumber, floor, numberOfStudents));
        }
        cursor.close();
        return  users;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE_NAME);
    }

    public ClassRoom getUser(long id){
        ClassRoom classRoom = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            int roomNumber = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ROOMNUMBER));
            int floor = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_FLOOR));
            int numberOfStudents = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_NUMBEROFSTUDENTS));
            classRoom = new ClassRoom(id, name, roomNumber, floor, numberOfStudents);
        }
        cursor.close();
        return  classRoom;
    }

    public long insertData(ClassRoom classRoom){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, classRoom.getName());
        cv.put(DatabaseHelper.COLUMN_ROOMNUMBER, classRoom.getRoomNumber());
        cv.put(DatabaseHelper.COLUMN_FLOOR, classRoom.getFloor());
        cv.put(DatabaseHelper.COLUMN_NUMBEROFSTUDENTS, classRoom.getNumberOfStudents());

        return  database.insert(DatabaseHelper.TABLE_NAME, null, cv);
    }

    public long delete(long userId){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(userId)};
        return database.delete(DatabaseHelper.TABLE_NAME, whereClause, whereArgs);
    }

    public long updateData(ClassRoom classRoom){

        String whereClause = DatabaseHelper.COLUMN_ID + "=" + String.valueOf(classRoom.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, classRoom.getName());
        cv.put(DatabaseHelper.COLUMN_ROOMNUMBER, classRoom.getRoomNumber());
        cv.put(DatabaseHelper.COLUMN_FLOOR, classRoom.getFloor());
        cv.put(DatabaseHelper.COLUMN_NUMBEROFSTUDENTS, classRoom.getNumberOfStudents());

        return database.update(DatabaseHelper.TABLE_NAME, cv, whereClause, null);
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = db.delete(dbHelper.TABLE_NAME, "ID=?", new String[]{row_id});
    }

    public void deleteAllData(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + dbHelper.TABLE_NAME);
    }
}
