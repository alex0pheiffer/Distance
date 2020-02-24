package com.example.distance.db_things;

import android.os.AsyncTask;

public class PopulateDbAsync extends AsyncTask<Void,Void,Void> {

    private final Reminder_dao reminderDao;
    PopulateDbAsync(room_database db) {
        reminderDao = db.reminderDao();
    }
    private void removeAll() {
        reminderDao.deleteAll();
    }

    @Override
    protected Void doInBackground(final Void... params) {
        //this.removeAll();
        Reminder_dbObj remind = new Reminder_dbObj("Add_New", "location?", 0, 0, 0);

        return null;
    }

}
