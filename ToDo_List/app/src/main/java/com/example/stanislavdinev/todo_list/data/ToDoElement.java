package com.example.stanislavdinev.todo_list.data;



public class ToDoElement {
    // TODO introduce field id
    private int id;
    private String title;
    private long date;
    private String description;

    // TODO the date should be long
    public ToDoElement(int id, String title, long date, String description) {
        this.id = id;
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