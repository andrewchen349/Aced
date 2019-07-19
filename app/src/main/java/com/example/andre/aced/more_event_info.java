package com.example.andre.aced;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shrikanthravi.collapsiblecalendarview.data.Day;

import model.Events;


public class more_event_info extends AppCompatActivity {

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

        Day day = Calendar.collapsibleCalendar.getSelectedDay();

        Intent i = getIntent();
        yr = i.getIntExtra("year", 0);
        m = i.getIntExtra("month", 0);
        days = i.getIntExtra("day", 0);
        position = i.getIntExtra("position", 0);

        for(Events e : calendar.all_calendar_events){

            if(yr == day.getYear() && m == day.getMonth() && days == day.getDay()){
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
            }
        });
    }
}
