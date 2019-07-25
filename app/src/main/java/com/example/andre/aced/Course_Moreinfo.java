package com.example.andre.aced;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dpro.widgets.WeekdaysPicker;

import java.util.ArrayList;
import java.util.List;

import data.DatabaseHelper;
import model.Course;

public class Course_Moreinfo extends AppCompatActivity {

    private EditText course_name;
    private EditText course_profesor;
    private EditText course_location;
    private EditText course_profesor_email;

    private TextView course_time;
    private WeekdaysPicker weekdaysPicker;
    private ImageView save;

    public List<Course> all_courses_update = new ArrayList<>();
    private int pos;
    private DatabaseHelper db_update;

    private int user_selected_hour;
    private  int user_selected_minute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_moreinfo);

        findXML();

        all_courses_update.addAll(Classes_Planner.db.getAllCourses());
        Intent i = getIntent();
        pos = i.getIntExtra("position", 0);

        db_update = new DatabaseHelper(this);

        course_name.setText(all_courses_update.get(pos).getCourseName());
        course_profesor.setText(all_courses_update.get(pos).getProffesorName());
        course_location.setText(all_courses_update.get(pos).getLocation());
        course_profesor_email.setText(all_courses_update.get(pos).getEmail());
        user_selected_hour = all_courses_update.get(pos).getHour();
        user_selected_minute = all_courses_update.get(pos).getMinute();


        String s = all_courses_update.get(pos).getHour() + ":" + all_courses_update.get(pos).getMinute();
        course_time.setText(s);
    }


    private void findXML(){
        course_name = (EditText)findViewById(R.id.classname_update);
        course_profesor = (EditText)findViewById(R.id.name_update);
        course_location = (EditText)findViewById(R.id.location_update);
        course_profesor_email = (EditText)findViewById(R.id.email_update);

        course_time = (TextView)findViewById(R.id.timenumbers_update);
        weekdaysPicker = (WeekdaysPicker)findViewById(R.id.weekdays_update);
        save = (ImageView)findViewById(R.id.confirm_update);
    }
}
