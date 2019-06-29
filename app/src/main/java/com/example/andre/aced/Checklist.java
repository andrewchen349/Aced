package com.example.andre.aced;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import Util.bottomNavBarHelper;

public class Checklist extends AppCompatActivity {

    private static final int ACTIVITY_NUM  = 1;
    private ImageView moreOptions;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        setUpBottomNavbar();

        //Field Corresponding XML Components
        moreOptions = (ImageView)findViewById(R.id.moreoption);
        moreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Checklist.this, Option.class);
                Checklist.this.startActivity(intent1);
            }
        });
    }


    private void setUpBottomNavbar(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomnavbar);
        bottomNavBarHelper.enbaleNav(Checklist.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}

