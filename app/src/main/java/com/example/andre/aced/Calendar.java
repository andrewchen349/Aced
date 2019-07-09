package com.example.andre.aced;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.util.ArrayList;
import java.util.List;

import Util.MyDividerItemDecoration;
import Util.bottomNavBarHelper;
import data.DatabaseHelper;
import model.Events;
import view.Calendar_Task_Adapter;

public class Calendar extends AppCompatActivity {

    //Constants and Fields
    private static final int ACTIVITY_NUM  = 0;

    private Button calendar_add_event;
    private RecyclerView recyclerView_calendar;
    private DatabaseHelper db_calendar;
    private Calendar_Task_Adapter calendar_task_adapter;
    private List<Events>all_calendar_events = new ArrayList<>();
    private String selectDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  Declare a new thread to do a preference check for tutorial page
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    final Intent i = new Intent(Calendar.this, Tutorial.class);

                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            startActivity(i);
                        }
                    });

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view1);
        setUpBottomNavbar();

        final CollapsibleCalendar collapsibleCalendar = findViewById(R.id.calendarView);
        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() {
                Day day = collapsibleCalendar.getSelectedDay();
                Log.i(getClass().getName(), "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay());
                selectDate = Integer.toString(day.getYear()) + Integer.toString(day.getMonth()) +Integer.toString(day.getDay());
            }

            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {

            }

            @Override
            public void onWeekChange(int i) {

            }
        });



        //Find Corresponding XML Components
        calendar_add_event = (Button)findViewById(R.id.addEvent);
        recyclerView_calendar = (RecyclerView)findViewById(R.id.calendar_recycler_view);
        db_calendar = new DatabaseHelper(this);

        calendar_add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTaskDialog(false, null, -1);
            }
        });

        all_calendar_events.addAll(db_calendar.getAllEvents());

        calendar_task_adapter = new Calendar_Task_Adapter(all_calendar_events, this);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerView_calendar.setLayoutManager(mLayoutManager1);
        recyclerView_calendar.setItemAnimator(new DefaultItemAnimator());
        recyclerView_calendar.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView_calendar.setAdapter(calendar_task_adapter);

        }

    private void showTaskDialog(final boolean shouldUpdate, final Events event, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.calendar_event_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Calendar.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputEvent= view.findViewById(R.id.event_calendar);
        TextView dialogTitle = view.findViewById(R.id.dialog_title_calendar);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_event_title) : getString(R.string.lbl_edit_event_title));

        if (shouldUpdate && event != null) {
            inputEvent.setText(event.getEvent());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputEvent.getText().toString())) {
                    Toast.makeText(Calendar.this, "Enter event!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating task
                if (shouldUpdate && event != null) {
                    // update task by it's id
                    updateEvent(inputEvent.getText().toString(), position);
                } else {
                    // create new task
                    createEvent(inputEvent.getText().toString());
                }
            }
        });
    }

    private void createEvent(String event){

        long id = db_calendar.insertEvent(event);

        // get the newly inserted task from db
        Events n = db_calendar.getEvent(id);

        if (n != null) {
            // adding new task to array list at 0 position
            all_calendar_events.add(0, n);

            // refreshing the list
            calendar_task_adapter.notifyDataSetChanged();

        }

        calendar_task_adapter.notifyDataSetChanged();
    }

    private void updateEvent(String event, int position){
        Events n = all_calendar_events.get(position);
        // updating event text
        n.setEvent(event);

        // updating event in db
        db_calendar.updateEvent(n);

        // refreshing the list
        all_calendar_events.set(position, n);
        calendar_task_adapter.notifyItemChanged(position);

    }

    private void deleteEvent(final int position){

        db_calendar.deleteEvent(all_calendar_events.get(position));

        // removing the task from the list
        all_calendar_events.remove(position);
        calendar_task_adapter.notifyItemRemoved(position);

    }

    //Bottom Navigation Bar Setup
   private void setUpBottomNavbar(){
       BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomnavbar);
       bottomNavBarHelper.enbaleNav(Calendar.this, bottomNavigationView);
       Menu menu = bottomNavigationView.getMenu();
       MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
       menuItem.setChecked(true);
       }
}
