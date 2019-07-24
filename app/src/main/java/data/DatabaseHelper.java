package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import model.Checklist;
import model.Course;
import model.Events;
import model.Note;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_checklist_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Note.CREATE_TABLE);

        //creates checklist table
        db.execSQL(model.Checklist.CREATE_TABLE1);

        //create Calendar Event table
        db.execSQL(Events.CREATE_TABLE2);

        //Create Courses Table
        db.execSQL(Course.CREATE_TABLE3);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + model.Checklist.TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + Events.TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + Course.TABLE_NAME3);

        // Create tables again
        onCreate(db);
    }

    public long insertCourseName(String coursename){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_COURSENAME, coursename);

        long id = db.insert(Course.TABLE_NAME3, null, values);
        db.close();

        return id;
    }


    public long insertNote(String note) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them

        values.put(Note.COLUMN_NOTE, note);


        // insert row
        long id = db.insert(Note.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id

        return id;
    }

    public long insertChecklist(String task){
        //Opens db for inserting new task
        SQLiteDatabase db = this.getWritableDatabase();

        //Store info in key value pairs using android ContentValues
        ContentValues values = new ContentValues();
        values.put(model.Checklist.COLUMN_TASK2, task);

        long id = db.insert(model.Checklist.TABLE_NAME1, null, values);

        db.close();

        return id; //return the id of the task at specific row
    }

    public long insertEvent(String event){
        //Open db for inserting new task
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Events.COLUMN_EVENTS, event);

        long id = db.insert(Events.TABLE_NAME2, null, values);
        db.close();
        return id;
    }

    public int insertMon(Course course){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_MONDAY, course.getMon());

        return db.update(Course.TABLE_NAME3, values, Course.COLUMN_ID3 + " = ?",
                new String[]{String.valueOf(course.getId())});

    }

    public int insertTues(Course course){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_TUESDAY, course.getMon());

        return db.update(Course.TABLE_NAME3, values, Course.COLUMN_ID3 + " = ?",
                new String[]{String.valueOf(course.getId())});

    }

    public int insertWed(Course course){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_WEDNESDAY, course.getMon());

        return db.update(Course.TABLE_NAME3, values, Course.COLUMN_ID3 + " = ?",
                new String[]{String.valueOf(course.getId())});

    }

    public int insertThurs(Course course){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_THURSDAY, course.getMon());

        return db.update(Course.TABLE_NAME3, values, Course.COLUMN_ID3 + " = ?",
                new String[]{String.valueOf(course.getId())});

    }

    public int insertFri(Course course){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_FRIDAY, course.getMon());

        return db.update(Course.TABLE_NAME3, values, Course.COLUMN_ID3 + " = ?",
                new String[]{String.valueOf(course.getId())});

    }


    public int insertYear(Events event){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Events.COLUMN_YEAR, event.get_later_calendar_year());

        return db.update(Events.TABLE_NAME2, values, Events.COLUMN_ID2 + " = ?",
                new String[]{String.valueOf(event.getId())});
    }

    public int insertMonth(Events event){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Events.COLUMN_MONTH, event.get_later_calendar_month());

        return db.update(Events.TABLE_NAME2, values, Events.COLUMN_ID2 + " = ?",
                new String[]{String.valueOf(event.getId())});
    }

    public int insertDay(Events event){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Events.COLUMN_DAY, event.get_later_calendar_day());

        return db.update(Events.TABLE_NAME2, values, Events.COLUMN_ID2 + " = ?",
                new String[]{String.valueOf(event.getId())});
    }

    //Event set/get min, hr

    public int insertEventHour(Events events){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Events.COLUMN_HOUR, events.getHour());

        return db.update(Events.TABLE_NAME2, values, Events.COLUMN_ID2 + " = ?",
                new String[]{String.valueOf(events.getId())});
    }

    public int insertEventMinute(Events events){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Events.COLUMN_MINUTE, events.getMinute());

        return db.update(Events.TABLE_NAME2, values, Events.COLUMN_ID2 + " = ?",
                new String[]{String.valueOf(events.getId())});
    }

    public int insertEventLocation(Events events){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Events.COLUMN_LOCATION, events.getLocation());

        return db.update(Events.TABLE_NAME2, values, Events.COLUMN_ID2 + " = ?",
                new String[]{String.valueOf(events.getId())});
    }

    public int insertTaskLocation(Checklist task){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Checklist.COLUMN_LOCATION, task.getLocation());

        return db.update(Checklist.TABLE_NAME1, values, Checklist.COLUMN_ID1 + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    public int insertCourseLocation(Course course){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_LOCATIONCLASS, course.getLocation());

        return db.update(Course.TABLE_NAME3, values, Course.COLUMN_ID3 + " = ?",
                new String[]{String.valueOf(course.getId())});
    }

    public int insertTeacherEmail(Course course){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_PROFESSOREMAIL, course.getEmail());

        return db.update(Course.TABLE_NAME3, values, Course.COLUMN_ID3 + " = ?",
                new String[]{String.valueOf(course.getId())});
    }

    public String getCourseLocation(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Course.TABLE_NAME3,
                new String[]{Course.COLUMN_ID3, Course.COLUMN_COURSENAME, Course.COLUMN_PROFESSOREMAIL, Course.COLUMN_TEACHERNAME, Course.COLUMN_LOCATIONCLASS,
                        Course.COLUMN_MONDAY, Course.COLUMN_TUESDAY, Course.COLUMN_WEDNESDAY, Course.COLUMN_THURSDAY,Course.COLUMN_FRIDAY},
                Course.COLUMN_ID3 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        String s  = cursor.getString(cursor.getColumnIndex(Course.COLUMN_LOCATIONCLASS));

        cursor.close();

        return s;
    }

    public String getCourseTeacherEmail(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Course.TABLE_NAME3,
                new String[]{Course.COLUMN_ID3, Course.COLUMN_COURSENAME, Course.COLUMN_PROFESSOREMAIL, Course.COLUMN_TEACHERNAME, Course.COLUMN_LOCATIONCLASS,
                        Course.COLUMN_MONDAY, Course.COLUMN_TUESDAY, Course.COLUMN_WEDNESDAY, Course.COLUMN_THURSDAY,Course.COLUMN_FRIDAY},
                Course.COLUMN_ID3 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        String s  = cursor.getString(cursor.getColumnIndex(Course.COLUMN_PROFESSOREMAIL));

        cursor.close();

        return s;
    }

    public String getCourseTeacher(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Course.TABLE_NAME3,
                new String[]{Course.COLUMN_ID3, Course.COLUMN_COURSENAME, Course.COLUMN_PROFESSOREMAIL, Course.COLUMN_TEACHERNAME, Course.COLUMN_LOCATIONCLASS,
                        Course.COLUMN_MONDAY, Course.COLUMN_TUESDAY, Course.COLUMN_WEDNESDAY, Course.COLUMN_THURSDAY,Course.COLUMN_FRIDAY},
                Course.COLUMN_ID3 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        String s  = cursor.getString(cursor.getColumnIndex(Course.COLUMN_TEACHERNAME));

        cursor.close();

        return s;
    }

    public int getMon(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Course.TABLE_NAME3,
                new String[]{Course.COLUMN_ID3, Course.COLUMN_COURSENAME, Course.COLUMN_PROFESSOREMAIL, Course.COLUMN_TEACHERNAME, Course.COLUMN_LOCATIONCLASS,
                Course.COLUMN_MONDAY, Course.COLUMN_TUESDAY, Course.COLUMN_WEDNESDAY, Course.COLUMN_THURSDAY,Course.COLUMN_FRIDAY},
                Course.COLUMN_ID3 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        int min = cursor.getInt(cursor.getColumnIndex(Course.COLUMN_MONDAY));

        cursor.close();

        return min;
    }

    public int getTues(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Course.TABLE_NAME3,
                new String[]{Course.COLUMN_ID3, Course.COLUMN_COURSENAME, Course.COLUMN_PROFESSOREMAIL, Course.COLUMN_TEACHERNAME, Course.COLUMN_LOCATIONCLASS,
                        Course.COLUMN_MONDAY, Course.COLUMN_TUESDAY, Course.COLUMN_WEDNESDAY, Course.COLUMN_THURSDAY,Course.COLUMN_FRIDAY},
                Course.COLUMN_ID3 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        int min = cursor.getInt(cursor.getColumnIndex(Course.COLUMN_TUESDAY));

        cursor.close();

        return min;
    }

    public int getWed(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Course.TABLE_NAME3,
                new String[]{Course.COLUMN_ID3, Course.COLUMN_COURSENAME, Course.COLUMN_PROFESSOREMAIL, Course.COLUMN_TEACHERNAME, Course.COLUMN_LOCATIONCLASS,
                        Course.COLUMN_MONDAY, Course.COLUMN_TUESDAY, Course.COLUMN_WEDNESDAY, Course.COLUMN_THURSDAY,Course.COLUMN_FRIDAY},
                Course.COLUMN_ID3 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        int min = cursor.getInt(cursor.getColumnIndex(Course.COLUMN_WEDNESDAY));

        cursor.close();

        return min;
    }

    public int getThurs(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Course.TABLE_NAME3,
                new String[]{Course.COLUMN_ID3, Course.COLUMN_COURSENAME, Course.COLUMN_PROFESSOREMAIL, Course.COLUMN_TEACHERNAME, Course.COLUMN_LOCATIONCLASS,
                        Course.COLUMN_MONDAY, Course.COLUMN_TUESDAY, Course.COLUMN_WEDNESDAY, Course.COLUMN_THURSDAY,Course.COLUMN_FRIDAY},
                Course.COLUMN_ID3 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        int min = cursor.getInt(cursor.getColumnIndex(Course.COLUMN_THURSDAY));

        cursor.close();

        return min;
    }

    public int getFri(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Course.TABLE_NAME3,
                new String[]{Course.COLUMN_ID3, Course.COLUMN_COURSENAME, Course.COLUMN_PROFESSOREMAIL, Course.COLUMN_TEACHERNAME, Course.COLUMN_LOCATIONCLASS,
                        Course.COLUMN_MONDAY, Course.COLUMN_TUESDAY, Course.COLUMN_WEDNESDAY, Course.COLUMN_THURSDAY,Course.COLUMN_FRIDAY},
                Course.COLUMN_ID3 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        int min = cursor.getInt(cursor.getColumnIndex(Course.COLUMN_FRIDAY));

        cursor.close();

        return min;
    }

    public int getEventMin(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Events.TABLE_NAME2,
                new String[]{Events.COLUMN_ID2, Events.COLUMN_EVENTS, Events.COLUMN_MONTH, Events.COLUMN_YEAR, Events.COLUMN_DAY, Events.COLUMN_HOUR, Events.COLUMN_MINUTE, Events.COLUMN_LOCATION},
                Events.COLUMN_ID2 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        int min = cursor.getInt(cursor.getColumnIndex(Events.COLUMN_MINUTE));

        cursor.close();

        return min;
    }

    public int getEventHour(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Events.TABLE_NAME2,
                new String[]{Events.COLUMN_ID2, Events.COLUMN_EVENTS, Events.COLUMN_MONTH, Events.COLUMN_YEAR, Events.COLUMN_DAY, Events.COLUMN_HOUR, Events.COLUMN_MINUTE, Events.COLUMN_LOCATION},
                Events.COLUMN_ID2 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        int min = cursor.getInt(cursor.getColumnIndex(Events.COLUMN_HOUR));

        cursor.close();

        return min;
    }

    public int insertHour(model.Checklist task){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(model.Checklist.COLUMN_HOUR, task.getHour());

        return db.update(model.Checklist.TABLE_NAME1, values, model.Checklist.COLUMN_ID1 + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    public int insertMinute(model.Checklist task){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(model.Checklist.COLUMN_MINUTE, task.getMinute());

        return db.update(model.Checklist.TABLE_NAME1, values, model.Checklist.COLUMN_ID1 + " = ?",
                new String[]{String.valueOf(task.getId())});
    }
    public int getMin(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(model.Checklist.TABLE_NAME1,
                new String[]{model.Checklist.COLUMN_ID1, model.Checklist.COLUMN_TASK2, model.Checklist.COLUMN_TIMESTAMP2,
                        model.Checklist.COLUMN_HOUR, model.Checklist.COLUMN_MINUTE, Checklist.COLUMN_LOCATION},
                model.Checklist.COLUMN_ID1 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        int min = cursor.getInt(cursor.getColumnIndex(model.Checklist.COLUMN_MINUTE));

        cursor.close();

        return min;
    }

    public int getHour(long id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(model.Checklist.TABLE_NAME1,
                new String[]{model.Checklist.COLUMN_ID1, model.Checklist.COLUMN_TASK2, model.Checklist.COLUMN_TIMESTAMP2,
                        model.Checklist.COLUMN_HOUR, model.Checklist.COLUMN_MINUTE, Checklist.COLUMN_LOCATION},
                model.Checklist.COLUMN_ID1 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        int hour = cursor.getInt(cursor.getColumnIndex(model.Checklist.COLUMN_HOUR));

        cursor.close();

        return hour;
    }


    public Note getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        //Retreive specific info from rows using Android Cursor
        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_ID, Note.COLUMN_NOTE, Note.COLUMN_TIMESTAMP},
                Note.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare task object
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return note;
    }

    public model.Checklist getTask(long id){

        //reads database
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(model.Checklist.TABLE_NAME1,
                new String[]{model.Checklist.COLUMN_ID1, model.Checklist.COLUMN_TASK2, model.Checklist.COLUMN_TIMESTAMP2,
                        model.Checklist.COLUMN_HOUR, model.Checklist.COLUMN_MINUTE, Checklist.COLUMN_LOCATION},
                model.Checklist.COLUMN_ID1 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        model.Checklist task = new model.Checklist(cursor.getInt(cursor.getColumnIndex(model.Checklist.COLUMN_ID1)),
                cursor.getString(cursor.getColumnIndex(model.Checklist.COLUMN_TASK2)),
                cursor.getString(cursor.getColumnIndex(model.Checklist.COLUMN_TIMESTAMP2)),
                cursor.getInt(cursor.getColumnIndex(model.Checklist.COLUMN_HOUR)),
                cursor.getInt(cursor.getColumnIndex(model.Checklist.COLUMN_MINUTE)),
                cursor.getString(cursor.getColumnIndex(Checklist.COLUMN_LOCATION)));

        cursor.close();

        return task;

    }

    public Events getEvent(long id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Events.TABLE_NAME2,
                new String[]{Events.COLUMN_ID2, Events.COLUMN_EVENTS, Events.COLUMN_YEAR, Events.COLUMN_MONTH,
                        Events.COLUMN_DAY, Events.COLUMN_HOUR, Events.COLUMN_MINUTE, Events.COLUMN_LOCATION},
                Events.COLUMN_ID2 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }



       Events event = new Events(cursor.getInt(cursor.getColumnIndex(Events.COLUMN_ID2)),
                cursor.getString(cursor.getColumnIndex(Events.COLUMN_EVENTS)),
                cursor.getInt(cursor.getColumnIndex(Events.COLUMN_YEAR)),
               cursor.getInt(cursor.getColumnIndex(Events.COLUMN_HOUR)),
               cursor.getInt(cursor.getColumnIndex(Events.COLUMN_MINUTE)),
               cursor.getInt(cursor.getColumnIndex(Events.COLUMN_MONTH)),
               cursor.getInt(cursor.getColumnIndex(Events.COLUMN_DAY)),
               cursor.getString(cursor.getColumnIndex(Events.COLUMN_LOCATION)));

        /*Events eventTime = new Events(cursor.getInt(cursor.getColumnIndex(Events.COLUMN_ID2)),
                cursor.getString(cursor.getColumnIndex(Events.COLUMN_EVENTS)),
                cursor.getInt(cursor.getColumnIndex(Events.COLUMN_YEAR)),
                cursor.getInt(cursor.getColumnIndex(Events.COLUMN_MONTH)),
                cursor.getInt(cursor.getColumnIndex(Events.COLUMN_HOUR)),
                cursor.getInt(cursor.getColumnIndex(Events.COLUMN_MINUTE)),
                cursor.getInt(cursor.getColumnIndex(Events.COLUMN_DAY)));*/

        /*if( cursor.getInt(cursor.getColumnIndex(Events.COLUMN_HOUR)) != 0 && cursor.getInt(cursor.getColumnIndex(Events.COLUMN_MINUTE)) != 0){
            cursor.close();
            return eventTime;

        }*/


            cursor.close();
            return  event;



        //cursor.close();


    }

    public Course getCourse(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Course.TABLE_NAME3,
                new String[]{Course.COLUMN_ID3, Course.COLUMN_COURSENAME, Course.COLUMN_PROFESSOREMAIL, Course.COLUMN_TEACHERNAME, Course.COLUMN_LOCATIONCLASS,
                        Course.COLUMN_MONDAY, Course.COLUMN_TUESDAY, Course.COLUMN_WEDNESDAY, Course.COLUMN_THURSDAY,Course.COLUMN_FRIDAY},
                Course.COLUMN_ID3 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Course course = new Course(cursor.getInt(cursor.getColumnIndex(Course.COLUMN_ID3)),
                cursor.getString(cursor.getColumnIndex(Course.COLUMN_COURSENAME)),
                cursor.getString(cursor.getColumnIndex(Course.COLUMN_TEACHERNAME)),
                cursor.getInt(cursor.getColumnIndex(Course.COLUMN_MONDAY)),
                cursor.getInt(cursor.getColumnIndex(Course.COLUMN_TUESDAY)),
                cursor.getInt(cursor.getColumnIndex(Course.COLUMN_WEDNESDAY)),
                cursor.getInt(cursor.getColumnIndex(Course.COLUMN_THURSDAY)),
                cursor.getInt(cursor.getColumnIndex(Course.COLUMN_FRIDAY)),
                cursor.getString(cursor.getColumnIndex(Course.COLUMN_LOCATIONCLASS)),
                cursor.getString(cursor.getColumnIndex(Course.COLUMN_PROFESSOREMAIL)));

        cursor.close();
        return course;

    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    //add all Tasks to a List

    public List<model.Checklist> getAllTasks(){
        List<model.Checklist> tasks = new ArrayList<>();

        // Select All Query Arrange in DESC
        String selectQuery = "SELECT  * FROM " + model.Checklist.TABLE_NAME1 + " ORDER BY " +
                model.Checklist.COLUMN_TIMESTAMP2 + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null); // Points to all rows

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                model.Checklist task = new model.Checklist();
                task.setId(cursor.getInt(cursor.getColumnIndex(model.Checklist.COLUMN_ID1)));
                task.setTask(cursor.getString(cursor.getColumnIndex(model.Checklist.COLUMN_TASK2)));
                task.setTimestamp(cursor.getString(cursor.getColumnIndex(model.Checklist.COLUMN_TIMESTAMP2)));
                task.setHour(cursor.getInt(cursor.getColumnIndex(model.Checklist.COLUMN_HOUR)));
                task.setMin(cursor.getInt(cursor.getColumnIndex(model.Checklist.COLUMN_MINUTE)));
                task.setLocation(cursor.getString(cursor.getColumnIndex(Checklist.COLUMN_LOCATION)));


                tasks.add(task);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        //returns List
        return tasks;
    }
    public List<Events>getAllEvents(){
        List<Events> events = new ArrayList<>();

        // Select All Query Arrange in DESC
        String selectQuery = "SELECT  * FROM " + Events.TABLE_NAME2 + " ORDER BY " +
                Events.COLUMN_DAY + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null); // Points to all rows

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Events event = new Events();
                event.setId(cursor.getInt(cursor.getColumnIndex(Events.COLUMN_ID2)));
                event.setEvent(cursor.getString(cursor.getColumnIndex(Events.COLUMN_EVENTS)));
                event.set_calendar_year(cursor.getInt(cursor.getColumnIndex(Events.COLUMN_YEAR)));
                event.set_calendar_month(cursor.getInt(cursor.getColumnIndex(Events.COLUMN_MONTH)));
                event.setHour(cursor.getInt(cursor.getColumnIndex(Events.COLUMN_HOUR)));
                event.setMinute(cursor.getInt(cursor.getColumnIndex(Events.COLUMN_MINUTE)));
                event.set_calendar_day(cursor.getInt(cursor.getColumnIndex(Events.COLUMN_DAY)));
                event.setLocation(cursor.getString(cursor.getColumnIndex(Events.COLUMN_LOCATION)));

                events.add(event);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        //returns List
        return events;

    }

    public List<Course>getAllCourses(){
        List<Course> courses = new ArrayList<>();

        // Select All Query Arrange in DESC
        String selectQuery = "SELECT  * FROM " + Course.TABLE_NAME3 + " ORDER BY " +
                Course.COLUMN_COURSENAME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null); // Points to all rows

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setId(cursor.getInt(cursor.getColumnIndex(Course.COLUMN_ID3)));
                course.setCourseName(cursor.getString(cursor.getColumnIndex(Course.COLUMN_COURSENAME)));
                course.setProfessorName(cursor.getString(cursor.getColumnIndex(Course.COLUMN_TEACHERNAME)));
                course.setEmail(cursor.getString(cursor.getColumnIndex(Course.COLUMN_PROFESSOREMAIL)));
                course.setLocation(cursor.getString(cursor.getColumnIndex(Course.COLUMN_LOCATIONCLASS)));


                courses.add(course);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        //returns List
        return courses;

    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Note.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }


    public int getTaskCount(){
        int count;

        String countQuery = "SELECT  * FROM " + model.Checklist.TABLE_NAME1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        count = cursor.getCount();
        cursor.close();

        return count;



    }
    public int getEvensCount(){
         int count;

        String countQuery = "SELECT  * FROM " + Events.TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        count = cursor.getCount();
        cursor.close();

        return count;

    }

    public int getCoursesCount(){
        int count;

        String countQuery = "SELECT  * FROM " + Course.TABLE_NAME3;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        count = cursor.getCount();
        cursor.close();

        return count;


    }


    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_NOTE, note.getNote());

        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    //Updating Tasks
    public int updateTask(model.Checklist task){

        //Modfiying Database
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(model.Checklist.COLUMN_TASK2, task.getTask());

        //update row with new task
        return db.update(model.Checklist.TABLE_NAME1, values, model.Checklist.COLUMN_ID1 + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    public int updateEvent(Events events){

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(Events.COLUMN_EVENTS, events.getEvent());

        //update row with new task
        return db.update(Events.TABLE_NAME2, values, Events.COLUMN_ID2 + " = ?",
                new String[]{String.valueOf(events.getId())});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public void deleteTask(model.Checklist task){

        //Need Readable Database for deletion
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(model.Checklist.TABLE_NAME1, model.Checklist.COLUMN_ID1 + " = ?",
                new String[]{String.valueOf(task.getId())});

        //close connection
        db.close();

    }

    public void deleteEvent(Events event){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Events.TABLE_NAME2, Events.COLUMN_ID2 + " = ?",
                new String[]{String.valueOf(event.getId())});
        db.close();

    }
    }
