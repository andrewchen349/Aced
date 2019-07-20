package com.example.andre.aced;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shrikanthravi.collapsiblecalendarview.data.Day;

import model.Events;


public class more_event_info extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private ImageView back;
    private ImageView delete;
    private TextView event_description;
    private TextView event_date;
    private TextView time;
    private TextView time_input;
    private TextView location;
    private TextView location_input;
    private TextView invite;
    private TextView invite_input;
    public int position;
    public Calendar calendar;

    public int yr;
    public int m;
    public int days;
    public String event_title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_event_info);

        //find corresponding XML components
        back = (ImageView) findViewById(R.id.more_info_back);
        delete = (ImageView)findViewById(R.id.more_info_trash);
        event_description = (TextView)findViewById(R.id.more_info_event);
        event_date = (TextView)findViewById(R.id.more_info_event_date);
        time = (TextView)findViewById(R.id.time);
        time_input = (TextView)findViewById(R.id.user_selected_time);
        location = (TextView)findViewById(R.id.location);
        location_input = (TextView)findViewById(R.id.location_input);
        invite = (TextView)findViewById(R.id.more_info_invite);
        invite_input = (TextView)findViewById(R.id.invite_input);

        //Create a Calendar object
        calendar = new Calendar();
        calendar.all_calendar_events.addAll(calendar.db_calendar.getAllEvents());

        //Day day = Calendar.collapsibleCalendar.getSelectedDay();

        Intent i = getIntent();
        yr = i.getIntExtra("year", 0);
        m = i.getIntExtra("month", 0);
        days = i.getIntExtra("day", 0);
        position = i.getIntExtra("position", 0);
        event_title = i.getStringExtra("Event Name");

        event_description.setText(event_title);

        for(Events e : calendar.all_calendar_events){

            if(yr == e.get_later_calendar_year() && m == e.get_later_calendar_month() && days == e.get_later_calendar_day()){
                calendar.current_calendar_events.add(e);
            }
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(more_event_info.this, Calendar.class);
                more_event_info.this.startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.deleteEvent(position);
                calendar.calendar_task_adapter.notifyDataSetChanged();
                Toast.makeText(more_event_info.this, "Event Deleted!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(more_event_info.this, Calendar.class);
                more_event_info.this.startActivity(intent);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        event_date.setText(dateFormat());
    }

    private String monthFormat(int m){

        m = m + 1;

        if(m == 1){
            return "January";
        }

        if(m == 2){
            return "February";
        }

        if(m == 3){
            return "March";
        }

        if(m == 4){
            return "April";
        }

        if(m == 5){
            return "May";
        }

        if(m == 6){
            return "June";
        }

        if(m == 7){
            return "July";
        }
        if(m == 8){
            return "August";
        }

        if(m== 9){
            return "September";
        }
        if(m == 10){
            return "October";
        }

        if(m == 11){
            return "November";
        }
        else{
            return "December";
        }

    }

    public String dateFormat() {

        return monthFormat(m) + " " + days + " " + yr;

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(java.util.Calendar.MINUTE, minute);
        c.set(java.util.Calendar.SECOND, 0);
        int ampm = c.get(java.util.Calendar.AM_PM);

        if(hourOfDay > 12){
            hourOfDay = hourOfDay - 12;
        }

        if(ampm == 0){
            String time = hourOfDay + ":" +  minute + " " + "AM";
            time_input.setText(time);
        }

        else if(ampm == 1) {
            String time = hourOfDay + ":" + minute + " " + "PM";
            time_input.setText(time);
        }

        //Adds a Day for Midnight
        if(c.before(java.util.Calendar.getInstance())) {
            c.add(java.util.Calendar.DATE, 1);
        }

    }
}
