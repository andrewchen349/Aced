package view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.andre.aced.R;

import java.util.List;

import model.Checklist;
import model.Events;

public class Calendar_Task_Adapter extends RecyclerView.Adapter<Calendar_Task_Adapter.MyViewHolder2> {


    //Constants and Fields
    private List<Events> calendar_event_list;
    private Context context;

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        public TextView event;
        public TextView event_dot;

        public MyViewHolder2(View view) {
            super(view);
            event = view.findViewById(R.id.calendar_event);
            event_dot = view.findViewById(R.id.dot_event);
            }
    }

    //Constructor
    public Calendar_Task_Adapter(List <Events> eventList, Context context ){
        this.context = context;
        this.calendar_event_list = eventList;
    }


    @Override
    public Calendar_Task_Adapter.MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_even_row, parent, false);

        return new Calendar_Task_Adapter.MyViewHolder2(itemView);
    }


    //Reloads old rows with new data functionality of onBindViewHolder
    @Override
    public void onBindViewHolder(final Calendar_Task_Adapter.MyViewHolder2 holder, int position) {

        Events event  = calendar_event_list.get(position);
        holder.event.setText(event.getEvent());

        //Set Dot
        holder.event_dot.setText(Html.fromHtml("&#8226;"));

    }

    @Override
    public int getItemCount() {
        return calendar_event_list.size();
    }
}
