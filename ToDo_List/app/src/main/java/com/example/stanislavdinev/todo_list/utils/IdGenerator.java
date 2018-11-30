package com.example.stanislavdinev.todo_list.utils;


public class IdGenerator {
    private static int id = 0;

    // TODO implement me
    public static void incrementID() {
        id += 1;
    }

    public static int getId() {
        return id;
    }
}

