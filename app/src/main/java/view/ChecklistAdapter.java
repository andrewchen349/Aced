package view;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
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
import java.util.Locale;
import java.util.Random;

import model.Checklist;


public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.MyViewHolder1> {

    //Constants and Fields
    private List<Checklist> taskList;
    private Context context;

    private int [] colors = {Color.parseColor("#5AC9DD"),Color.parseColor("#FF971A"), Color.parseColor("#FF6961")
    , Color.parseColor("#E2A7D9")};
    private int colorCode;


    public class MyViewHolder1 extends RecyclerView.ViewHolder {
        public TextView task;
        public CheckBox checkBox;
        public TextView timestamp;
        private TextView itemtitle;
        private TextView itemdesc;
        private CardView cardView;


        public MyViewHolder1(View view) {
            super(view);
            //task = view.findViewById(R.id.task_row);
            //checkBox = view.findViewById(R.id.dot_checklist);

            //timestamp = view.findViewById(R.id.timestamp_checklist);

            itemtitle = view.findViewById(R.id.item_title);
            itemdesc = view.findViewById(R.id.item_detail);
            cardView = view.findViewById(R.id.card_view);


        }
    }

    //Constructor
    public ChecklistAdapter(List <Checklist> taskList, Context context ){
        this.context = context;
        this.taskList = taskList;
    }


    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checklist_list_row, parent, false);*/

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        return new MyViewHolder1(itemView);
    }
    //Reloads old rows with new data functionality of onBindViewHolder
    @Override
    public void onBindViewHolder(final MyViewHolder1 holder, int position) {
        //final Checklist task = taskList.get(position);

        //holder.task.setText(task.getTask());


        // Formatting and displaying timestamp
        //holder.timestamp.setText(formatDate(task.getTimestamp()));



        final Checklist task = taskList.get(position);

        if(task.getHour() == 0 && task.getMinute() == 0){
            holder.cardView.setCardBackgroundColor(random_color());
            holder.itemtitle.setText(task.getTask());
            //holder.itemdesc.setText("All Day");
        }
        else {
            holder.cardView.setCardBackgroundColor(random_color());
            holder.itemtitle.setText(task.getTask());
            //holder.itemdesc.setText(formatTime(task.getMinute(), task.getHour()));
        }


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

    private int random_color(){
        for(int i = 0; i < colors.length; i ++){
            int rnd = new Random().nextInt(colors.length);
            colorCode = colors[rnd];

        }

        return colorCode;
    }

    private String formatTime(int min, int hr){
        int m = min;
        int hour = hr;

        if(hour > 12){
            hour = hr - 12;
            String result = (hour + ":" + m);
            return result;
        }

        if(m == 0){
            String result = hour + ":" + m + "0";
            return result;
        }
        else {
            String result = (hour + ":" + m);
            return result;
        }
    }




}
