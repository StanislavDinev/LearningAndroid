package com.example.stanislavdinev.todo_list.data;

import java.util.ArrayList;
import java.util.List;

public class DataManager implements DataManagerContract {
    private static List<ToDoElement> myList;
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
    public void add(ToDoElement toDoElement) {
        myList.add(toDoElement);
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
    public void edit(ToDoElement toDoElement) {
        for (int i = 0; i < myList.size(); i++) {
            if (toDoElement.getId() == myList.get(i).getId()) {
                myList.set(i, toDoElement);
            }
        }
    }

    @Override
    public ToDoElement getItemById(int id) {
        for (int i = 0; i < myList.size(); i++) {
            if (myList.get(i).getId() == id) {
                return myList.get(i);
            }
        }
        return null;
    }

    @Override
    public List<ToDoElement> getAllToDos() {
        return myList;
    }
}