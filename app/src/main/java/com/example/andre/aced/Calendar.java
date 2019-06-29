package com.example.andre.aced;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import Util.bottomNavBarHelper;

public class Calendar extends AppCompatActivity {

    //Constants and Fields
   // private TextView mTextMessage;
    //private FirebaseAuth mAuth1;
    private ImageView moreOptions;
    private static final int ACTIVITY_NUM  = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setUpBottomNavbar();

        moreOptions = (ImageView)findViewById(R.id.moreoption);
        //mAuth1 = FirebaseAuth.getInstance();

        moreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Calendar.this, Option.class);
                Calendar.this.startActivity(intent1);
            }
        });
    }


    //Bottom Navigation Bar Setup
   private void setUpBottomNavbar(){
       BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomnavbar);
       bottomNavBarHelper.enbaleNav(Calendar.this, bottomNavigationView);
       Menu menu = bottomNavigationView.getMenu();
       MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
       menuItem.setChecked(true);
       }
}
