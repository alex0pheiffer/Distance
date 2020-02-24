package com.example.distance.db_things;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.distance.db_things.Reminder_dao;
import com.example.distance.db_things.Reminder_dbObj;

@Database(entities = Reminder_dbObj.class, version = 3)
public abstract class room_database extends RoomDatabase {
    public abstract Reminder_dao reminderDao();

    private static volatile room_database INSTANCE;

    static room_database getDatabase(final Context context) {

        //INSTANCE = Room.databaseBuilder(context.getApplicationContext(), room_database.class, "rpg_database").addMigrations(MIGRATION_2_3).build();

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

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            //sendMessage("mitigationTest ver: "+database.getVersion(),forMessaging);

            // Since we didn't alter the table, there's nothing else to do here.
            //IF YOU DO MODIFY, EX:
            //adding columns: https://medium.com/@manuelvicnt/android-room-upgrading-alpha-versions-needs-a-migration-with-kotlin-or-nonnull-7a2d140f05b9
            /*
            database.execSQL("ALTER TABLE User_Values_Table"
                            + " ADD COLUMN REGION TEXT NOT NULL");
             */
            //create new table

            database.execSQL("CREATE TABLE IF NOT EXISTS TABLE_NAME_TEMP " +
                    "(`id` INTEGER NOT NULL PRIMARY KEY, " +
                    "`LABEL` TEXT NOT NULL, " +
                    "`LOCATION` TEXT NOT NULL, " +
                    "`LAT` DOUBLE NOT NULL, " +
                    "`LON` DOUBLE NOT NULL, " +
                    "`DISTANCE` INTEGER NOT NULL " +" )"
            );
            //copy the data
            /*
            database.execSQL("INSERT INTO TABLE_NAME_TEMP (LABEL, LVL, RANK, WEAPON, DECK, ITEM) "
                    + "SELECT LABEL, LVL, EXP, WEAPON, DECK, ITEM"
                    + "FROM User_Characters_Table");
            */
            //drop table
            database.execSQL("DROP TABLE Reminder_Table");
            //replace table name
            database.execSQL("ALTER TABLE TABLE_NAME_TEMP RENAME TO Reminder_Table");

        }
    };

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
