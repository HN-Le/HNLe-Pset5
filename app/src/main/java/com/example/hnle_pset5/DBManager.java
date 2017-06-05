package com.example.hnle_pset5;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DBHelper DBhelper;
    private Context context;
    private SQLiteDatabase database;

    // Constructor
    public DBManager (Context c) { context = c; }

    // Open database
    public DBManager open() throws SQLException {

        DBhelper = DBHelper.getsInstance(context);
        database = DBhelper.getWritableDatabase();
        return this;

    }

    // Close Database
    public void close() { DBhelper.close(); }
}
