package com.example.stanislavdinev.task_list.ViewContracts;

import com.example.stanislavdinev.task_list.data.Task;

import java.util.List;

public interface TaskListView {
    void setTasks(List<Task> taskList);
    void showEmptyLayout();
}