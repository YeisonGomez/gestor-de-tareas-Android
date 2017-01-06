package com.example.yeisongomez.gestordetareas;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private taskDB taskDb;
    private TaskSimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.content_task);
        findViewById(R.id.content_task);
        mListView.setDivider(null);
        this.taskDb = new taskDB(this);
        this.taskDb.open();

        //Iniciarlizar algunos valores
        if (savedInstanceState == null)
            taskDb.deleteTaskAll();
            taskDb.createTask("Hola 1", true);
            taskDb.createTask("Hola 2", false);
            taskDb.createTask("Hola 3", false);
            taskDb.createTask("Hola 4", false);

        Cursor cursor = taskDb.readTask();

        String[] from = new String[]{
                taskDb.TASK_SUBJECT
        };

        int[] to = new int[]{
                R.id.textView
        };

        cursorAdapter = new TaskSimpleCursorAdapter(
                MainActivity.this,
                R.layout.content_main,
                cursor,
                from,
                to,
                0

        );

        /* //ADAPTER ESTATICO
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.component_task,
                R.id.textView,
                new String[]{"Hola 1", "Hola 2", "Hola 3"}
        );*/

        mListView.setAdapter(cursorAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.d(getLocalClassName(), "Crear nuevo Aviso");
                return true;
            case R.id.action_exit:
                finish();
                return true;
            default:
                return false;
        }
    }
}
