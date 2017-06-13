package com.example.hnle_pset5;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;

public class Task implements Serializable {
    private String task_name;
    private String task_status;
    private String task_group;

    // Constructor for Tasks from database
    public Task (String task, String status, String group){
        task_name = task;
        task_status = status;
        task_group = group;
    }

    // Get Task name
    public String getTask_name(){return task_name;}

    // Get Task status
    public String getTask_status() {return task_status;}

    public String getTask_group() {return task_group;}

    // Turn tasks into strings
    @Override
    public String toString(){return task_name;}

    // Set Task status
    public void setTask_status (String newStatus) {task_status = newStatus;}

}
