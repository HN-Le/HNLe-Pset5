package com.example.hnle_pset5;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TaskAdapter extends ArrayAdapter {

    private ArrayList<Task> taskData;
    private Context context;
    private ListActivity listActivity;
    String group;
    Task task;

    // Constructor
    public TaskAdapter (Context temp_context, ArrayList<Task> data){
        super(temp_context, 0, data);
        this.taskData = data;
        this.listActivity = (ListActivity) context;
        this.context = temp_context;
    }

    @Override
    public int getCount() {
        return taskData.size();
    }


    // Get view and return
    @Override
    public View getView(int pos, View  convertView, ViewGroup parent){

        View view = convertView;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_tasks, parent, false);

        }

        task = taskData.get(pos);

        String task_item = task.getTask_name();

        TextView listTextView = (TextView) view.findViewById(R.id.task_title);
        listTextView.setText(task_item);

        return view;

    }
}

