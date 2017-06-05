package com.example.hnle_pset5;

import java.io.Serializable;

public class Task implements Serializable {
    private String task_name;
    private String task_status;

    // Constructor for Tasks from database
    public Task (String task, String status){
        task_name = task;
        task_status = status;
    }

    // Get Task name
    public String getTask_name(){return task_name;}

    // Get Task status
    public String getTask_status() {return task_status;}

    // Turn tasks into strings
    @Override
    public String toString(){return task_name;}

    // Set Task status
    public void setTask_status (String newStatus) {task_status = newStatus;}




}
