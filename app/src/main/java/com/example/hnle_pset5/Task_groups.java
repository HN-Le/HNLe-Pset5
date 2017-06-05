package com.example.hnle_pset5;

import java.io.Serializable;
import java.util.ArrayList;

public class Task_groups implements Serializable {

    // Title of task group
    private String group_title;

    // List of all the task items in a specific group
    private ArrayList<Task> group_items;

    // Constructor
    public Task_groups (String title){

        group_title = title;

    }

    // Return title of task group
    public String getGroup_title(){ return  group_title; }

    // Return list of task items
    public ArrayList<Task> getGroup_items() { return group_items; }



    // Check if everything is done in the list
    public boolean check(){

        // Loop through all the task items and check if completed or not
        for (Task item : group_items){
            if (item.getTask_status().equals("TODO")){
                return false;
            }
        }

        return true;
    }

}
