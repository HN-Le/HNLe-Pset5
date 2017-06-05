package com.example.hnle_pset5;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Task> taskData;
    private Context context;
    private TaskAdapter adapter;
    DBHelper helper;
    DBManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Arraylist to view items later
        taskData = new ArrayList<>();

        // Open database
        database.open();

        // Set adapter and view list
        setAdapter();

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

//                        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
//                        startActivity(intent);

                        // Get user input
                        String new_list = String.valueOf(user_input.getText());

                        // Create new entry into database
                        helper.create_group(new_list);

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
        adapter = new TaskAdapter(this, taskData);
        ListView listView = (ListView) findViewById(R.id.task_list);
        listView.setAdapter(adapter);
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
