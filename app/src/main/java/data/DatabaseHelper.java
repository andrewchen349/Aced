package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.andre.aced.Checklist;

import java.util.ArrayList;
import java.util.List;

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
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + model.Checklist.TABLE_NAME1);

        // Create tables again
        onCreate(db);
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
                new String[]{model.Checklist.COLUMN_ID1, model.Checklist.COLUMN_TASK2, model.Checklist.COLUMN_TIMESTAMP2},
                model.Checklist.COLUMN_ID1 + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        model.Checklist task = new model.Checklist(cursor.getInt(cursor.getColumnIndex(model.Checklist.COLUMN_ID1)),
                cursor.getString(cursor.getColumnIndex(model.Checklist.COLUMN_TASK2)),
                cursor.getString(cursor.getColumnIndex(model.Checklist.COLUMN_TIMESTAMP2)));

        //Close db connection
        cursor.close();

        return task;

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
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null); // Points to all rows

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                model.Checklist task = new model.Checklist();
                task.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                task.setTask(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));
                task.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

                tasks.add(task);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        //returns List
        return tasks;
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
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
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
    }
