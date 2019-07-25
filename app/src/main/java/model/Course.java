package model;

public class Course {

    //Create Table Name
    public static final String TABLE_NAME3 = "Courses";

    //Columns Names
    public static final String COLUMN_ID3 = "idCourses";
    public static final String COLUMN_COURSENAME = "coursename";
    public static final String COLUMN_TEACHERNAME = "teachername";
    public static final String COLUMN_MONDAY = "monday";
    public static final String COLUMN_TUESDAY = "tuesday";
    public static final String COLUMN_WEDNESDAY = "wednesday";
    public static final String COLUMN_THURSDAY = "thursday";
    public static final String COLUMN_FRIDAY = "friday";
    public static final String COLUMN_LOCATIONCLASS = "location";
    public static final String COLUMN_PROFESSOREMAIL = "email";
    public static final String COLUMN_HOUR = "hour";
    public static final String COLUMN_MINUTE = "minute";

    //FIELDS
    private int id;
    private String course_name;
    private String teacher_name;
    private String local;
    private String teacheremail;
    private int Mon;
    private int Tues;
    private int Wed;
    private int Thurs;
    private int Fri;
    private int hour;
    private int minute;

    // Create table SQL query
    public static final String CREATE_TABLE3 =
            "CREATE TABLE " + TABLE_NAME3 + "("
                    + COLUMN_ID3 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_COURSENAME + " TEXT,"
                    + COLUMN_PROFESSOREMAIL + " EMAIL,"
                    + COLUMN_TEACHERNAME + " TEACHERNAME,"
                    + COLUMN_LOCATIONCLASS + " LOCATION,"
                    + COLUMN_MONDAY + " MONDAY,"
                    + COLUMN_TUESDAY + " TUESDAY,"
                    + COLUMN_WEDNESDAY + " WEDNESDAY,"
                    + COLUMN_THURSDAY + " THURSDAY,"
                    + COLUMN_FRIDAY + " FRIDAY,"
                    + COLUMN_HOUR + " HOUR,"
                    + COLUMN_MINUTE + " MINUTE"
                    + ")";

    //Constructors
    public Course() {
    }

    public Course(int id, String coursename, String profesorname, int mon, int tues, int wed, int thurs, int fri, String local, String email, int hr, int min) {
        this.course_name = coursename;
        this.teacher_name = profesorname;
        this.Mon = mon;
        this.Tues = tues;
        this.Wed = wed;
        this.Thurs = thurs;
        this.Fri = fri;
        this.id = id;
        this.local = local;
        this.teacheremail = email;
        this.hour = hr;
        this.minute = min;
    }


    //GETTERS and SETTERS
    public void setCourseName(String s){
        this.course_name = s;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setLocation(String s){
        this.local = s;
    }

    public void setEmail(String s){
        this.teacheremail= s;
    }

    public void setProfessorName(String s){
        this.teacher_name = s;
    }

    public void setHour(int s){
        this.hour = s;
    }

    public void setMinute(int s){
        this.minute = s;
    }

    public int getHour(){
        return this.hour;
    }

    public int getMinute(){
        return this.minute;
    }

    public int getId(){
        return this.id;
    }

    public String getEmail(){
        return this.teacheremail;
    }

    public String getLocation(){
        return this.local;
    }
    public String getCourseName(){
        return this.course_name;
    }

    public String getProffesorName(){
        return this.teacher_name;
    }

    public int getMon(){
        return this.Mon;
    }

    public int getTues(){
        return this.Tues;
    }

    public int getWed(){
        return this.Wed;
    }

    public int getThurs(){
        return this.Thurs;
    }

    public int getFri(){
        return this.Fri;
    }









}
