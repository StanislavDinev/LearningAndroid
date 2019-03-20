package com.example.stanislavdinev.task_list;

import android.app.Application;

import com.example.stanislavdinev.task_list.data.DataManagerContract;
import com.example.stanislavdinev.task_list.data.sqLite.DatabaseHelper;

public class TaskApp extends Application {
    private DataManagerContract dataManager;
    private static TaskApp instance;
    public void onCreate() {
        super.onCreate();
        dataManager = new DatabaseHelper(getApplicationContext());
        instance = this;
    }

    public DataManagerContract getDataManager() {
        return dataManager;
    }

    public static TaskApp getInstance() {
        return instance;
    }
}
