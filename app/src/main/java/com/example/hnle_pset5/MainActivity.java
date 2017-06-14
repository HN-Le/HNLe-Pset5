package com.example.hnle_pset5;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private ArrayList<Task_groups> taskData;
    private Context context = this;
    private TaskGroupAdapter adapter;
    public DBManager database = new DBManager(context);
    public ListView group_items;
    public String group_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        group_items = (ListView) findViewById(R.id.task_list);

        // Open database
        database.open();

        // Array list to view items later
        taskData = DBHelper.getsInstance(MainActivity.this).read_group();

        // Set adapter and view list
        setAdapter();

        // Set listeners for taps and long taps
        group_items.setOnItemClickListener(new Listener());
        group_items.setOnItemLongClickListener(new MainActivity.LongListener());

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
                .setMessage("Add a plan")
                .setView(user_input)


                // Make an "Add" button
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    // When pressed on the "Add" button of the dialog screen
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        // Get user input
                        String new_list = String.valueOf(user_input.getText());

                        // Create new entry into database
                        DBHelper.getsInstance(MainActivity.this).create_group(new_list);

                        // Restart the main activity to show the added task
                        restartFirstActivity();
                    }
                })

                // Make a "Cancel" button
                .setNegativeButton("Cancel", null)
                .create();

        dialog.show();
        return true;

    }

    public void setAdapter(){
        adapter = new TaskGroupAdapter(this, taskData);
        ListView listView = (ListView) findViewById(R.id.task_list);
        listView.setAdapter(adapter);
    }

    // Onclick Listener for when an item has been clicked in the list
    private class Listener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            group_id = taskData.get(position).getGroup_id();

            // Pass group id and go to next screen
            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
            intent.putExtra("group_id", group_id);
            startActivity(intent);

        }

    }

    // Long Onclick listener for when an task is long pressed
    private class LongListener implements AdapterView.OnItemLongClickListener{

        public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id) {

            // Delete from database
            DBHelper.getsInstance(MainActivity.this).delete_group(taskData.get(position));

            // Delete object
            taskData.remove(position);

            // Restart activity
            restartFirstActivity();

            return true;
        }
    }

    // Restart the main activity
    private void restartFirstActivity()
    {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName() );

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity(i);
    }

}
