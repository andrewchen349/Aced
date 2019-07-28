package com.example.andre.aced;

import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dpro.widgets.WeekdaysPicker;
import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import data.DatabaseHelper;
import model.Course;

public class Course_Input extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, RangeTimePickerDialog.ISelectedTime {

    private EditText course_name;
    private EditText course_profesor;
    private EditText course_location;
    private EditText course_profesor_email;

    private TextView course_time;
    private WeekdaysPicker weekdaysPicker;
    private Button save;

    public static DatabaseHelper db;

    public static int user_selected_hour;
    public static int user_selected_minute;

    public static int user_selected_hour_later;
    public static int getUser_selected_minute_later;

    private List<Integer>selectedDays = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_input_field);

        findXML();
        db = new DatabaseHelper(this);


        course_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DialogFragment timePicker = new TimePickerFragment();
                //timePicker.show(getSupportFragmentManager(), "time picker");

                RangeTimePickerDialog dialog = new RangeTimePickerDialog();
                dialog.newInstance();
                dialog.setRadiusDialog(20); // Set radius of dialog (default is 50)
                dialog.setColorTabSelected(R.color.white);
                //dialog.setColorBackgroundHeader(R.color.colorPrimary);
                dialog.setIs24HourView(false); // Indicates if the format should be 24 hours
                dialog.setColorBackgroundHeader(R.color.colorPrimary); // Set Color of Background header dialog
                dialog.setColorTextButton(R.color.colorPrimaryDark); // Set Text color of button
                FragmentManager fragmentManager = getFragmentManager();
                dialog.show(fragmentManager, "");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(course_name.getText().length() != 0) {
                    createCourse(course_name.getText().toString());
                    Intent intent = new Intent(Course_Input.this, Classes_Planner.class);
                    Course_Input.this.startActivity(intent);
                    Toast.makeText(Course_Input.this, "Course Created!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //selectedDays = weekdaysPicker.getSelectedDays();

    }




    private void findXML(){

        course_name = (EditText)findViewById(R.id.classname);
        course_profesor = (EditText)findViewById(R.id.name);
        course_location = (EditText)findViewById(R.id.location);
        course_profesor_email = (EditText)findViewById(R.id.email);

        course_time = (TextView)findViewById(R.id.timenumbers);
        weekdaysPicker = (WeekdaysPicker)findViewById(R.id.weekdays);
        save = (Button) findViewById(R.id.confirm);
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

        course1.setHourEnd(user_selected_hour_later);
        course1.setMinuteEnd(getUser_selected_minute_later);

        db.insertCourseHour(course1);
        db.insertCourseMin(course1);

        db.insertCourseEndHour(course1);
        db.insertCourseEndMin(course1);

        selectedDays = weekdaysPicker.getSelectedDays();



        for(int i : selectedDays){

            if(i == 2){
                course1.setMon(i);
                db.insertMon(course1);
                System.out.println("mhere");
            }

            if(i == 3){
                course1.setTues(i);
                db.insertTues(course1);
                System.out.println("there");
            }

            if(i == 4){
                course1.setWed(i);
                db.insertWed(course1);
                System.out.println("where");
            }

            if(i == 5){
                course1.setThurs(i);
                db.insertThurs(course1);
                System.out.println("there");
            }

            if(i == 6){
                course1.setFri(i);
                db.insertFri(course1);
                System.out.println("fhere");
            }

        }


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

    @Override
    public void onSelectedTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {

        Calendar c = java.util.Calendar.getInstance();
        c.set(Calendar.HOUR, hourStart);
        c.set(Calendar.MINUTE, minuteStart);
        int ampm = c.get(java.util.Calendar.AM_PM);
        System.out.println("am" + ampm);


        user_selected_hour = hourStart;
        user_selected_minute = minuteStart;

        user_selected_hour_later = hourEnd;
        getUser_selected_minute_later = minuteEnd;


    }
}
