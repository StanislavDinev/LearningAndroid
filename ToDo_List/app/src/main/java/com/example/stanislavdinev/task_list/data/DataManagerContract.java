package com.example.stanislavdinev.todo_list.data;

import java.util.List;

public interface DataManagerContract {
    void add(Task task);
    void delete(int id);
    void edit(Task task);
    Task getItemById(int id);
    List<Task> getAllToDos();
}