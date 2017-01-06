package com.example.yeisongomez.gestordetareas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by YeisonGomez on 5/01/17.
 */

public class taskDB {

    public static final String TASK_ID = "_id";
    public static final String TASK_SUBJECT = "subject";
    public static final String TASK_IMPORTANT = "important";

    private static final String TAG = "TaskAdapter";
    private DatabaseHelper DbHelper;
    private SQLiteDatabase Db;

    private static final String DATABASE_NAME = "db_gestor_tareas";
    private static final String TABLE_NAME  = "task";
    private static final int DATABASE_VERSION = 1;

    private static Context ctx;

    private static final String CREATE_DB =
            "CREATE TABLE if not exists" + TABLE_NAME + " ( " +
                    TASK_ID + " INTEGER PRIMARY KEY autoincrement,  " +
                    TASK_SUBJECT + " TEXT, " +
                    TASK_IMPORTANT + " INTEGER );";


    public taskDB(Context ctx){
        this.ctx = ctx;
    }

    //CRUD
    //CREATE
    public void createTask(String subject, boolean important){
        ContentValues values = new ContentValues();
        values.put(TASK_SUBJECT, subject);
        values.put(TASK_IMPORTANT, important ? 1 : 0);
        Db.insert(TABLE_NAME, null, values);
    }

    public long createTask(taskObject reminder){
        ContentValues values = new ContentValues();
        values.put(TASK_SUBJECT, reminder.getSubject());
        values.put(TASK_IMPORTANT, reminder.getImportant());
        return Db.insert(TABLE_NAME, null, values);
    }

    //READ
    public taskObject readTaskById(int id){
        Cursor cursor = Db.query(
                TABLE_NAME,
                new String[]{ TASK_ID, TASK_SUBJECT, TASK_IMPORTANT },
                TASK_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null
        );
        if (cursor != null)
            cursor.moveToFirst();

        return new taskObject(
                cursor.getString(1),
                cursor.getInt(0),
                cursor.getInt(2)
        );
    }

    public Cursor readTask(){
        Cursor cursor = Db.query(
                TABLE_NAME,
                new String[]{ TASK_ID, TASK_SUBJECT, TASK_IMPORTANT },
                null, null, null, null, null
        );
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    //UPDATE
    public void updateTask(taskObject reminder){
        ContentValues values = new ContentValues();
        values.put(TASK_SUBJECT, reminder.getSubject());
        values.put(TASK_IMPORTANT, reminder.getImportant());
        Db.update(TABLE_NAME, values, TASK_ID + "=?", new String[]{String.valueOf(reminder.getId())});
    }

    //DELETE
    public void deleteTaskById(int id){
        Db.delete(TABLE_NAME, TASK_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteTaskAll(){
        Db.delete(TABLE_NAME, null, null);
    }

    //abrir
    public void open() throws SQLException{
        DbHelper = new DatabaseHelper(this.ctx);
        Db = DbHelper.getWritableDatabase();
    }

    //cerrar
    public void close() {
        if (DbHelper != null){
            DbHelper.close();
        }
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, CREATE_DB);
            db.execSQL(CREATE_DB);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgradind database from version " + oldVersion + " to " +
                + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
