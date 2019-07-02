package com.example.andre.aced;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Util.MyDividerItemDecoration;
import Util.Recylcer_Touch_Listener;
import Util.bottomNavBarHelper;
import data.DatabaseHelper;
import view.ChecklistAdapter;

//import view.ChecklistAdapter;

public class Checklist extends AppCompatActivity {

    private static final int ACTIVITY_NUM  = 1;

    private ImageView moreOptions;
    private DatabaseHelper db;
    private RecyclerView recyclerView1;
    private ChecklistAdapter checklistAdapter;
    private List<model.Checklist>checklistList = new ArrayList<>();
    private TextView noTaskView;
    private Button add;
    private CheckBox completeTask;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        setUpBottomNavbar();

        //Field Corresponding XML Components
        moreOptions = (ImageView)findViewById(R.id.moreoption);
        moreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Checklist.this, Option.class);
                Checklist.this.startActivity(intent1);
            }
        });



        add = (Button)findViewById(R.id.addTask);
        recyclerView1 = (RecyclerView)findViewById(R.id.recycler_view_checklist);
        noTaskView = (TextView)findViewById(R.id.empty_checklist_view);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTaskDialog(false, null, -1);
            }
        });

        db = new DatabaseHelper(this);
        checklistList.addAll(db.getAllTasks());


        checklistAdapter = new ChecklistAdapter(checklistList, this);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerView1.setLayoutManager(mLayoutManager1);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView1.setAdapter(checklistAdapter);


        recyclerView1.addOnItemTouchListener(new Recylcer_Touch_Listener(this,
                recyclerView1, new Recylcer_Touch_Listener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                completeTasks(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));

        toggleEmptyTask();


    }

    private void completeTasks(final int position){

        completeTask = (CheckBox)findViewById(R.id.dot_checklist) ;

        completeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(completeTask.isChecked()){
                    completeTask.setVisibility(View.INVISIBLE);
                    Toast.makeText(Checklist.this, "Task Completed!", Toast.LENGTH_LONG).show();
                    db.deleteTask(checklistList.get(position));
                    checklistList.remove(position);
                    checklistAdapter.notifyItemRemoved(position);
                }
            }
        });

        toggleEmptyTask();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete", "Mark As Complete", "Set Priority"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showTaskDialog(true, checklistList.get(position), position);
                 if (which == 1){
                        completeTasks(position);
                    }
                } else {
                    deleteTask(position);
                }
            }
        });
        builder.show();
    }

    private void createTask(String task) {
        // inserting task in db and getting
        // newly inserted task id
        long id = db.insertChecklist(task);

        // get the newly inserted task from db
        model.Checklist n = db.getTask(id);

        if (n != null) {
            // adding new task to array list at 0 position
            checklistList.add(0, n);

            // refreshing the list
            checklistAdapter.notifyDataSetChanged();

            toggleEmptyTask();
        }

        checklistAdapter.notifyDataSetChanged();
    }

    private void updateTask(String task, int position) {
        model.Checklist n = checklistList.get(position);
        // updating task text
        n.setTask(task);

        // updating task in db
        db.updateTask(n);

        // refreshing the list
        checklistList.set(position, n);
        checklistAdapter.notifyItemChanged(position);

        toggleEmptyTask();
    }


    private void deleteTask(final int position) {
        // deleting the task from db
        db.deleteTask(checklistList.get(position));

        // removing the task from the list
        checklistList.remove(position);
        checklistAdapter.notifyItemRemoved(position);

        /*completeTask = (CheckBox)findViewById(R.id.dot_checklist) ;

        completeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(completeTask.isChecked()){
                    completeTask.setVisibility(View.INVISIBLE);
                    Toast.makeText(Checklist.this, "Task Completed!", Toast.LENGTH_LONG).show();
                    db.deleteTask(checklistList.get(position));
                    checklistList.remove(position);
                    checklistAdapter.notifyItemRemoved(position);
                }
            }
        });*/

        toggleEmptyTask();
    }

    private void showTaskDialog(final boolean shouldUpdate, final model.Checklist task, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.checklist_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Checklist.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputTask= view.findViewById(R.id.task);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_task_title) : getString(R.string.lbl_edit_task_title));

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
                    Toast.makeText(Checklist.this, "Enter task!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                // check if user updating task
                if (shouldUpdate && task != null) {
                    // update task by it's id
                    updateTask(inputTask.getText().toString(), position);
                } else {
                    // create new task
                    createTask(inputTask.getText().toString());
                }
            }
        });
    }


    private void toggleEmptyTask() {
        // you can check notesList.size() > 0

        if (db.getTaskCount() > 0) {
            noTaskView.setVisibility(View.GONE);
        } else {
            noTaskView.setVisibility(View.VISIBLE);
        }
    }


    private void setUpBottomNavbar(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomnavbar);
        bottomNavBarHelper.enbaleNav(Checklist.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}


