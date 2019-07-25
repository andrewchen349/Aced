package com.example.andre.aced;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import Util.bottomNavBarHelper;
import data.DatabaseHelper;
import model.Course;
import model.Events;
import view.ChecklistAdapter;
import view.Courses_Adapter;

import static com.example.andre.aced.Checklist.checklistAdapter;

public class Classes_Planner extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 3;
    private Button addCourse;
    public List<Course> all_courses = new ArrayList<>();
    private RecyclerView recyclerView;
    public static Courses_Adapter courses_adapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planner);

        recyclerView = (RecyclerView)findViewById(R.id.courserecycler);

        addCourse = (Button)findViewById(R.id.addCourse);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Classes_Planner.this, Course_Input.class);
                Classes_Planner.this.startActivity(intent);
            }
        });

        setUpBottomNavbar();
        /*if(Course_Input.db != null) {
            all_courses.addAll(Course_Input.db.getAllCourses());
        }*/

        db =  new DatabaseHelper(this);
        all_courses.addAll(db.getAllCourses());


        courses_adapter = new Courses_Adapter(this, all_courses);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager1);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView1.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(courses_adapter);
        courses_adapter.notifyDataSetChanged();


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
