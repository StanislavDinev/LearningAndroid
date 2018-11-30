package com.example.stanislavdinev.todo_list;

import android.app.Application;

import com.example.stanislavdinev.todo_list.data.DataManager;
import com.example.stanislavdinev.todo_list.data.DataManagerContract;

public class TodoApp extends Application {
    private DataManagerContract dataManager;
    private static TodoApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        dataManager = DataManager.getInstance();
        instance = this;
    }

    public DataManagerContract getDataManager() {
        return dataManager;
    }

    public static TodoApp getInstance() {
        return instance;
    }
}
