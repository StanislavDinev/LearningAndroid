//package com.example.stanislavdinev.task_list.data;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DataManager implements TaskDataManagerContract {
//    private static List<Task> taskList;
//    private static DataManager instance = null;
//
//    private DataManager() {
//        taskList = new ArrayList<>();
//    }
//
//    public static DataManager getInstance() {
//        if (instance == null) {
//            instance = new DataManager();
//        }
//        return instance;
//    }
//
//    @Override
//    public void add(Task task) {
//        taskList.add(task);
//    }
//
//    @Override
//    public void delete(int id) {
//        for (int i = 0; i < taskList.size(); i++) {
//            if (taskList.get(i).getId() == id) {
//                taskList.remove(i);
//            }
//        }
//    }
//
//    @Override
//    public void edit(Task task) {
//        for (int i = 0; i < taskList.size(); i++) {
//            if (task.getId() == taskList.get(i).getId()) {
//                taskList.set(i, task);
//            }
//        }
//    }
//
//    @Override
//    public Task getTaskById(int id) {
//        for (int i = 0; i < taskList.size(); i++) {
//            if (taskList.get(i).getId() == id) {
//                return taskList.get(i);
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public List<Task> getAllTasks() {
//        return taskList;
//    }
//}