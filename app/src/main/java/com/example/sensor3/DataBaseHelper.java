package com.example.sensor3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    private static final String SENSOR_TABLE = "SENSOR_TABLE";
    private static final String COLUMN_X = "KOOR_X";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_Y = "KOOR_Y";
    private static final String COLUMN_Z = "KOOR_Z";

    public DataBaseHelper(Context context) {
        super(context, "sensor.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + SENSOR_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_X + " TEXT, "
                + COLUMN_Y + " TEXT, " + COLUMN_Z + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(SensorModel sensorModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_X, sensorModel.getX());
        cv.put(COLUMN_Y, sensorModel.getY());
        cv.put(COLUMN_Z, sensorModel.getZ());

        long insert = db.insert(SENSOR_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return false;
        }
    }

    public List<SensorModel> getEveryone() {
        List<SensorModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + SENSOR_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
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

}
