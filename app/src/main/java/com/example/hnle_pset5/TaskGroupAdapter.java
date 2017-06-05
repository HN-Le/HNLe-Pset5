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
import java.util.List;

/**
 * Created by Tiny on 2-6-2017.
 */

public class TaskGroupAdapter extends ArrayAdapter {
    private static final String TAG = "TaskGroupAdapter";
    private ArrayList<Task_groups> taskData;
    private Context context;
    private ListActivity listActivity;

    // Constructor
    public TaskGroupAdapter (Context temp_context, ArrayList<Task_groups> data){
        super(temp_context, 0, data);
        Log.d(TAG, "TaskGroupAdapter() called with: temp_context = [" + temp_context + "], data = [" + data + "]"); 
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
        Log.d(TAG, "getView() called with: pos = [" + pos + "], convertView = [" + convertView + "], parent = [" + parent + "]");

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_main, parent, false);

        }

        Task_groups group = taskData.get(pos);
        String task_item = group.getGroup_title();

        TextView listTextView = (TextView) view.findViewById(R.id.listTextView);
        listTextView.setText(task_item);

        // ONCLICK LISTENER HERE FOR WHEN PRESSED ON TASK

        return view;

    }
}
