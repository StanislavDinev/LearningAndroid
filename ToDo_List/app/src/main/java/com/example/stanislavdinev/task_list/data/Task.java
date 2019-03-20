package com.example.stanislavdinev.task_list.data;



public class Task {
    private int id;
    private String title;
    private long date;
    private String description;

    public Task(int id, String title, long date, String description) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public Task() {
    }

    public Task(String title, long date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}