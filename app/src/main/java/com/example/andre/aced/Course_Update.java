package com.example.andre.aced;

import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import java.util.LinkedHashMap;
import java.util.List;

import data.DatabaseHelper;
import model.Course;

public class Course_Update extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, RangeTimePickerDialog.ISelectedTime{

    private EditText course_name;
    private EditText course_profesor;
    private EditText course_location;
    private EditText course_profesor_email;

    private TextView course_time;
    private WeekdaysPicker weekdaysPicker;
    private Button save;

    public List<Course> all_courses_update = new ArrayList<>();
    private int pos;
    private DatabaseHelper db_update;

    private int user_selected_hour;
    private  int user_selected_minute;

    public static int user_selected_hour_later;
    public static int getUser_selected_minute_later;

    private List<Integer>selectedDays = new ArrayList<>();

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

        user_selected_hour_later = all_courses_update.get(pos).getHourEnd();
        getUser_selected_minute_later = all_courses_update.get(pos).getMinuteEnd();



        String s = all_courses_update.get(pos).getHour() + ":" + all_courses_update.get(pos).getMinute() + " to " +
                all_courses_update.get(pos).getHourEnd() + ":" + all_courses_update.get(pos).getMinuteEnd();
        course_time.setText(s);

        course_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DialogFragment timePicker = new TimePickerFragment();
                //timePicker.show(getSupportFragmentManager(), "time picker");

                RangeTimePickerDialog dialog = new RangeTimePickerDialog();
                dialog.newInstance();
                dialog.setRadiusDialog(20); // Set radius of dialog (default is 50)
                dialog.setIs24HourView(false); // Indicates if the format should be 24 hours
                dialog.setColorTabSelected(R.color.white);
                dialog.setColorBackgroundHeader(R.color.colorPrimary); // Set Color of Background header dialog
                dialog.setColorTextButton(R.color.colorPrimaryDark); // Set Text color of button
                FragmentManager fragmentManager = getFragmentManager();
                dialog.show(fragmentManager, "");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCourse(course_name.getText().toString());
                Intent intent = new Intent(Course_Update.this, Classes_Planner.class);
                Course_Update.this.startActivity(intent);
                Toast.makeText(Course_Update.this, "Course Updated!", Toast.LENGTH_SHORT).show();
            }
        });

        //selectedDays = weekdaysPicker.getSelectedDays();




    }

    private void findXML(){
        course_name = (EditText)findViewById(R.id.classname_update);
        course_profesor = (EditText)findViewById(R.id.name_update);
        course_location = (EditText)findViewById(R.id.location_update);
        course_profesor_email = (EditText)findViewById(R.id.email_update);

        course_time = (TextView)findViewById(R.id.timenumbers_update);
        weekdaysPicker = (WeekdaysPicker)findViewById(R.id.weekdays_update);
        save = (Button)findViewById(R.id.confirm);
    }

    private void updateCourse(String course){
        //TODO
        //long id = db_update.insertCourseName(course);
        Course course1 = all_courses_update.get(pos);
        course1.setCourseName(course);
        db_update.updateCourse(course1);

        course1.setMon(0);
        db_update.insertMon(course1);
        course1.setTues(0);
        db_update.insertTues(course1);
        course1.setWed(0);
        db_update.insertWed(course1);
        course1.setThurs(0);
        db_update.insertThurs(course1);
        course1.setFri(0);
        db_update.insertFri(course1);

        course1.setLocation(course_location.getText().toString());
        db_update.insertCourseLocation(course1);

        course1.setProfessorName(course_profesor.getText().toString());
        db_update.insertTeacherName(course1);

        course1.setEmail(course_profesor_email.getText().toString());
        db_update.insertTeacherEmail(course1);

        course1.setHour(user_selected_hour);
        course1.setMinute(user_selected_minute);

        course1.setHourEnd(user_selected_hour_later);
        course1.setMinuteEnd(getUser_selected_minute_later);

        db_update.insertCourseHour(course1);
        db_update.insertCourseMin(course1);

        db_update.insertCourseEndHour(course1);
        db_update.insertCourseEndMin(course1);

        selectedDays = weekdaysPicker.getSelectedDays();

        for(int i : selectedDays){

            System.out.println(i);

            if(i == 2){
                System.out.println("2h");
                course1.setMon(i);
                db_update.insertMon(course1);
            }

            /*if(i != 2){
                System.out.println("2n");
                course1.setMon(0);
                db_update.insertMon(course1);
            }*/

            if(i == 3){
                System.out.println("4h");
                course1.setTues(i);
                db_update.insertTues(course1);
            }

            /*if(i != 3){
                System.out.println("3n");
                course1.setTues(0);
                db_update.insertTues(course1);
            }*/

            if(i == 4){
                System.out.println("4h");
                course1.setWed(i);
                db_update.insertWed(course1);
            }

            /*if(i != 4){
                System.out.println("4n");
                course1.setWed(0);
                db_update.insertWed(course1);
            }*/

            if(i == 5){
                System.out.println("5h");
                course1.setThurs(i);
                db_update.insertThurs(course1);
            }

            /*if(i != 5){
                System.out.println("5n");
                course1.setThurs(0);
                db_update.insertThurs(course1);
            }*/

            if(i == 6){
                System.out.println("6h");
                course1.setFri(i);
                db_update.insertFri(course1);
            }

            /*if(i != 6){
                System.out.println("6n");
                course1.setFri(0);
                db_update.insertFri(course1);
            }*/

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

        user_selected_hour = hourStart;
        user_selected_minute = minuteStart;

        user_selected_hour_later = hourEnd;
        getUser_selected_minute_later = minuteEnd;

    }


}
