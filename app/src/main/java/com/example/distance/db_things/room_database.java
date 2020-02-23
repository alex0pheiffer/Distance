package com.example.distance.db_things;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.distance.db_things.Reminder_dao;
import com.example.distance.db_things.Reminder_dbObj;

@Database(entities = Reminder_dbObj.class, version = 1)
public abstract class room_database extends RoomDatabase {
    public abstract Reminder_dao reminderDao();

    private static volatile room_database INSTANCE;

    static room_database getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (room_database.class) {
                if (INSTANCE == null) {
                    //normal
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            room_database.class, "rpg_database").addCallback(sRoomDatabaseCallback).build();
                    //sendMessage("Database created; ver: ",context);
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {

        }

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            System.out.println("DB HAS BEEN OPENED");
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

}
