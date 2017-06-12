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
    ArrayList<String> groupList = new ArrayList<>();
    String status_task;
    String group;
    ListView lvitems;
    ArrayAdapter arrayAdapter;
    String group_id;
    Context context = this;
    DBManager database = new DBManager(context);
    TaskAdapter adapter;

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
        Log.i("tasks oncreate", taskList.toString());

        setAdapter();

        // Loop through task database
        for (Task task: taskList){

            //retrieve group_id


            // If task is done check the box
            if ("DONE".equals(task.getTask_status())) {
                lvitems.setItemChecked(taskList.indexOf(task), true);
            }

            // If not, leave it unchecked
            else {
                lvitems.setItemChecked(taskList.indexOf(task), false);
            }

        }

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
                        Log.i("input", task);

                        // Store user input in the database
                        DBHelper.getsInstance(SecondActivity.this).create(task, "TODO", group_id);

                        taskList = DBHelper.getsInstance(SecondActivity.this).read(group_id);

                        Log.i("tasks ADD ", taskList.toString());

                        ArrayList <String> groups = new ArrayList<>();

                        for(Task taskie : taskList){
                            groups.add(taskie.getTask_name());
                            Log.i("groups", groups.toString());
                        }

                        groupList = groups;

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
                (this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, groupList); // VERANDER

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

            group = taskList.get(position).getTask_group();

            // check the status of the task
            status_task = taskList.get(position).getTask_status();

            // If box is unchecked
            if (status_task.equals("TODO")){
                // Check the box
                lvitems.setItemChecked(position, true);

                // Change the status in object to DONE
                taskList.get(position).setTask_status("DONE");
            }

            // If box is checked
            else{
                // Un check the box
                lvitems.setItemChecked(position, false);

                // Change the status in object to TO DO
                taskList.get(position).setTask_status("TODO");
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

