package com.example.distance.db_things;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Reminder_Table")
public class Reminder_dbObj {

    @PrimaryKey(autoGenerate= true)
    private int id;

    @NonNull
    @ColumnInfo(name = "LABEL")
    private String label;

    @NonNull
    @ColumnInfo(name = "LOCATION")
    private String location;

    @NonNull
    @ColumnInfo(name = "LAT")
    private int lat;

    @NonNull
    @ColumnInfo(name = "LON")
    private int lon;

    @NonNull
    @ColumnInfo(name = "DISTANCE")
    private int distance;

    public Reminder_dbObj(String label, String location, int lat, int lon, int distance) {
        this.label = label;
        this.location = location;
        this.lat = lat;
        this.lon = lon;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public int getDistance() {
        return distance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLat() {
        return lat;
    }

    public int getLon() {
        return lon;
    }


    public void setLat(int lat) {
        this.lat = lat;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public void setLabel(@NonNull String label) {
        this.label = label;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
