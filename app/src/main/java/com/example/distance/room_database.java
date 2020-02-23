package com.example.distance;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Reminder_dbObj.class, version = 1)
public abstract class room_database extends RoomDatabase {
    public abstract Reminder_dao Reminder_dao();

}
