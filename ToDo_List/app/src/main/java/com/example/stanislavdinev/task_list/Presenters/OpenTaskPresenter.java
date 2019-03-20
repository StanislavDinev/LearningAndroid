package com.example.stanislavdinev.task_list.Presenters;


import com.example.stanislavdinev.task_list.ViewContracts.OpenTaskView;
import com.example.stanislavdinev.task_list.data.DataManagerContract;

public class OpenTaskPresenter {
    private OpenTaskView view;
    private DataManagerContract dataManager;

    public OpenTaskPresenter(OpenTaskView view, DataManagerContract dataManager) {
        this.view = view;
        this.dataManager = dataManager;
    }

    public void setButtons(int id) {
       dataManager.getTaskById(id);
       if(id>0) {
           view.onEdit();
       } else {
           view.onAdd();
       }
    }

    public void deletingTask(int id) {
        dataManager.delete(id);
    }

    public void setupEditText(int id) {
        if(id>=0) {
            view.setupEditText(dataManager.getTaskById(id));
        }
    }
}