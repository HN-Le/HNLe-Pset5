package com.example.hnle_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // Track and save instance of DBHelper
    private static DBHelper sInstance;

    // Static strings
    private static final String DATABASE_NAME = "TaskDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE = "task_table";
    private static final String KEY_ID = "task_id";
    private static final String KEY_NAME = "task_name";
    private static final String KEY_STATUS = "task_status";
    private static final String KEY_GROUP_ID = "task_group_id";

    private static final String GROUP_ID = "group_id";
    private static final String GROUP = "task_group";
    private static final String TABLE_GROUP = "group_table";

    // Retrieve current instance of singleton, otherwise create new one
    public static synchronized DBHelper getsInstance(Context context){

        if (sInstance == null){
            sInstance = new DBHelper(context.getApplicationContext());
        }

        return sInstance;
    }

    // Constructor
    private DBHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        Log.i("Tag", "ONCREATE");

        // Table for tasks
        String CREATE_DB = "CREATE TABLE " + TABLE +
                "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT," + KEY_STATUS + " TEXT " + ", "+ KEY_GROUP_ID + ")";

        // Table for groups
        String CREATE_DB2 = "CREATE TABLE " + TABLE_GROUP +
                "(" + GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GROUP + " TEXT NOT NULL)";

        db.execSQL(CREATE_DB);
        db.execSQL(CREATE_DB2);

        Log.i("Tag", CREATE_DB);

    }

    // Upgrade database when helper object is made and there is one already
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public void create(String task_name, String task_status, String task_group_id) {
        Log.i("Tag", "CREATE");

        SQLiteDatabase db = getWritableDatabase();
        onUpgrade(db, 1, 1);
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task_name);
        values.put(KEY_STATUS, task_status);
        values.put(KEY_GROUP_ID, task_group_id);
        db.insert(TABLE, null, values);
        db.close();
    }

    public void create_group(String task_group) {
        Log.i("Tag", "CREATE_GROUP");

        SQLiteDatabase db = getWritableDatabase();
        onUpgrade(db, 1, 1);
        ContentValues values = new ContentValues();
        values.put(GROUP, task_group);
        db.insert(TABLE_GROUP, null, values);
        db.close();
    }

    public ArrayList<Task> read(String group){

        SQLiteDatabase db = getReadableDatabase();

        // List of custom objects to store data
        ArrayList<Task> tasks = new ArrayList<>();

        // Create query to give to the cursor
        String query = "SELECT " + KEY_NAME + ", " + KEY_STATUS + " FROM " + TABLE + " WHERE " + KEY_GROUP_ID + " = "  + group;

        Cursor cursor = db.rawQuery(query, null);

        // Set cursor to the beginning of the database
        if (cursor.moveToFirst()) {
            do {
                // add id, done-status and to-do from current row to TodoList
                String task_name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                String task_status = cursor.getString(cursor.getColumnIndex(KEY_STATUS));
                String group_id = cursor.getString(cursor.getColumnIndex(KEY_GROUP_ID));

                // Create contact object with the retrieved data
                Task task = new Task(task_name, task_status, group_id);
                tasks.add(task);
            }

            // While there is still a next entry
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return tasks;
    }

    public ArrayList<Task_groups> read_group() {

        Log.i("Tag", "READ_GROUP");

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<Task_groups> groups = new ArrayList<>();

        String query = "SELECT " + GROUP + ", " + GROUP_ID + " FROM " + TABLE_GROUP;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // add id, done-status and to-do from current row to TodoList
                String task_name = cursor.getString(cursor.getColumnIndex(GROUP));
                String group_id = cursor.getString(cursor.getColumnIndex(GROUP_ID));

                // Create contact object with the retrieved data
                Task_groups group = new Task_groups(task_name, group_id);
                groups.add(group);

            }

            // While there is still a next entry
            while (cursor.moveToNext());
        }

        // Close database and cursor
        cursor.close();
        db.close();

        return groups;
    }

    public void update(Task task){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getTask_name());
        values.put(KEY_STATUS, task.getTask_status());

        db.update(TABLE, values, KEY_NAME + " = ? ", new String[] {task.getTask_name()});
    }



    public void delete(Task task){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, KEY_NAME + " = ? ", new String[] {task.getTask_name()});
        db.close();
    }







}





