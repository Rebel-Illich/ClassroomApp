package com.hunter.myclassroommap.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hunter.myclassroommap.R;
import com.hunter.myclassroommap.db.DatabaseHelper;

public class ControllerActivity  extends AppCompatActivity{

    private AddFragment addFragment;
    private MainListFragment mainListFragment;
    private UpdateFragment updateFragment;
    private WorksWithAdd worksWithAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controller_activity);

        worksWithAdd= new WorksWithAdd();

        mainListFragment = MainListFragment.newInstance(worksWithAdd);

        worksWithAdd.mainClass();
    }

    class WorksWithAdd{
        public WorksWithAdd() {
            super();
        }

        void addClass(){
            if (addFragment == null) {
                addFragment = AddFragment.newInstance(worksWithAdd);
            }
               getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, addFragment)
                   .addToBackStack(null)
                   .commit();
        }

        void updateClass(String id, String name, String floor, String room, String countOfStudent){
            if (updateFragment == null) {
                updateFragment = UpdateFragment.newInstance(worksWithAdd);
            }
            updateFragment.setData(id, name, floor, room, countOfStudent);
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, updateFragment)
                .addToBackStack(null)
                .commit();
        }

        void mainClass() {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, mainListFragment)
                .addToBackStack(null)
                .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper myDB = new DatabaseHelper(ControllerActivity.this);
                myDB.deleteAllData();

                //Refresh Fragment
                mainListFragment = MainListFragment.newInstance(worksWithAdd);

                worksWithAdd.mainClass();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }
}
