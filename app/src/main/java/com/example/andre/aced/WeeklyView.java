package com.example.andre.aced;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
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

    public TimetableView timetableView;
    private DatabaseHelper db;

    public List<Course>all_courses = new ArrayList<>();
    public ArrayList<Schedule>scheduleList = new ArrayList<>();
    private String json;
    private ImageView back;
    private TextView add;

    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;
    public static int requestCode = 2;

    private Button save;
    private Button load;

    private Context context = this;

    private NotificationManagerCompat notificationManagerCompat;

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

        loadSavedData();

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

        //loadSavedData();
        timetableView.add(importclassschedule());
        //saveByPreference(timetableView.createSaveData());
        //loadSavedData();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeeklyView.this, com.example.andre.aced.Calendar.class);
                WeeklyView.this.startActivity(intent);
                saveByPreference(timetableView.createSaveData());
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

        notificationManagerCompat = NotificationManagerCompat.from(this);
        //startAlarm();
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

    private void startAlarm(){

        AlarmManager alarmManager = (AlarmManager)getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver2.class);

        for(Course c : all_courses){

            //Calendar cal = Calendar.getInstance();
            if(c.getMon() == 2){
                Calendar cal = Calendar.getInstance();
                if(cal.get(Calendar.AM_PM) == 0){
                    cal.set(Calendar.AM_PM, 1);
                }

                else{
                    cal.set(Calendar.AM_PM, 0);
                }

                cal.set(Calendar.HOUR_OF_DAY, c.getHour());
                cal.set(Calendar.MINUTE, c.getMinute() );
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.DAY_OF_WEEK, c.getMon() - 1);
                intent.putExtra("coursename", c.getCourseName());
                requestCode ++;
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);
                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                //cal.clear();
            }

            if(c.getTues() == 3){
                Calendar cal1 = Calendar.getInstance();
                if(cal1.get(Calendar.AM_PM) == 0){
                    cal1.set(Calendar.AM_PM, 1);
                }

                else{
                    cal1.set(Calendar.AM_PM, 0);
                }
                cal1.set(Calendar.HOUR_OF_DAY, c.getHour());
                cal1.set(Calendar.MINUTE, c.getMinute() );
                cal1.set(Calendar.DAY_OF_WEEK, c.getTues() - 1);
                intent.putExtra("coursename", c.getCourseName());
                requestCode ++;
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), pendingIntent);
                //cal1.clear();

            }

            if(c.getWed() == 4){
                Calendar cal2 = Calendar.getInstance();
                if(cal2.get(Calendar.AM_PM) == 0){
                    cal2.set(Calendar.AM_PM, 1);
                }

                else{
                    cal2.set(Calendar.AM_PM, 0);
                }
                cal2.set(Calendar.HOUR_OF_DAY, c.getHour());
                cal2.set(Calendar.MINUTE, c.getMinute() - 10);
                cal2.set(Calendar.DAY_OF_WEEK, c.getWed() - 1);
                intent.putExtra("coursename", c.getCourseName());
                requestCode++;
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal2.getTimeInMillis(), pendingIntent);
                //cal2.clear();
            }

            if(c.getThurs() == 5){
                Calendar cal3 = Calendar.getInstance();
                if(cal3.get(Calendar.AM_PM) == 0){
                    cal3.set(Calendar.AM_PM, 1);
                }

                else{
                    cal3.set(Calendar.AM_PM, 0);
                }
                cal3.set(Calendar.HOUR_OF_DAY, c.getHour());
                cal3.set(Calendar.MINUTE, c.getMinute() - 10);
                cal3.set(Calendar.DAY_OF_WEEK, c.getThurs() - 1);
                intent.putExtra("coursename", c.getCourseName());
                requestCode ++;
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal3.getTimeInMillis(), pendingIntent);
                //cal3.clear();

            }

            if(c.getFri() == 6){
                Calendar cal4 = Calendar.getInstance();
                if(cal4.get(Calendar.AM_PM) == 0){
                    cal4.set(Calendar.AM_PM, 1);
                }

                else{
                    cal4.set(Calendar.AM_PM, 0);
                }
                cal4.set(Calendar.HOUR_OF_DAY, c.getHour());
                cal4.set(Calendar.MINUTE, c.getMinute() - 10);
                cal4.set(Calendar.DAY_OF_WEEK, c.getFri() - 1);
                intent.putExtra("coursename", c.getCourseName());
                requestCode ++;
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal4.getTimeInMillis(), pendingIntent);
                //cal4.clear();

            }

        }
    }

    private void saveByPreference(String data){
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString("timetable_demo",data);
        editor.commit();
        //Toast.makeText(this,"saved!",Toast.LENGTH_SHORT).show();
    }

    /** get json data from SharedPreferences and then restore the timetable */
    private void loadSavedData(){
        timetableView.removeAll();
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(this);
        String savedData = mPref.getString("timetable_demo","");
        if(savedData == null && savedData.equals("")) return;
        timetableView.load(savedData);
        //Toast.makeText(this,"loaded!",Toast.LENGTH_SHORT).show();
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
