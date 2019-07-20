package com.example.andre.aced;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.rany.albeg.wein.springfabmenu.SpringFabMenu;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import Util.MyDividerItemDecoration;
import Util.Recylcer_Touch_Listener;
import Util.bottomNavBarHelper;
import data.DatabaseHelper;
import model.Events;
import view.Calendar_Task_Adapter;

import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;

public class Calendar extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    //Constants and Fields
    private static final int ACTIVITY_NUM = 0;

    private Button calendar_add_event;
    private RecyclerView recyclerView_calendar;
    public static DatabaseHelper db_calendar;
    public static Calendar_Task_Adapter calendar_task_adapter;
    public List<Events> all_calendar_events = new ArrayList<>();
    public List<Events> current_calendar_events = new ArrayList<>();
    private TextView noEventView;
    private TextView selectDate;

    private int calendar_year;
    private int calendar_day;
    private int calendar_month;
    public TextView mo;

    public static CollapsibleCalendar collapsibleCalendar;
    public static CompactCalendarView compactCalendarView;


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
                        @Override
                        public void run() {
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


        //Find Corresponding XML Components
        //calendar_add_event = (Button)findViewById(R.id.addEvent);
        recyclerView_calendar = (RecyclerView) findViewById(R.id.calendar_recycler_view);
        db_calendar = new DatabaseHelper(this);

        /*calendar_add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                showEventDialog(false, null, -1);
            }
        });*/

        SpringFabMenu sfm = (SpringFabMenu) findViewById(R.id.springfab);

        sfm.setOnSpringFabMenuItemClickListener(new SpringFabMenu.OnSpringFabMenuItemClickListener() {
            @Override
            public void onSpringFabMenuItemClick(View view) {
                switch (view.getId()) {
                    case R.id.fab_1:
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.show(getSupportFragmentManager(), "date picker");
                        showEventDialog(false, null, -1);

                    case R.id.fab_2:
                        break;



                }
            }
        });

        all_calendar_events.addAll(db_calendar.getAllEvents());

        //calendar_task_adapter = new Calendar_Task_Adapter(all_calendar_events, this);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerView_calendar.setLayoutManager(mLayoutManager1);
        recyclerView_calendar.setItemAnimator(new DefaultItemAnimator());
        recyclerView_calendar.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        //recyclerView_calendar.setAdapter(calendar_task_adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                int position = viewHolder.getAdapterPosition();
                deleteEvent(position);



                Toast.makeText(Calendar.this, "Event Deleted!", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView_calendar);

        recyclerView_calendar.addOnItemTouchListener(new Recylcer_Touch_Listener(this,
                recyclerView_calendar, new Recylcer_Touch_Listener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                showActionsDialog(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        collapsibleCalendar = findViewById(R.id.calendarView);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        noEventView = (TextView) findViewById(R.id.empty_eventss_view);
        selectDate = (TextView) findViewById(R.id.select_date_view);
        mo = (TextView) findViewById(R.id.me);

        java.util.Calendar c = java.util.Calendar.getInstance();
        int f = c.get((java.util.Calendar.MONTH));
        mo.setText(monthFormat(f));

        compactCalendarView.setFirstDayOfWeek(java.util.Calendar.MONDAY);

        for (Events e : all_calendar_events) {
            int month = e.get_later_calendar_month() + 1;
            int year = e.get_later_calendar_year();
            int day = e.get_later_calendar_day();

            if (month < 10 && day < 10) {
                String date = "0" + month + "/" + "0" + day + "/" + year;
                //Long millis = null;
                try {
                    long millis = new SimpleDateFormat("MM/dd/yyyy").parse(date).getTime();
                    System.out.println(millis);
                    Event ev2 = new Event(Color.parseColor("#5AC9DD"), millis);
                    compactCalendarView.addEvent(ev2);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
            if (month < 10) {
                String date = "0" + month + "/" + day + "/" + year;
                //Long millis = null;
                try {
                    long millis = new SimpleDateFormat("MM/dd/yyyy").parse(date).getTime();
                    System.out.println(millis);
                    Event ev2 = new Event(Color.parseColor("#5AC9DD"), millis);
                    compactCalendarView.addEvent(ev2);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

            } else {
                String date = month + "/" + day + "/" + year;
                //Long millis = null;
                try {
                    long millis = new SimpleDateFormat("MM/dd/yyyy").parse(date).getTime();
                    System.out.println(millis);
                    Event ev2 = new Event(Color.parseColor("#5AC9DD"), millis);
                    compactCalendarView.addEvent(ev2);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        }


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                selectDate.setVisibility(View.GONE);
                current_calendar_events.clear();

                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(dateClicked);
                int month = cal.get(java.util.Calendar.MONTH);
                int year = cal.get(java.util.Calendar.YEAR);
                int day = cal.get(java.util.Calendar.DAY_OF_MONTH);

                for (Events e : all_calendar_events) {

                    System.out.println("he" + e.get_later_calendar_day());

                    if (e.get_later_calendar_year() == year && e.get_later_calendar_month() == month && e.get_later_calendar_day() == day) {
                        current_calendar_events.add(e);
                    }
                }

                if (current_calendar_events.size() > 0) {
                    noEventView.setVisibility(View.GONE);
                } else {
                    noEventView.setVisibility(View.VISIBLE);
                }

                calendar_task_adapter = new Calendar_Task_Adapter(current_calendar_events, getApplicationContext());
                recyclerView_calendar.setAdapter(calendar_task_adapter);
                calendar_task_adapter.notifyDataSetChanged();

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
                mo.setText(simpleDateFormat.format(firstDayOfNewMonth));
            }
        });


        noEventView.setVisibility(View.GONE);

        /*collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() {
                selectDate.setVisibility(View.GONE);
                current_calendar_events.clear();
                Day day = collapsibleCalendar.getSelectedDay();
                Log.i(getClass().getName(), "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay());

                for(Events e : all_calendar_events){

                    if(e.get_later_calendar_year() == day.getYear() && e.get_later_calendar_month() == day.getMonth() && e.get_later_calendar_day() == day.getDay()){

                        current_calendar_events.add(e);
                    }
                }

                if(current_calendar_events.size() > 0){
                    noEventView.setVisibility(View.GONE);
                }
                else{
                    noEventView.setVisibility(View.VISIBLE);
                }

                calendar_task_adapter = new Calendar_Task_Adapter(current_calendar_events, getApplicationContext());
                recyclerView_calendar.setAdapter(calendar_task_adapter);
                calendar_task_adapter.notifyDataSetChanged();


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
        });*/

    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "More Info", "Delete"};
        //final more_event_info mei = new more_event_info();
        //final Day day = collapsibleCalendar.getSelectedDay();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        AlertDialog.Builder builder1 = builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showEventDialog(true, all_calendar_events.get(position), position);
                }

                if (which == 1) {
                    Intent intent = new Intent(Calendar.this, more_event_info.class);
                    intent.putExtra("Event Name", current_calendar_events.get(position).getEvent());
                    intent.putExtra("year", current_calendar_events.get(position).get_later_calendar_year());
                    intent.putExtra("month", current_calendar_events.get(position).get_later_calendar_month());
                    intent.putExtra("day", current_calendar_events.get(position).get_later_calendar_day());
                    intent.putExtra("position", position);

                    Calendar.this.startActivity(intent);


                } else {
                    deleteEvent(position);
                }
            }
        });
        builder.show();
    }

    private void showEventDialog(final boolean shouldUpdate, final Events event, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.calendar_event_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Calendar.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputEvent = view.findViewById(R.id.event_calendar);
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

    private void createEvent(String event) {

        long id = db_calendar.insertEvent(event);

        // get the newly inserted task from db
        Events n = db_calendar.getEvent(id);

        if (n != null) {
            // adding new task to array list at 0 position
            all_calendar_events.add(0, n);
            n.set_calendar_year(calendar_year);
            n.set_calendar_month(calendar_month);
            n.set_calendar_day(calendar_day);

            db_calendar.insertYear(n);
            db_calendar.insertMonth(n);
            db_calendar.insertDay(n);

            // refreshing the list
            if (calendar_task_adapter != null) {
                calendar_task_adapter.notifyDataSetChanged();
            }
        }

        if (calendar_task_adapter != null) {
            calendar_task_adapter.notifyDataSetChanged();
        }
        setDots(n);

    }

    private void updateEvent(String event, int position) {
        Events n = all_calendar_events.get(position);
        // updating event text
        n.setEvent(event);

        // updating event in db
        db_calendar.updateEvent(n);

        // refreshing the list
        all_calendar_events.set(position, n);
        calendar_task_adapter.notifyItemChanged(position);

    }

    public void deleteEvent(final int position) {


        db_calendar.deleteEvent(all_calendar_events.get(position));

        // removing the task from the list
        all_calendar_events.remove(position);
        int year = current_calendar_events.get(position).get_later_calendar_year();
        int month = current_calendar_events.get(position).get_later_calendar_month();
        int day = current_calendar_events.get(position).get_later_calendar_day();

        Date date = new GregorianCalendar(year, month , day).getTime();

        //compactCalendarView.getEvents(date);
        compactCalendarView.removeEvent(compactCalendarView.getEvents(date).get(0));
        current_calendar_events.remove(position);
        calendar_task_adapter.notifyItemRemoved(position);

        //System.out.println(current_calendar_events.get(position).get_later_calendar_day());
        //compactCalendarView.removeEvents();


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        //create new Calendar instance
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        calendar.set(java.util.Calendar.YEAR, year);
        calendar.set(java.util.Calendar.MONTH, month);
        calendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);

        calendar_year = year;
        calendar_month = month;
        calendar_day = dayOfMonth;

    }


    //Bottom Navigation Bar Setup
    private void setUpBottomNavbar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavbar);
        bottomNavBarHelper.enbaleNav(Calendar.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private String monthFormat(int m) {

        m += 1;
        if (m == 1) {
            return "January";
        }

        if (m == 2) {
            return "February";
        }

        if (m == 3) {
            return "March";
        }

        if (m == 4) {
            return "April";
        }

        if (m == 5) {
            return "May";
        }

        if (m == 6) {
            return "June";
        }

        if (m == 7) {
            return "July";
        }
        if (m == 8) {
            return "August";
        }

        if (m == 9) {
            return "September";
        }
        if (m == 10) {
            return "October";
        }

        if (m == 11) {
            return "November";
        } else {
            return "December";
        }
    }

    private void setDots(Events e) {

            int month = e.get_later_calendar_month() + 1;
            int year = e.get_later_calendar_year();
            int day = e.get_later_calendar_day();

            if (month < 10 && day < 10) {
                String date = "0" + month + "/" + "0" + day + "/" + year;
                //Long millis = null;
                try {
                    long millis = new SimpleDateFormat("MM/dd/yyyy").parse(date).getTime();
                    System.out.println(millis);
                    Event ev2 = new Event(Color.parseColor("#5AC9DD"), millis);
                    compactCalendarView.addEvent(ev2);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
            if (month < 10) {
                String date = "0" + month + "/" + day + "/" + year;
                //Long millis = null;
                try {
                    long millis = new SimpleDateFormat("MM/dd/yyyy").parse(date).getTime();
                    System.out.println(millis);
                    Event ev2 = new Event(Color.parseColor("#5AC9DD"), millis);
                    compactCalendarView.addEvent(ev2);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

            } else {
                String date = month + "/" + day + "/" + year;
                //Long millis = null;
                try {
                    long millis = new SimpleDateFormat("MM/dd/yyyy").parse(date).getTime();
                    System.out.println(millis);
                    Event ev2 = new Event(Color.parseColor("#5AC9DD"), millis);
                    compactCalendarView.addEvent(ev2);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        }

}
