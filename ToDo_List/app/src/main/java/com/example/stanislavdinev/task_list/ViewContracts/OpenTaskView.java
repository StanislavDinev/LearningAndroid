package com.example.stanislavdinev.task_list.ViewContracts;

import com.example.stanislavdinev.task_list.data.Task;

public interface OpenTaskView {
    void setupEditText(Task task);
    void onEdit();
    void onAdd();
    void onDelete();
}