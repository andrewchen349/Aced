package com.example.andre.aced;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dpro.widgets.WeekdaysPicker;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;

import java.util.ArrayList;

public class Weekly_View_Update_Event extends AppCompatActivity implements RangeTimePickerDialog.ISelectedTime {

    private EditText eventName;
    private EditText location;
    private Button save;
    private TextView time;
    private ImageView trash;
    private WeekdaysPicker weekdaysPicker;

    private int mode;
    private int editIdx;

    private Schedule schedule;

    public static final int RESULT_OK_ADD = 1;
    public static final int RESULT_OK_EDIT = 2;
    public static final int RESULT_OK_DELETE = 3;

    private int user_selected_hr_start;
    private int user_selected_min_start;
    private int user_selected_hr_end;
    private int user_selected_min_end;

    private Boolean clicked = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_view_update_view);

        findXML();

        schedule = new Schedule();

        checkMode();

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                clicked = true;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode == WeeklyView.REQUEST_EDIT){
                    inputDataProcessing();
                    Intent i = new Intent();
                    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
                    schedules.add(schedule);
                    i.putExtra("idx",editIdx);
                    i.putExtra("schedules",schedules);
                    setResult(RESULT_OK_EDIT,i);
                    finish();
                    //Weekly_View_Update_Event.this.startActivity(i);

                }

                if(mode == WeeklyView.REQUEST_ADD){
                    inputDataProcessing();
                    Intent i = new Intent();
                    ArrayList<Schedule> schedules = new ArrayList<Schedule>();
                    schedules.add(schedule);
                    i.putExtra("schedules",schedules);
                    setResult(RESULT_OK_ADD,i);
                    finish();
                }
            }
        });

        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("idx",editIdx);
                setResult(RESULT_OK_DELETE, i);
                finish();
            }
        });

        weekdaysPicker.setSelectOnlyOne(true);



    }

    private void findXML(){
        eventName = (EditText)findViewById(R.id.event_name);
        location = (EditText)findViewById(R.id.event_location);
        save = (Button)findViewById(R.id.event_confirm);
        time = (TextView)findViewById(R.id.timenumbers);
        trash = (ImageView) findViewById(R.id.course_delete);
        weekdaysPicker = (WeekdaysPicker)findViewById(R.id.weekdays);
    }

    private void checkMode(){
        Intent i = getIntent();
        mode = i.getIntExtra("mode", WeeklyView.REQUEST_ADD);

        if(mode == WeeklyView.REQUEST_EDIT){
            weekdaysPicker.setVisibility(View.INVISIBLE);
            loadScheduleData();
        }
    }

    private void loadScheduleData(){
        Intent i = getIntent();
        editIdx = i.getIntExtra("idx",-1);
        ArrayList<Schedule> schedules = (ArrayList<Schedule>)i.getSerializableExtra("schedules");
        schedule = schedules.get(0);

    }

    @Override
    public void onSelectedTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {

        user_selected_hr_start = hourStart;
        user_selected_min_start = minuteStart;
        user_selected_hr_end = hourEnd;
        user_selected_min_end = minuteEnd;

    }

    private void inputDataProcessing(){
        schedule.setClassTitle(eventName.getText().toString());
        schedule.setClassPlace(location.getText().toString());

        if(weekdaysPicker.getSelectedDays().size() != 0){
            schedule.setDay(weekdaysPicker.getSelectedDays().get(0) - 2);
        }

        if(clicked || mode == WeeklyView.REQUEST_ADD){
            schedule.setStartTime(new Time(user_selected_hr_start,user_selected_min_start));
            schedule.setEndTime(new Time(user_selected_hr_end, user_selected_min_end));
        }
    }
}
