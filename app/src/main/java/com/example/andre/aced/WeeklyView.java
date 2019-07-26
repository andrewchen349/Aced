package com.example.andre.aced;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import java.util.ArrayList;
import java.util.List;

import data.DatabaseHelper;
import model.Course;

public class WeeklyView extends AppCompatActivity {

    private TimetableView timetableView;
    private DatabaseHelper db;

    private List<Course>all_courses = new ArrayList<>();
    private List<Schedule>scheduleList = new ArrayList<>();
    private String json;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_view);

        //Weektable
        timetableView = (TimetableView)findViewById(R.id.timetable);
        db = new DatabaseHelper(this);

        all_courses.addAll(db.getAllCourses());

        timetableView.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                //TODO: Show Action Dialog, Add a schedule, add schedule to the list
            }
        });

        /*if(!json.isEmpty()){
            timetableView.load(json);
        }*/
        importclassschedule();
    }

    private void importclassschedule(){

        if(all_courses.size() != 0){

            //Loop through each object

            for (Course c : all_courses){
                Schedule schedule = new Schedule();
                schedule.setClassTitle(c.getCourseName());
                schedule.setClassPlace(c.getLocation());
                schedule.setProfessorName(c.getProffesorName());
                schedule.setStartTime(new Time(c.getHour(),c.getMinute()));
                schedule.setEndTime(new Time(c.getHourEnd(), c.getMinuteEnd()));
                schedule.setDay(c.getMon());
                schedule.setDay(c.getTues());
                schedule.setDay(c.getWed());
                schedule.setDay(c.getThurs());
                schedule.setDay(c.getFri());
                scheduleList.add(schedule);
            }
        }

        //json =  timetableView.createSaveData();
    }
}
