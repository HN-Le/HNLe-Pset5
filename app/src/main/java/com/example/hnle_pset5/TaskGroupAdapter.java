package com.example.hnle_pset5;

import android.app.ListActivity;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskGroupAdapter extends ArrayAdapter {
    private ArrayList<Task_groups> taskData;
    private Context context;
    private ListActivity listActivity;
    private String item;

    // Constructor
    public TaskGroupAdapter(Context temp_context, ArrayList<Task_groups> data) {
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
    public View getView(int pos, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_taskgroup, parent, false);

        }

        Task_groups group = taskData.get(pos);
        String task_item = group.getGroup_title();

        TextView listTextView = (TextView) view.findViewById(R.id.group_title);
        listTextView.setText(task_item);

        return view;

    }

}
