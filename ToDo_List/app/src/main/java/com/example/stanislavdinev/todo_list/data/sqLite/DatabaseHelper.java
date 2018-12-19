package com.example.stanislavdinev.todo_list.data.sqLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stanislavdinev.todo_list.data.DataManagerContract;
import com.example.stanislavdinev.todo_list.data.ToDoElement;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper implements DataManagerContract {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbTODO";


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
    public void add(ToDoElement toDoElement) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseManager.COLUMN_TITLE, toDoElement.getTitle());
        contentValues.put(DatabaseManager.COLUMN_DESCRIPTION, toDoElement.getDescription());
        contentValues.put(DatabaseManager.COLUMN_DATE, toDoElement.getDate());
        sqLiteDatabase.insert(DatabaseManager.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    @Override
    public void delete(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(DatabaseManager.TABLE_NAME, DatabaseManager.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    @Override
    public void edit(ToDoElement toDoElement) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseManager.COLUMN_TITLE, toDoElement.getTitle());
        contentValues.put(DatabaseManager.COLUMN_DESCRIPTION, toDoElement.getDescription());
        contentValues.put(DatabaseManager.COLUMN_DATE, toDoElement.getDate());
        sqLiteDatabase.update(DatabaseManager.TABLE_NAME, contentValues, DatabaseManager.COLUMN_ID + " = ?",
                new String[]{String.valueOf(toDoElement.getId())});
        sqLiteDatabase.close();
    }

    @Override
    public ToDoElement getItemById(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseManager.TABLE_NAME,
                new String[]{DatabaseManager.COLUMN_ID, DatabaseManager.COLUMN_TITLE, DatabaseManager.COLUMN_DESCRIPTION,
                        DatabaseManager.COLUMN_DATE}, DatabaseManager.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        ToDoElement toDoElement = new ToDoElement();
        if (cursor != null) {
            if(cursor.moveToFirst()) {

                toDoElement = new ToDoElement(
                        cursor.getColumnIndex(DatabaseManager.COLUMN_ID),
                        cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_TITLE)),
                        cursor.getLong(cursor.getColumnIndex(DatabaseManager.COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_DESCRIPTION)));
                cursor.close();
            }
        }
        sqLiteDatabase.close();
        return toDoElement;


//            ToDoElement toDoElement = new ToDoElement();
//            SQLiteDatabase db = this.getReadableDatabase();
//            //specify the columns to be fetched
//            String[] columns = {DatabaseManager.COLUMN_ID, DatabaseManager.COLUMN_TITLE,
//                    DatabaseManager.COLUMN_DATE, DatabaseManager.COLUMN_DESCRIPTION};
//            //Select condition
//            String selection = DatabaseManager.COLUMN_ID + " = ?";
//            //Arguments for selection
//            String[] selectionArgs = {String.valueOf(id)};
//
//
//            Cursor cursor = db.query(DatabaseManager.TABLE_NAME, columns, selection,
//                    selectionArgs, null, null, null);
//            if (cursor != null) {
//                cursor.moveToFirst();
//                toDoElement.setId(cursor.getInt(0));
//                toDoElement.setTitle(cursor.getString(1));
//                toDoElement.setDate(cursor.getLong(2));
//                toDoElement.setDescription(cursor.getString(3));
//            }
//            db.close();
//            cursor.close();
//            return toDoElement;
    }

    @Override
    public List<ToDoElement> getAllToDos() {
        List<ToDoElement> listOfToDos = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseManager.TABLE_NAME + " ORDER BY " + DatabaseManager.COLUMN_ID + " DESC";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ToDoElement toDoElement = new ToDoElement();
                toDoElement.setId(cursor.getInt(cursor.getColumnIndex(DatabaseManager.COLUMN_ID)));
                toDoElement.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_TITLE)));
                toDoElement.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_DESCRIPTION)));
                toDoElement.setDate(cursor.getLong(cursor.getColumnIndex(DatabaseManager.COLUMN_DATE)));
                listOfToDos.add(toDoElement);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return listOfToDos;
    }
}