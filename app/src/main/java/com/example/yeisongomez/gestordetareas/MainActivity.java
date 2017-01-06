package com.example.yeisongomez.gestordetareas;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private taskDB taskDb;
    private TaskSimpleCursorAdapter cursorAdapter;
    private FloatingActionButton addTask;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO Agregar icono en el header
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        //TODO Evento boton flotante
        addTask = (FloatingActionButton) findViewById(R.id.fab);
        addTask.setOnClickListener(view -> {
            activeModalAddTask(null);
        });

        //TODO Lista de tareas
        mListView = (ListView) findViewById(R.id.content_task);
        findViewById(R.id.content_task);
        mListView.setDivider(null);
        this.taskDb = new taskDB(this);
        this.taskDb.open();

        //Iniciarlizar algunos valores
        /* //TODO Agregar valores estaticos a SQLite
        if (savedInstanceState == null)
            taskDb.deleteTaskAll();
            taskDb.createTask("Hola 1", true);
            taskDb.createTask("Hola 2", false);
            taskDb.createTask("Hola 3", false);
            taskDb.createTask("Hola 4", false);
        */
        Cursor cursor = taskDb.readTask();

        String[] from = new String[]{
                taskDb.TASK_SUBJECT
        };

        int[] to = new int[]{
                R.id.textView
        };

        cursorAdapter = new TaskSimpleCursorAdapter(
                MainActivity.this,
                R.layout.component_task,
                cursor,
                from,
                to,
                0
        );

        /* //TODO Agregar valores estaticos al listView
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.component_task,
                R.id.textView,
                new String[]{"Hola 1", "Hola 2", "Hola 3"}
        );*/

        mListView.setAdapter(cursorAdapter);

        //TODO Agregar dialogo al darle click a un item (Edit, delete)
        /*mListView.setOnItemClickListener((parent, view, listPosition, l) -> {
            dialogOption(listPosition);
        });*/

        //TODO Activar headerOption (delete)
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener(){
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater =  mode.getMenuInflater();
                inflater.inflate(R.menu.cam_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_delete_task:
                        for (int nC = cursorAdapter.getCount() - 1; nC >= 0; nC--) {
                            if (mListView.isItemChecked(nC))
                                taskDb.deleteTaskById((int) cursorAdapter.getItemId(nC));
                        }
                        mode.finish(); //Desactiva los check de las listView
                        cursorAdapter.changeCursor(taskDb.readTask());
                        return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
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
                return true;
            case R.id.action_exit:
                finish();
                return true;
            default:
                return false;
        }
    }

    private void activeModalAddTask(taskObject task){
        //TODO Modal de agregar tareas
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_new_task);

        TextView text = (TextView) dialog.findViewById(R.id.textDialog);
        EditText editSubject = (EditText) dialog.findViewById(R.id.subject_task);
        CheckBox important = (CheckBox) dialog.findViewById(R.id.checkBox);
        Button button_cancel = (Button) dialog.findViewById(R.id.button_cancel);
        Button button_ok = (Button) dialog.findViewById(R.id.button_ok);
        boolean isEditOperation = (task != null);

        if (isEditOperation) {
            text.setText("Editar Tarea");
            editSubject.setText(task.getSubject());
            important.setChecked(task.getImportant() == 1);
        }

        button_ok.setOnClickListener(view -> {
            if (isEditOperation)
                taskDb.updateTask(new taskObject(editSubject.getText().toString(), important.isChecked() ? 1 : 0));
            else
                taskDb.createTask(editSubject.getText().toString(), important.isChecked());
            cursorAdapter.changeCursor(taskDb.readTask());
            dialog.dismiss();
        });

        button_cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    private void notifyToas(String text){
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }

    private void dialogOption(int listPosition){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final ListView modeListView = new ListView(MainActivity.this);
        String[] modes = new String[]{"Editar", "Borrar"};
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, modes);
        modeListView.setAdapter(modeAdapter);
        builder.setView(modeListView);
        final Dialog dialog = builder.create();
        dialog.show();
        modeListView.setOnItemClickListener((adapterView, view1, i, l1) -> {
            if (i == 0)
                notifyToas("Vas a editar la posicion: " + listPosition);
            else
                taskDb.deleteTaskById((int)cursorAdapter.getItemId(listPosition));
                cursorAdapter.changeCursor(taskDb.readTask());
            dialog.dismiss();
        });
    }
}
