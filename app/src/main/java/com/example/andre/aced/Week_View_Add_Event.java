package com.example.andre.aced;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.tlaabs.timetableview.Schedule;

import java.util.ArrayList;

public class Week_View_Add_Event extends AppCompatActivity {

    private EditText eventName;
    private EditText location;
    private Button save;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_view_addevent);

        findXML();

        final WeeklyView weeklyView = new WeeklyView();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Schedule schedule = new Schedule();
                schedule.setClassTitle(eventName.getText().toString());
                schedule.setClassPlace(location.getText().toString());
                Intent intent = new Intent(Week_View_Add_Event.this, WeeklyView.class);
                Week_View_Add_Event.this.startActivity(intent);
                weeklyView.scheduleList.add(schedule);
            }
        });


    }

    private void findXML(){

        eventName = (EditText)findViewById(R.id.event_name);
        location = (EditText)findViewById(R.id.event_location);
        save = (Button)findViewById(R.id.event_confirm);
    }

    private void showActionDialog() {

        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == 0){
                    //TODO
                }

                if(which == 1){

                }

            }
        });
        builder.show();
    }
}
