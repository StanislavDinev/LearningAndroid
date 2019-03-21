package com.example.stanislavdinev.task_list.Presenters;


import com.example.stanislavdinev.task_list.R;
import com.example.stanislavdinev.task_list.TaskApp;
import com.example.stanislavdinev.task_list.ViewContracts.OpenTaskView;
import com.example.stanislavdinev.task_list.data.Task;
import com.example.stanislavdinev.task_list.data.TaskDataManagerContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class OpenTaskPresenter {
    private OpenTaskView view;
    private TaskDataManagerContract taskDataManager;

    public OpenTaskPresenter(OpenTaskView view, TaskDataManagerContract taskDataManager) {
        this.view = view;
        this.taskDataManager = taskDataManager;
    }


    public void deletingTask(int id) {
        taskDataManager.delete(id);
    }

    public void setupEditText(int id) {
        if (isEditMode(id)) {
            view.setupEditText(taskDataManager.getTaskById(id));
        }
    }

    public void onButtonClick(int id, String taskName, String taskDescription, String taskDate) {
        long dateInMilliS = parseDate(taskDate);
        if (taskName.equals("")) {
            showMessage(R.string.no_title);
        } else {
            if (isEditMode(id)) {
                Task task = taskDataManager.getTaskById(id);
                task.setId(id);
                task.setTitle(taskName);
                task.setDescription(taskDescription);
                task.setDate(dateInMilliS);
                TaskApp.getInstance().getTaskDataManager().edit(task);
                showMessage(R.string.save_message);
            } else {
                Task task = new Task(taskName, dateInMilliS, taskDescription);
                taskDataManager.add(task);
            }
        }
    }

    public long parseDate(String taskDate) {
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        long dateInMilliS = 0;
        try {
            Date d = f.parse(taskDate);
            dateInMilliS = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateInMilliS;
    }


    public boolean isEditMode(int id) {
        return id >= 0;
    }

    private void showMessage(int message) {
        view.showMessage(message);
    }
}