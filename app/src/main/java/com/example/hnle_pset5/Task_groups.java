package com.example.hnle_pset5;

import java.io.Serializable;

public class Task_groups implements Serializable {

    // Title of task group
    private String group_title;
    private String group_id;

    // Constructor
    public Task_groups (String title, String id){

        group_title = title;
        group_id = id;

    }

    // Return title of task group
    public String getGroup_title(){ return  group_title; }

    public String getGroup_id() { return group_id; }

}
