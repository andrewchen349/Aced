package model;

public class Checklist {

    public static final String TABLE_NAME1 = "checklist";

    public static final String COLUMN_ID1 = "id";
    public static final String COLUMN_TASK2 = "task1";
    public static final String COLUMN_TIMESTAMP2 = "timestamp";

    private int id;
    private String task;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE1 =
            "CREATE TABLE " + TABLE_NAME1 + "("
                    + COLUMN_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TASK2 + " TEXT,"
                    + COLUMN_TIMESTAMP2 + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Checklist() {
    }

    public Checklist(int id, String task, String timestamp) {
        this.id = id;
        this.task = task;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
