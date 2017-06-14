package com.example.hnle_pset5;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ArrayList<Task> taskList;
    ListView lvitems;
    private TaskAdapter adapter;
    String group_id;
    Context context = this;
    DBManager database = new DBManager(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle extras = getIntent().getExtras();
        group_id = extras.getString("group_id");

        lvitems = (ListView) findViewById(R.id.lvitems);

        database.open();

        // Ask for a list of all tasks
        taskList = DBHelper.getsInstance(SecondActivity.this).read(group_id);

        Log.d("CHECKER TASKLIST", taskList.toString());

        for (Task task: taskList){
            Log.d("STATUS", task.getTask_status());
        }

        setAdapter();

        // Listener for the long taps on the task itself to delete
        lvitems.setOnItemLongClickListener(new LongListener());

    }

    // Renders the menu in the main activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    // When + sign is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Make an edit text for in the dialog
        final EditText user_input = new EditText(this);

        // Build dialog screen
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Add a task")
                .setView(user_input)


                // Make an "Add" button
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    // When pressed on the "Add" button of the dialog screen
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        // Get user input
                        String task = String.valueOf(user_input.getText());

                        // Store user input in the database
                        DBHelper.getsInstance(SecondActivity.this).create(task, group_id);

                        taskList = DBHelper.getsInstance(SecondActivity.this).read(group_id);

                        setAdapter();

                    }
                })

                // Make a "Cancel" button
                .setNegativeButton("Cancel", null)
                .create();

        dialog.show();
        return true;

    }

    public void setAdapter(){

        adapter = new TaskAdapter(this, taskList);
        // Make listener for taps in the list
        adapter.setOnItemCheckedListener(new TaskAdapter.OnItemCheckedListener() {
            @Override
            // If checked, change status and update database
            public void checkItem(Task task, int position, boolean isChecked) {
                task.setTask_status(isChecked ? "DONE" : "TODO");
                DBHelper.getsInstance(SecondActivity.this).update(task);
            }
        });

        ListView listView = (ListView) findViewById(R.id.lvitems);
        listView.setAdapter(adapter);

    }

    // Long Onclick listener for when an task is long pressed
    private class LongListener implements AdapterView.OnItemLongClickListener{

        public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id) {

            // Delete from database
            DBHelper.getsInstance(SecondActivity.this).delete(taskList.get(position));

            // Read database
            DBHelper.getsInstance(SecondActivity.this).read(group_id);

            // Delete
            taskList.remove(position);

            // notify adapter that list view was changed
            adapter.notifyDataSetChanged();

            return true;
        }
    }

}