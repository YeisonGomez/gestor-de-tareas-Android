 package com.example.yeisongomez.gestordetareas;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

 public class SecondActivity extends AppCompatActivity implements SensorEventListener{

     Button activeToast;
     String textNotify = "Hola Desarrollador!";

     LinearLayout layoutSensor;
     TextView state_sensor;
     Sensor sensor;
     SensorManager sm;

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

        //TODO Sensor de proximidad
        layoutSensor = (LinearLayout) findViewById(R.id.layoutSensor);
        state_sensor = (TextView) findViewById(R.id.state_sensor);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

     @Override
     public void onSensorChanged(SensorEvent event) {
         String texto = String.valueOf(event.values[0]);
         state_sensor.setText(texto);

         float valor = Float.parseFloat(texto);
         if (valor == 0){
            layoutSensor.setBackgroundColor(Color.BLUE);
         } else{
             layoutSensor.setBackgroundColor(Color.YELLOW);
         }
     }

     @Override
     public void onAccuracyChanged(Sensor sensor, int i) {

     }
 }
