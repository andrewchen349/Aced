package com.example.andre.aced;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import data.DatabaseHelper;
import model.Course;

public class WeeklyView extends AppCompatActivity {

    private TimetableView timetableView;
    private DatabaseHelper db;

    public List<Course>all_courses = new ArrayList<>();
    public ArrayList<Schedule>scheduleList = new ArrayList<>();
    private String json;
    private ImageView back;
    private TextView add;

    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;

    private Button save;
    private Button load;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_view);

        //Weektable
        timetableView = (TimetableView)findViewById(R.id.timetable);
        back = (ImageView)findViewById(R.id.weekly_view_back);
        add = (TextView)findViewById(R.id.add_weekly_view_event);
        db = new DatabaseHelper(this);
        save = (Button)findViewById(R.id.table_confirm);
        load = (Button)findViewById(R.id.table_load);
        all_courses.addAll(db.getAllCourses());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WeeklyView.this, Weekly_View_Update_Event.class);
                i.putExtra("mode",REQUEST_ADD);
                startActivityForResult(i,REQUEST_ADD);
            }
        });

        timetableView.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                Intent i = new Intent(WeeklyView.this, Weekly_View_Update_Event.class);
                i.putExtra("mode",REQUEST_EDIT);
                i.putExtra("idx", idx);
                i.putExtra("schedules", schedules);
                startActivityForResult(i,REQUEST_EDIT);

            }
        });

        timetableView.add(importclassschedule());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeeklyView.this, com.example.andre.aced.Calendar.class);
                WeeklyView.this.startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveByPreference(timetableView.createSaveData());
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSavedData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ADD:
                if(resultCode == Weekly_View_Update_Event.RESULT_OK_ADD){
                    ArrayList<Schedule> item = (ArrayList<Schedule>)data.getSerializableExtra("schedules");
                    timetableView.add(item);
                }
                break;
            case REQUEST_EDIT:
                /** Edit -> Submit */
                if(resultCode == Weekly_View_Update_Event.RESULT_OK_EDIT){
                    int idx = data.getIntExtra("idx",-1);
                    ArrayList<Schedule> item = (ArrayList<Schedule>)data.getSerializableExtra("schedules");
                    timetableView.edit(idx,item);

                }
                /** Edit -> Delete */
                else if(resultCode == Weekly_View_Update_Event.RESULT_OK_DELETE){
                    int idx = data.getIntExtra("idx",-1);
                    timetableView.remove(idx);
                }
                break;
        }
    }

    private void saveByPreference(String data){
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("timetable_demo",data);
        editor.commit();
        Toast.makeText(this,"saved!",Toast.LENGTH_SHORT).show();
    }

    /** get json data from SharedPreferences and then restore the timetable */
    private void loadSavedData(){
        timetableView.removeAll();
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
        String savedData = mPref.getString("timetable_demo","");
        if(savedData == null && savedData.equals("")) return;
        timetableView.load(savedData);
        Toast.makeText(this,"loaded!",Toast.LENGTH_SHORT).show();
    }


   private ArrayList importclassschedule(){
        if(all_courses.size() != 0){
            for (Course c : all_courses){
                Schedule schedule = new Schedule();
                schedule.setClassTitle(c.getCourseName());
                schedule.setClassPlace(c.getLocation());
                schedule.setProfessorName(c.getProffesorName());
                schedule.setStartTime(new Time(c.getHour(),c.getMinute()));
                schedule.setEndTime(new Time(c.getHourEnd(), c.getMinuteEnd()));

                if(c.getMon() != 0) {
                    schedule.setDay(c.getMon() - 2);
                    scheduleList.add(schedule);
                    schedule = new Schedule();
                    schedule.setClassTitle(c.getCourseName());
                    schedule.setClassPlace(c.getLocation());
                    schedule.setProfessorName(c.getProffesorName());
                    schedule.setStartTime(new Time(c.getHour(),c.getMinute()));
                    schedule.setEndTime(new Time(c.getHourEnd(), c.getMinuteEnd()));
                }

                if(c.getTues() != 0)
                {
                    schedule.setDay(c.getTues() - 2);
                    scheduleList.add(schedule);
                    schedule = new Schedule();
                    schedule.setClassTitle(c.getCourseName());
                    schedule.setClassPlace(c.getLocation());
                    schedule.setProfessorName(c.getProffesorName());
                    schedule.setStartTime(new Time(c.getHour(),c.getMinute()));
                    schedule.setEndTime(new Time(c.getHourEnd(), c.getMinuteEnd()));
                }

                if(c.getWed() != 0) {
                    schedule.setDay(c.getWed() - 2);
                    scheduleList.add(schedule);
                    schedule = new Schedule();
                    schedule.setClassTitle(c.getCourseName());
                    schedule.setClassPlace(c.getLocation());
                    schedule.setProfessorName(c.getProffesorName());
                    schedule.setStartTime(new Time(c.getHour(),c.getMinute()));
                    schedule.setEndTime(new Time(c.getHourEnd(), c.getMinuteEnd()));
                }

                if(c.getThurs() != 0) {
                    schedule.setDay(c.getThurs() - 2);
                    scheduleList.add(schedule);
                    schedule = new Schedule();
                    schedule.setClassTitle(c.getCourseName());
                    schedule.setClassPlace(c.getLocation());
                    schedule.setProfessorName(c.getProffesorName());
                    schedule.setStartTime(new Time(c.getHour(),c.getMinute()));
                    schedule.setEndTime(new Time(c.getHourEnd(), c.getMinuteEnd()));
                }

                if(c.getFri() != 0) {
                    System.out.println("fri ");
                    //scheduleList.add(schedule);
                    schedule.setDay(c.getFri() - 2);
                    scheduleList.add(schedule);
                    schedule = new Schedule();
                    schedule.setClassTitle(c.getCourseName());
                    schedule.setClassPlace(c.getLocation());
                    schedule.setProfessorName(c.getProffesorName());
                    schedule.setStartTime(new Time(c.getHour(),c.getMinute()));
                    schedule.setEndTime(new Time(c.getHourEnd(), c.getMinuteEnd()));
                }
                //scheduleList.add(schedule);
            }
        }

        //saveByPreference(timetableView.createSaveData());
        return scheduleList;
    }
}
