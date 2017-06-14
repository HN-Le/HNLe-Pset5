package com.example.hnle_pset5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;


public class TaskAdapter extends ArrayAdapter {

    private ArrayList<Task> taskData;
    private Task task;
    private OnItemCheckedListener listener;

    // Constructor
    public TaskAdapter (Context context, ArrayList<Task> data){
        super(context, 0, data);
        this.taskData = data;
    }

    // Get view and return
    @Override
    public View getView(final int pos, View  convertView, ViewGroup parent){

        View view = convertView;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_tasks, parent, false);

        }

        task = taskData.get(pos);
        String task_item = task.getTask_name();
        String task_status = task.getTask_status();

        //Vind de checkbox
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        checkBox.setOnCheckedChangeListener(null);

        // zet vinkje aan op basis van task status
        checkBox.setChecked("DONE".equals(task_status));

        // zet de tekst
        checkBox.setText(task_item);

        // luister op check events
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null) {
                    listener.checkItem(task, pos, isChecked);
                }
            }
        });


        return view;
    }

    public void setOnItemCheckedListener(OnItemCheckedListener listener) {
        this.listener = listener;
    }

    public interface OnItemCheckedListener {
        void checkItem(Task task, int position, boolean isChecked);
    }

}