 package com.example.yeisongomez.gestordetareas;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        //TODO listView basica
        String[] valores = new String[] {"Probando 1", "Probando 2", "Probando 3"};
        ListView list_guide = (ListView) findViewById(R.id.list_guide);
        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, valores);
        list_guide.setAdapter(adapter);

        //Cambiar a activity_webview
        Button open_webview = (Button) findViewById(R.id.open_webview);
        open_webview.setOnClickListener(view -> {
            Intent intent = new Intent(SecondActivity.this, WebViewActivity.class);
            startActivity(intent);
        });
    }

     @Override
     public void onSensorChanged(SensorEvent event) {
         String texto = String.valueOf(event.values[0]);
         state_sensor.setText(texto);

         float valor = Float.parseFloat(texto);
         if (valor == 100 ){
            layoutSensor.setBackgroundColor(Color.WHITE);
         } else{
             layoutSensor.setBackgroundColor(Color.BLUE);
         }
     }

     @Override
     public void onAccuracyChanged(Sensor sensor, int i) {

     }
 }
