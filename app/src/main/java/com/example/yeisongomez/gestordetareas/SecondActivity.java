 package com.example.yeisongomez.gestordetareas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

 public class SecondActivity extends AppCompatActivity {

     Button activeToast;
     String textNotify = "Hola Desarrollador!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Recuperar Extras enviados desde MainActivity
        Bundle extras = getIntent().getExtras();
        int important;
        if (extras != null) {
            important = extras.getInt("first_task_important");
            this.textNotify = extras.getString("first_task_subject") + ((important == 1) ? " Importante": " No Importante");
        }

        activeToast = (Button) findViewById(R.id.active_toast);
        activeToast.setOnClickListener(view -> {
            Toast.makeText(SecondActivity.this, this.textNotify, Toast.LENGTH_SHORT).show();
        });



    }
}
