package model;

public class Events {

    public static final String TABLE_NAME2 = "Calendar_Events";
    public static final String COLUMN_ID2 = "event_id";
    public static final String COLUMN_EVENTS = "events";

    private int id;
    private String event;

    // Create table SQL query
    public static final String CREATE_TABLE2 =
            "CREATE TABLE " + TABLE_NAME2 + "("
                    + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_EVENTS + " TEXT"
                    + ")";

    //Empty Constructor
    public Events()
    {

    }

    //Constructor
   public Events(int id, String event){

        this.id = id;
        this.event = event;
   }

   //Getters and Setters

    public int getId(){
        return this.id;
    }

    public String getEvent(){
        return this.event;
    }

    public void setId(int id ){
        this.id = id;
    }

    public void setEvent(String event){
        this.event = event;
    }


}
