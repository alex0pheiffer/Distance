package com.example.distance;

public class reminder {

    private int id;
    private String label;
    private String location; //turn into location object
    /*
    im assuming we'll want some sort of "location object"
    which we can convert our strange stored string in the db
    to an actual location we can use on google maps
     */
    public reminder(int id, String label, String location) {
        this.id = id;
        this.label = label;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }

    public int getDistance() {
        //find distance from us to the location
        return 0;
    }
}
