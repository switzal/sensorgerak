package com.example.sensor3;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    ListView lv_sensorList;
    ArrayAdapter sensorArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        dataBaseHelper = new DataBaseHelper(ListViewActivity.this);
        lv_sensorList = findViewById(R.id.listData);

        getEveryone();

        ShowSensorOnListView(dataBaseHelper);
    }

    public List<SensorModel> getEveryone() {
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        List<SensorModel> returnList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM SENSOR_TABLE", null);
        if (cursor.moveToFirst()) {
            do {
                int sensorID = cursor.getInt(0);
                String sensorX = cursor.getString(1);
                String sensorY = cursor.getString(2);
                String sensorZ = cursor.getString(3);

                SensorModel newSensor = new SensorModel(sensorID, sensorX, sensorY, sensorZ);
                returnList.add(newSensor);

            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        return returnList;

    }

    private void ShowSensorOnListView(DataBaseHelper dataBaseHelper2) {
        sensorArrayAdapter = new ArrayAdapter<SensorModel>(ListViewActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getEveryone());
        lv_sensorList.setAdapter(sensorArrayAdapter);
    }
}
