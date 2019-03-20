package com.example.stanislavdinev.task_list.data.sqLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stanislavdinev.task_list.data.DataManagerContract;
import com.example.stanislavdinev.task_list.data.Task;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper implements DataManagerContract {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbTask";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseManager.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldDatabase, int newDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseManager.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

    @Override
    public void add(final Task task) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase sqLiteDatabase = DatabaseHelper.this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseManager.COLUMN_TITLE, task.getTitle());
                contentValues.put(DatabaseManager.COLUMN_DESCRIPTION, task.getDescription());
                contentValues.put(DatabaseManager.COLUMN_DATE, task.getDate());
                sqLiteDatabase.insert(DatabaseManager.TABLE_NAME, null, contentValues);
                sqLiteDatabase.close();
            }
        }).start();
    }

    @Override
    public void delete(final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                SQLiteDatabase sqLiteDatabase = DatabaseHelper.this.getWritableDatabase();
                sqLiteDatabase.delete(DatabaseManager.TABLE_NAME, DatabaseManager.COLUMN_ID + " = ?",
                        new String[]{String.valueOf(id)});
                sqLiteDatabase.close();
            }
        }).start();
    }

    @Override
    public void edit(final Task task) {
        new Thread(new Runnable() {
            @Override
            public void run() {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseManager.COLUMN_TITLE, task.getTitle());
        contentValues.put(DatabaseManager.COLUMN_DESCRIPTION, task.getDescription());
        contentValues.put(DatabaseManager.COLUMN_DATE, task.getDate());
        sqLiteDatabase.update(DatabaseManager.TABLE_NAME, contentValues, DatabaseManager.COLUMN_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        sqLiteDatabase.close();
            }
        }).start();
    }

    @Override
    public Task getTaskById(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseManager.TABLE_NAME,
                new String[]{DatabaseManager.COLUMN_ID, DatabaseManager.COLUMN_TITLE, DatabaseManager.COLUMN_DESCRIPTION,
                        DatabaseManager.COLUMN_DATE}, DatabaseManager.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Task task = new Task();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                task = new Task(
                        cursor.getColumnIndex(DatabaseManager.COLUMN_ID),
                        cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_TITLE)),
                        cursor.getLong(cursor.getColumnIndex(DatabaseManager.COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_DESCRIPTION)));
                cursor.close();
            }
        }
        sqLiteDatabase.close();
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasksList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseManager.TABLE_NAME + " ORDER BY " + DatabaseManager.COLUMN_ID + " DESC";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(DatabaseManager.COLUMN_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_DESCRIPTION)));
                task.setDate(cursor.getLong(cursor.getColumnIndex(DatabaseManager.COLUMN_DATE)));
                tasksList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return tasksList;
    }
}