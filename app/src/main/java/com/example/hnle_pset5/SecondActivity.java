package com.example.hnle_pset5;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ArrayList<Task> taskList;
    String status_task;
    ListView lvitems;
    ArrayAdapter arrayAdapter;
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

        Log.d("CHECKER", taskList.toString());

        // Loop through task database
        for (Task task: taskList){

            // If task is done check the box
            if ("DONE".equals(task.getTask_status())) {

                lvitems.setItemChecked(taskList.indexOf(task), true);
            }

            // If not, leave it unchecked
            else {
                lvitems.setItemChecked(taskList.indexOf(task), false);
            }

            Log.d("CHECKER", task.getTask_status());
        }

        setAdapter();

        // Listener for the short taps to change the status
        lvitems.setOnItemClickListener(new Listener());

        // Listener for the long taps on the task itself to delete
        lvitems.setOnItemLongClickListener(new LongListener());
    }

    // Renders the menu in the main activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
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
        // list view with checkboxes next to the text

        arrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, taskList);

        lvitems = (ListView) findViewById(R.id.lvitems);

        assert lvitems != null;
        lvitems.setAdapter(arrayAdapter);

        lvitems.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }

    // Onclick Listener for when an item has been clicked in the list
    private class Listener implements AdapterView.OnItemClickListener{

        // Listener to check the boxes (un check/ check)
        @Override
        public void onItemClick (AdapterView<?> parent, View view, int position, long id) {

            // check the status of the task
            status_task = taskList.get(position).getTask_status();

            // If box is checked
            if (lvitems.isItemChecked(position)){
                // UN check the box
                lvitems.setItemChecked(position, true);

                // Change the status in object to "TO DO"
                taskList.get(position).setTask_status("TODO");


                Log.d("CHECKER CHECKED", status_task);
            }

            // If box is unchecked
            else{
                // check the box
                lvitems.setItemChecked(position, false);

                // Change the status in object to DONE
                taskList.get(position).setTask_status("DONE");

                Log.d("CHECKER UNCHECKED", status_task);

            }

            // Update the database
            DBHelper.getsInstance(SecondActivity.this).update(taskList.get(position));

        }

    }

    // Long Onclick listener for when an task is long pressed
    private class LongListener implements AdapterView.OnItemLongClickListener{

        public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id) {

            // Delete from database
            DBHelper.getsInstance(SecondActivity.this).delete(taskList.get(position));

            DBHelper.getsInstance(SecondActivity.this).read(group_id);

            // Delete object
            taskList.remove(position);

            // notify adapter that list view was changed
            arrayAdapter.notifyDataSetChanged();

            return true;
        }
    }

}

