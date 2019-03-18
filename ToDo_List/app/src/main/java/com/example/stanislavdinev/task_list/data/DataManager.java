package com.example.stanislavdinev.todo_list.data;

import java.util.ArrayList;
import java.util.List;

public class DataManager implements DataManagerContract {
    private static List<Task> myList;
    private static DataManager instance = null;

    private DataManager() {
        myList = new ArrayList<>();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    @Override
    public void add(Task task) {
        myList.add(task);
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < myList.size(); i++) {
            if (myList.get(i).getId() == id) {
                myList.remove(i);
            }
        }
    }

    @Override
    public void edit(Task task) {
        for (int i = 0; i < myList.size(); i++) {
            if (task.getId() == myList.get(i).getId()) {
                myList.set(i, task);
            }
        }
    }

    @Override
    public Task getItemById(int id) {
        for (int i = 0; i < myList.size(); i++) {
            if (myList.get(i).getId() == id) {
                return myList.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Task> getAllToDos() {
        return myList;
    }
}