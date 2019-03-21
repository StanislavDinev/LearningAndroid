package com.example.stanislavdinev.task_list;

import android.app.Application;

import com.example.stanislavdinev.task_list.data.TaskDataManagerContract;
import com.example.stanislavdinev.task_list.data.sqLite.DatabaseHelper;

public class TaskApp extends Application {
    private TaskDataManagerContract taskDataManager;
    private static TaskApp instance;
    public void onCreate() {
        super.onCreate();
        taskDataManager = new DatabaseHelper(getApplicationContext());
        instance = this;
    }

    public TaskDataManagerContract getTaskDataManager() {
        return taskDataManager;
    }

    public static TaskApp getInstance() {
        return instance;
    }
}
