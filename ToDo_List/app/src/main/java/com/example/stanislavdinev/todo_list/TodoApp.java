package com.example.stanislavdinev.todo_list;

import android.app.Application;

import com.example.stanislavdinev.todo_list.data.DataManager;
import com.example.stanislavdinev.todo_list.data.DataManagerContract;
import com.example.stanislavdinev.todo_list.data.sqLite.DatabaseHelper;
import com.example.stanislavdinev.todo_list.data.sqLite.DatabaseManager;

public class TodoApp extends Application {
    private DataManagerContract dataManager;
    private static TodoApp instance;
    public void onCreate() {
        super.onCreate();
        dataManager = new DatabaseHelper(getApplicationContext());
        instance = this;
    }

    public DataManagerContract getDataManager() {
        return dataManager;
    }

    public static TodoApp getInstance() {
        return instance;
    }
}
