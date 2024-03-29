package view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.andre.aced.R;

import org.w3c.dom.Text;

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
        public TextView time_date;

        private TextView itemtitle;
        private TextView itemdesc;
        private CardView cardView;
        private TextView time;

        public MyViewHolder2(View view) {
            super(view);
            //event = view.findViewById(R.id.calendar_event);
            event_dot = view.findViewById(R.id.dot);
            //time_date = view.findViewById(R.id.timestamp_calendar);

            itemtitle = view.findViewById(R.id.item_title);
            itemdesc = view.findViewById(R.id.item_detail);
            cardView = view.findViewById(R.id.card_view);
            time = view.findViewById(R.id.item_time);
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
                .inflate(R.layout.card_layout_event, parent, false);

        return new Calendar_Task_Adapter.MyViewHolder2(itemView);
    }


    //Reloads old rows with new data functionality of onBindViewHolder
    @Override
    public void onBindViewHolder(final Calendar_Task_Adapter.MyViewHolder2 holder, int position) {

        Events event  = calendar_event_list.get(position);
        //holder.event.setText(event.getEvent());

        //Set Dot
        holder.event_dot.setText(Html.fromHtml("&#8226;"));


        if(event.getHour() == 0 && event.getMinute() == 0) {
            holder.time.setText("All Day");
        }

        else{
            holder.time.setText(event.getHour() + ":" + event.getMinute());
        }

        //set Date
       // holder.time_date.setText(convertMonth(event.get_later_calendar_month()) + " " + event.get_later_calendar_day());

        holder.itemdesc.setText(event.getEvent());
        holder.itemtitle.setText(convertMonth(event.get_later_calendar_month()) + " " + event.get_later_calendar_day());

    }

    @Override
    public int getItemCount() {
        return calendar_event_list.size();
    }

    public String convertMonth(int m){

        m =  m + 1;

        if(m == 1){
            return "January";
        }

        if(m == 2){
            return "February";
        }

        if(m == 3){
            return "March";
        }

        if(m == 4){
            return "April";
        }

        if(m == 5){
            return "May";
        }

        if(m == 6){
            return "June";
        }

        if(m == 7){
            return "July";
        }
        if(m == 8){
            return "August";
        }

        if(m== 9){
            return "September";
        }
        if(m == 10){
            return "October";
        }

        if(m == 11){
            return "November";
        }
        else{
            return "December";
        }
    }

}
