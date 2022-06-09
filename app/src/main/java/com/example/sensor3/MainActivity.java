package com.example.sensor3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    TextView X, Y, Z;
    Button btn_list;
    ListView lv_sensorList;
    boolean flag = false;
    int interval = 2000;
    Handler handler;
    private final Runnable processSensors = new Runnable() {
        @Override
        public void run() {
            flag = true;
            handler.postDelayed(this, interval);
        }
    };

    ArrayAdapter sensorArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        X = findViewById(R.id.textX);
        Y = findViewById(R.id.textY);
        Z = findViewById(R.id.textZ);

        btn_list = findViewById(R.id.buttondatabase);
        lv_sensorList = findViewById(R.id.lv_sensorList);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        ShowSensorOnListView(dataBaseHelper);

        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            X.setText("0");
            Y.setText("0");
            Z.setText("0");
        } else {
            X.setText("Sensor NA");
            Y.setText("Sensor NA");
            Z.setText("Sensor NA");
        }

        final Handler handler = new Handler();
        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                SensorModel sensorModel;
                try {
                    sensorModel = new SensorModel(-1, X.getText().toString(), Y.getText().toString(), Z.getText().toString());
                } catch (Exception e) {
                    sensorModel = new SensorModel(-1, "error", "error", "error");
                }
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                boolean success = dataBaseHelper.addOne(sensorModel);
                ShowSensorOnListView(dataBaseHelper);
                handler.postDelayed(this, 60000);
            }
        };
        handler.postDelayed(refresh, 60000);

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

    }

    private void ShowSensorOnListView(DataBaseHelper dataBaseHelper2) {
        sensorArrayAdapter = new ArrayAdapter<SensorModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getEveryone());
        lv_sensorList.setAdapter(sensorArrayAdapter);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (flag) {
            String x = String.valueOf(sensorEvent.values[0]);
            String y = String.valueOf(sensorEvent.values[1]);
            String z = String.valueOf(sensorEvent.values[2]);
            X.setText(x);
            Y.setText(y);
            Z.setText(z);

            flag = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);

        handler.post(processSensors);
    }
}

