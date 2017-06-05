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
    private static final String KEY_ID = "task_id";
    private static final String KEY_NAME = "task_name";
    private static final String KEY_STATUS = "task_status";
    private static final String KEY_GROUP = "task_group";
    private static final String TABLE = "task_table";

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
        String CREATE_DB = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME
                + " TEXT," + KEY_STATUS + " TEXT " + " ," + KEY_GROUP + " TEXT)";

    Log.d("TAAAG", CREATE_DB);
        db.execSQL(CREATE_DB);
    }

    // Upgrade database when helper object is made and there is one already
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public void create(String task_name, String task_status) {
        SQLiteDatabase db = getWritableDatabase();
        onUpgrade(db, 1, 1);
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task_name);
        values.put(KEY_STATUS, task_status);
        db.insert(TABLE, null, values);
        db.close();
    }

    public void create_group(String task_group) {
        SQLiteDatabase db = getWritableDatabase();
        onUpgrade(db, 1, 1);
        ContentValues values = new ContentValues();
        values.put(KEY_GROUP, task_group);
        db.insert(TABLE, null, values);
        db.close();
    }

    public ArrayList<Task> read(){
        SQLiteDatabase db2 = getReadableDatabase();

        // List of custom objects to store data
        ArrayList<Task> tasks = new ArrayList<>();

        // Create query to give to the cursor
        String query = "SELECT " + KEY_NAME + ", " + KEY_STATUS + " FROM " + TABLE;
        Cursor cursor = db2.rawQuery(query, null);

        // Set cursor to the beginning of the database
        if (cursor.moveToFirst()) {
            do {
                // add id, done-status and to-do from current row to TodoList
                String task_name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                String task_status = cursor.getString(cursor.getColumnIndex(KEY_STATUS));

                // Create contact object with the retrieved data
                Task task = new Task(task_name, task_status);
                tasks.add(task);
            }

            // While there is still a next entry
            while (cursor.moveToNext());
        }

        // Close database and cursor
        cursor.close();
        db2.close();


        return tasks;
    }

    public ArrayList<Task_groups> read_group(){
        SQLiteDatabase db = getReadableDatabase();

        // List of custom objects to store data
        ArrayList<Task_groups> task_group = new ArrayList<>();

        // Create query to give to the cursor
        String query = "SELECT " + KEY_GROUP + " FROM " + TABLE;
        Cursor cursor = db.rawQuery(query, null);

        // Set cursor to the beginning of the database
        if (cursor.moveToFirst()) {
            do {
                // add id, done-status and to-do from current row to TodoList
                String task_group_list = cursor.getString(cursor.getColumnIndex(KEY_GROUP));

                // Create task object with the retrieved data
                Task_groups tasks_group = new Task_groups(task_group_list);
                task_group.add(tasks_group);
            }

            // While there is still a next entry
            while (cursor.moveToNext());
        }

        // Close database and cursor
        cursor.close();
        db.close();


        return task_group;
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





