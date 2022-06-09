package com.example.sensor3;

public class SensorModel {
    private int id;
    private String x, y, z;
    ListViewActivity listViewActivity;

    public SensorModel(int id, String x, String y, String z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public SensorModel() {

    }

    @Override
    public String toString() {
        listViewActivity = new ListViewActivity();
        return "No. " + id + " || X : "+x+ " || Y : "+y+ " || Z : "+z+'\'';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }
}
