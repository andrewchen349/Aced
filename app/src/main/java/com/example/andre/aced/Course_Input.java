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

import data.DatabaseHelper;
import model.Course;

public class Course_Input extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private EditText course_name;
    private EditText course_profesor;
    private EditText course_location;
    private EditText course_profesor_email;

    private TextView course_time;
    private WeekdaysPicker weekdaysPicker;
    private ImageView save;

    public static DatabaseHelper db;

    public static int user_selected_hour;
    public static int user_selected_minute;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_input_field);

        findXML();
        db = new DatabaseHelper(this);


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
                if(course_name.getText().length() != 0) {
                    createCourse(course_name.getText().toString());
                    Intent intent = new Intent(Course_Input.this, Classes_Planner.class);
                    Course_Input.this.startActivity(intent);
                }
            }
        });



    }


    private void findXML(){

        course_name = (EditText)findViewById(R.id.classname);
        course_profesor = (EditText)findViewById(R.id.name);
        course_location = (EditText)findViewById(R.id.location);
        course_profesor_email = (EditText)findViewById(R.id.email);

        course_time = (TextView)findViewById(R.id.timenumbers);
        weekdaysPicker = (WeekdaysPicker)findViewById(R.id.weekdays);
        save = (ImageView)findViewById(R.id.confirm);
    }

    private void createCourse(String course){
        //TODO

        long id = db.insertCourseName(course);
        Course course1 = db.getCourse(id);

        course1.setLocation(course_location.getText().toString());
        db.insertCourseLocation(course1);

        course1.setProfessorName(course_profesor.getText().toString());
        db.insertTeacherName(course1);

        course1.setEmail(course_profesor_email.getText().toString());
        db.insertTeacherEmail(course1);

        course1.setHour(user_selected_hour);
        course1.setMinute(user_selected_minute);

        db.insertCourseHour(course1);
        db.insertCourseMin(course1);
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
