package com.example.stanislavdinev.task_list.data.sqLite;

public class DatabaseManager {
    public static final String TABLE_NAME = "Tasks";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";

    private int id;


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + ", " +
                    COLUMN_DESCRIPTION + ", " +
                    COLUMN_DATE +
                    ")";

    public DatabaseManager() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}