package com.example.andre.aced;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
    private Context context;
    private Calendar c = Calendar.getInstance();

    private TextView course_time;
    private WeekdaysPicker weekdaysPicker;
    private Button save;

    public static int requestCode = 0;

    public static DatabaseHelper db;

    public static int user_selected_hour;
    public static int user_selected_minute;

    public static int user_selected_hour_later;
    public static int getUser_selected_minute_later;

    private List<Integer>selectedDays = new ArrayList<>();
    private List<Course>all_courses = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_input_field);

        findXML();
        db = new DatabaseHelper(this);
        all_courses.addAll(db.getAllCourses());


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
                    //startAlarm();

                }
            }
        });

        //selectedDays = weekdaysPicker.getSelectedDays();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Course course, int dayOfWeek){

        Calendar cal = Calendar.getInstance();

        AlarmManager alarmManager = (AlarmManager)getSystemService(context.ALARM_SERVICE);

        cal.set(Calendar.HOUR_OF_DAY, user_selected_hour);
        cal.set(Calendar.MINUTE, user_selected_minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        Calendar currentCal = Calendar.getInstance();

        /*if(cal.compareTo(currentCal) < 0) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }*/

        if(cal.getTimeInMillis() < System.currentTimeMillis()) {
            cal.add(Calendar.DAY_OF_YEAR, 7);
        }

        Intent intent = new Intent(this, AlertReceiver2.class);
        intent.putExtra("coursename", course.getCourseName());
        requestCode ++;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);


        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
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
        //all_courses.add(course1);

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
                //c.set(Calendar.DAY_OF_WEEK, course1.getMon() - 1 );
            }

            if(i == 3){
                course1.setTues(i);
                db.insertTues(course1);
                //c.set(Calendar.DAY_OF_WEEK, course1.getTues() - 1 );
            }

            if(i == 4){
                course1.setWed(i);
                db.insertWed(course1);
                //c.set(Calendar.DAY_OF_WEEK, course1.getWed() - 1 );
            }

            if(i == 5){
                course1.setThurs(i);
                db.insertThurs(course1);
               // c.set(Calendar.DAY_OF_WEEK, course1.getThurs() - 1 );
            }

            if(i == 6){
                course1.setFri(i);
                db.insertFri(course1);
                //c.set(Calendar.DAY_OF_WEEK, course1.getFri() - 1 );
            }

        }

        //startAlarm(c);
        //determineAlarm(course1);

        if(course1.getMon() == 2){

            //c.set(Calendar.DAY_OF_WEEK, course.getMon() - 1 );
            //requestCode++;
            startAlarm(course1, Calendar.MONDAY);
        }

        if(course1.getTues() == 3){

            //c.set(Calendar.DAY_OF_WEEK, course.getTues() - 1 );
            //requestCode++;
            startAlarm(course1, Calendar.TUESDAY);
        }

        if(course1.getWed() == 4){

            //c.set(Calendar.DAY_OF_WEEK, course.getWed() - 1 );
            //requestCode++;
            startAlarm(course1, Calendar.WEDNESDAY);
        }

        if(course1.getThurs() == 5){

            //c.set(Calendar.DAY_OF_WEEK, course.getThurs() - 1 );
            //requestCode++;

            startAlarm(course1, Calendar.THURSDAY);
        }

        if(course1.getFri() == 6){

            //c.set(Calendar.DAY_OF_WEEK, course.getFri() - 1 );
            //requestCode++;
            startAlarm(course1, Calendar.FRIDAY);
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

        //startAlarm(c);

    }

    @Override
    public void onSelectedTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {

        /*c = java.util.Calendar.getInstance();
        c.set(Calendar.HOUR, hourStart);
        c.set(Calendar.MINUTE, minuteStart);
        int ampm = c.get(java.util.Calendar.AM_PM);
        System.out.println("am" + ampm);*/


        user_selected_hour = hourStart;
        user_selected_minute = minuteStart;

        user_selected_hour_later = hourEnd;
        getUser_selected_minute_later = minuteEnd;

        //requestCode++;
        //startAlarm(c);


    }

   /* public void determineAlarm(Course course){

        if(course.getMon() == 2){

            //c.set(Calendar.DAY_OF_WEEK, course.getMon() - 1 );
            //requestCode++;
            startAlarm(course, c, Calendar.MONDAY);
        }

        if(course.getTues() == 3){

            //c.set(Calendar.DAY_OF_WEEK, course.getTues() - 1 );
            //requestCode++;
            startAlarm(course, c, Calendar.TUESDAY);
        }

        if(course.getWed() == 4){

            //c.set(Calendar.DAY_OF_WEEK, course.getWed() - 1 );
            //requestCode++;
            startAlarm(course, c, Calendar.WEDNESDAY);
        }

        if(course.getThurs() == 5){

            //c.set(Calendar.DAY_OF_WEEK, course.getThurs() - 1 );
            //requestCode++;

            startAlarm(course, c, Calendar.THURSDAY);
        }

        if(course.getFri() == 6){

            //c.set(Calendar.DAY_OF_WEEK, course.getFri() - 1 );
            //requestCode++;
            startAlarm(course,c, Calendar.FRIDAY);
        }

        }*/
    }

