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

import Util.bottomNavBarHelper;

public class Calendar extends AppCompatActivity {

    //Constants and Fields
    private static final int ACTIVITY_NUM  = 0;

    private Button calendar_add_event;
    private RecyclerView recyclerView_calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view1);
        setUpBottomNavbar();

        //Find Corresponding XML Components
        calendar_add_event = (Button)findViewById(R.id.addEvent);
        recyclerView_calendar = (RecyclerView)findViewById(R.id.calendar_recycler_view);

        //  Declare a new thread to do a preference check
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


        final CollapsibleCalendar collapsibleCalendar = findViewById(R.id.calendarView);
        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() {
                Day day = collapsibleCalendar.getSelectedDay();
                Log.i(getClass().getName(), "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay());
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

        calendar_add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTaskDialog(false, null, -1);
            }
        });

    }

    private void showTaskDialog(final boolean shouldUpdate, final model.Checklist task, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.calendar_event_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Calendar.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputTask= view.findViewById(R.id.event_calendar);
        TextView dialogTitle = view.findViewById(R.id.dialog_title_calendar);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_event_title) : getString(R.string.lbl_edit_event_title));

        if (shouldUpdate && task != null) {
            inputTask.setText(task.getTask());
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
                if (TextUtils.isEmpty(inputTask.getText().toString())) {
                    Toast.makeText(Calendar.this, "Enter event!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating task
                if (shouldUpdate && task != null) {
                    // update task by it's id
                    updateEvent(inputTask.getText().toString(), position);
                } else {
                    // create new task
                    createEvent(inputTask.getText().toString());
                }
            }
        });
    }

    private void createEvent(String event){
        //TODO
    }

    private void updateEvent(String event, int position){
        //TODO
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
