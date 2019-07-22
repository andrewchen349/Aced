package com.example.andre.aced;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import Util.bottomNavBarHelper;

public class Classes_Planner extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_planner_layout);

        setUpBottomNavbar();
    }


    //Bottom Navigation Bar Setup
    private void setUpBottomNavbar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavbar);
        bottomNavBarHelper.enbaleNav(Classes_Planner.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
