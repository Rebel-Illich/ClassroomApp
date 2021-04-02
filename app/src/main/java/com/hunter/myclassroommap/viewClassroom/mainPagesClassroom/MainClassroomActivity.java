package com.hunter.myclassroommap.viewClassroom.mainPagesClassroom;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.hunter.myclassroommap.R;

public class MainClassroomActivity extends AppCompatActivity{


    private FragmentsNavigator fragmentsNavigator;
    MenuItem searchItem;
    MenuItem mainScreenItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controller_activity);

        fragmentsNavigator = new FragmentsNavigator(getSupportFragmentManager());

        fragmentsNavigator.mainClass();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);

        searchItem = menu.findItem(R.id.search_by);
        mainScreenItem = menu.findItem(R.id.search_menu);
        mainScreenItem.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_by:
                fragmentsNavigator.showSearchFragment();
                searchItem.setVisible(false);
                mainScreenItem.setVisible(true);
                return true;
            case R.id.search_menu:
                searchItem.setVisible(true);
                mainScreenItem.setVisible(false);
                fragmentsNavigator.mainClass();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 1) return;
        super.onBackPressed();
        searchItem.setVisible(true);
        mainScreenItem.setVisible(false);
    }
}
