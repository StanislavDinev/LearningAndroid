package com.example.stanislavdinev.task_list.data;

import java.util.List;

public interface DataManagerContract {
    void add(Task task);
    void delete(int id);
    void edit(Task task);
    Task getTaskById(int id);
    List<Task> getAllTasks();
}