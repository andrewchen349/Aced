package com.example.andre.aced;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import Util.bottomNavBarHelper;

public class Notes extends AppCompatActivity {

    private  static final int ACTIVITY_NUM = 2;
    private ImageView moreOptions;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        setUpBottomNavbar();

        //Find Corresponding XML Components
        moreOptions = (ImageView)findViewById(R.id.moreoption);
        moreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Notes.this, Option.class);
                Notes.this.startActivity(intent1);
            }
        });


    }

    //Bottom Navigation Bar Setup
    private void setUpBottomNavbar(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomnavbar);
        bottomNavBarHelper.enbaleNav(Notes.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
