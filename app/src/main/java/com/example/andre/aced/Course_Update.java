package com.example.andre.aced;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dpro.widgets.WeekdaysPicker;

import java.util.ArrayList;
import java.util.List;

import data.DatabaseHelper;
import model.Course;

public class Course_Update extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

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
        setContentView(R.layout.course_update);

        findXML();

        all_courses_update.addAll(Classes_Planner.db.getAllCourses());
        Intent i = getIntent();
        pos = i.getIntExtra("position", 0);

        db_update = new DatabaseHelper(this);
        //Classes_Planner.all_courses.remove(pos);

        course_name.setText(all_courses_update.get(pos).getCourseName());
        course_profesor.setText(all_courses_update.get(pos).getProffesorName());
        course_location.setText(all_courses_update.get(pos).getLocation());
        course_profesor_email.setText(all_courses_update.get(pos).getEmail());
        user_selected_hour = all_courses_update.get(pos).getHour();
        user_selected_minute = all_courses_update.get(pos).getMinute();


        String s = all_courses_update.get(pos).getHour() + ":" + all_courses_update.get(pos).getMinute();
        course_time.setText(s);

        course_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCourse(course_name.getText().toString());
                Intent intent = new Intent(Course_Update.this, Classes_Planner.class);
                Course_Update.this.startActivity(intent);
            }
        });


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

    private void updateCourse(String course){
        //TODO
        //long id = db_update.insertCourseName(course);
        Course course1 = all_courses_update.get(pos);
        course1.setCourseName(course);
        db_update.updateCourse(course1);

        course1.setLocation(course_location.getText().toString());
        db_update.insertCourseLocation(course1);

        course1.setProfessorName(course_profesor.getText().toString());
        db_update.insertTeacherName(course1);

        course1.setEmail(course_profesor_email.getText().toString());
        db_update.insertTeacherEmail(course1);

        course1.setHour(user_selected_hour);
        course1.setMinute(user_selected_minute);

        db_update.insertCourseHour(course1);
        db_update.insertCourseMin(course1);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(java.util.Calendar.MINUTE, minute);
        c.set(java.util.Calendar.SECOND, 0);
        int ampm = c.get(java.util.Calendar.AM_PM);

        //Adds a Day for Midnight
        if (c.before(java.util.Calendar.getInstance())) {
            c.add(java.util.Calendar.DATE, 1);
        }

        user_selected_hour = hourOfDay;
        user_selected_minute = minute;

        if (hourOfDay > 12) {
            hourOfDay = hourOfDay - 12;
        }

        if (ampm == 0) {
            String time = hourOfDay + ":" + minute + " " + "AM";
            course_time.setText(time);
        } else if (ampm == 1) {
            String time = hourOfDay + ":" + minute + " " + "PM";
            course_time.setText(time);
        }

    }
}
