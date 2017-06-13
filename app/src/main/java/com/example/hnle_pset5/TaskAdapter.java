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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TaskAdapter extends ArrayAdapter {

    private ArrayList<Task> taskData;
    private Context context;
    private ListActivity listActivity;
    String group;
    Task task;
    String task_status;
    String task_item;



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
        Log.d("TASK view item", "WERKT");

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_tasks, parent, false);

        }

        task = taskData.get(pos);
        task_item = task.getTask_name();
        task_status = task.getTask_status();

        TextView listTextView = (CheckBox) view.findViewById(R.id.checkBox);
        Log.d("TASK view status", task_status);
        Log.d("TASK view item", task_item);

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        checkBox.setOnCheckedChangeListener(new Listener());

        listTextView.setText(task_item);

        return view;

    }

    public class Listener implements CompoundButton.OnCheckedChangeListener{


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            if (task_status.equals("TODO")){
                checkBox.setChecked(checkBox.isChecked());
                Log.d("TASK TODO", task_status);
            }

            else {
                checkBox.setChecked(!checkBox.isChecked());
                Log.d("TASK NOP", task_status);
            }

        }
    }

}

