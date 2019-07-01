package view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.andre.aced.R;

import java.util.List;

import model.Checklist;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.MyViewHolder> {

    //Constants and Fields
    private List<Checklist> taskList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView note;
        public TextView dot;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            note = view.findViewById(R.id.task);
            dot = view.findViewById(R.id.dot_checklist);
            timestamp = view.findViewById(R.id.timestamp_checklist);
        }
    }

    //Constructor
    public ChecklistAdapter(List <Checklist> taskList, Context context ){
        this.context = context;
        this.taskList = taskList;
    }

}
