package view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andre.aced.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import data.DatabaseHelper;
import model.Checklist;


public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.MyViewHolder> {

    //Constants and Fields
    private List<Checklist> taskList;
    private Context context;
    private DatabaseHelper db;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView task;
        public CheckBox checkBox;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.task_row);
            checkBox = view.findViewById(R.id.dot_checklist);

            timestamp = view.findViewById(R.id.timestamp_checklist);
            db = new DatabaseHelper(context);
        }
    }

    //Constructor
    public ChecklistAdapter(List <Checklist> taskList, Context context ){
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public ChecklistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checklist_list_row, parent, false);

        return new ChecklistAdapter.MyViewHolder(itemView);
    }

    //Reloads old rows with new data functionality of onBindViewHolder
    @Override
    public void onBindViewHolder(final ChecklistAdapter.MyViewHolder holder, int position) {
        final Checklist task = taskList.get(position);

        holder.task.setText(task.getTask());


        // Displaying dot from HTML character code
        //holder.dot.setText(Html.fromHtml("&#8226;"));
        //holder.checkBox.setChecked(false);
        /*holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()){
                    Toast.makeText(context, "Task Completed!", Toast.LENGTH_LONG).show();
                    db.deleteTask(task);

                }
                else{
                    //TODO
                }
            }
        });*/


        // Formatting and displaying timestamp
        holder.timestamp.setText(formatDate(task.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }

}
