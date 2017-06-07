package com.example.hnle_pset5;

import java.io.Serializable;

public class Task_groups implements Serializable {

    // Title of task group
    private String group_title;

    // Constructor
    public Task_groups (String title){

        group_title = title;

    }

    // Return title of task group
    public String getGroup_title(){ return  group_title; }

}
