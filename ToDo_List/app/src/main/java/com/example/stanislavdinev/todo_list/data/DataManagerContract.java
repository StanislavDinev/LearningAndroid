package com.example.stanislavdinev.todo_list.data;

import java.util.List;

public interface DataManagerContract {
    void add(ToDoElement toDoElement);
    void delete(int id);
    void edit(ToDoElement toDoElement);
    ToDoElement getItemById(int id);
    List<ToDoElement> getAllToDos();
}