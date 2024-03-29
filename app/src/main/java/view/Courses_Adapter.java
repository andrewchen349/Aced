package view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andre.aced.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Course;
import model.Note;


public class Courses_Adapter extends RecyclerView.Adapter<Courses_Adapter.MyViewHolder>{

    private Context context;
    private List<Course>courseList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView courseName;
        public TextView teacherName;
        public TextView timeview;
        public TextView location_display;

        public MyViewHolder(View view) {
            super(view);
            courseName= view.findViewById(R.id.coursetitle);
            teacherName = view.findViewById(R.id.profeesorname);
            timeview = view.findViewById(R.id.date);
            location_display = view.findViewById(R.id.locationdisplay);
        }
    }


    public Courses_Adapter(Context context, List<Course> coursesList) {
        this.context = context;
        this.courseList = coursesList;
    }


    @NonNull
    @Override
    public Courses_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_does, parent, false);

        return new Courses_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Courses_Adapter.MyViewHolder holder, int position) {

        Course course = courseList.get(position);


        //TODO: Implement getters in Course model class
        holder.courseName.setText(course.getCourseName());
        holder.teacherName.setText((course.getProffesorName()));
        holder.location_display.setText(course.getLocation());
        holder.timeview.setText(formatTime(course));


        //Call to String Methods
        //holder.timeview.setText(formatDays());

        // Displaying dot from HTML character code
        //holder.dot.setText(Html.fromHtml("&#8226;"));

        // Formatting and displaying timestamp
        //holder.timestamp.setText(formatDate(note.getTimestamp()));

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    //TODO
    public String formatTime(Course course){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, course.getHour());
        calendar.set(Calendar.MINUTE, course.getMinute());

        /* if(calendar.get(Calendar.AM_PM) == 0){
            calendar.set(Calendar.AM_PM, 1);
        }

        else{
            calendar.set(Calendar.AM_PM, 0);
        }*/

        Date d1 =calendar.getTime();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, course.getHourEnd());
        cal.set(Calendar.MINUTE, course.getMinuteEnd());

        if(cal.get(Calendar.AM_PM) == 0){
            cal.set(Calendar.AM_PM, 1);
        }

        else{
            cal.set(Calendar.AM_PM, 0);
        }

        Date d2 = cal.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");

        //String s = course.getHour() + ":" + course.getMinute() + " - " + course.getHourEnd() + ":" + course.getMinuteEnd();

        String s = simpleDateFormat.format(d1) + " - " + simpleDateFormat.format(d2);
        return  s;


    }
}
