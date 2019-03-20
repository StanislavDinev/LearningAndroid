package com.example.stanislavdinev.task_list.Presenters;

import com.example.stanislavdinev.task_list.ViewContracts.TaskListView;
import com.example.stanislavdinev.task_list.data.DataManagerContract;


public class TasksListPresenter {

    private TaskListView view;
    private DataManagerContract dataManager;

    public TasksListPresenter(TaskListView view, DataManagerContract dataManager) {
        this.view = view;
        this.dataManager = dataManager;
    }

    public void loadTasks() {
        if (dataManager.getAllTasks() != null && dataManager.getAllTasks().size() > 0) {
            view.setTasks(dataManager.getAllTasks());
        } else {
            view.showEmptyLayout();
        }
    }
}