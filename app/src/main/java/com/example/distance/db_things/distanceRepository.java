package com.example.distance.db_things;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class distanceRepository {

    private Reminder_dao reminderDao;
    private LiveData<List<Reminder_dbObj>> lreminders;

    public distanceRepository(Application application) {
        room_database db = room_database.getDatabase(application);
        reminderDao = db.reminderDao();
        lreminders = reminderDao.getAll();
    }

    public LiveData<List<Reminder_dbObj>> getlreminders() {return lreminders;}

    public void insert(Reminder_dbObj reminder) {
        new insertAsyncTaskReminder(reminderDao).execute(reminder);
    }

    public void updateLabel(Reminder_dbObj reminder) {
        new updateLabelAsyncTask(reminderDao).execute(reminder);
    }

    public void delete(Reminder_dbObj reminder) {
        new deleteAsyncTaskReminder(reminderDao).execute(reminder);
    }

    //inserting
    private static class insertAsyncTaskReminder extends AsyncTask<Reminder_dbObj, Void, Void> {

        private Reminder_dao mAsyncTaskDao;

        insertAsyncTaskReminder(Reminder_dao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Reminder_dbObj... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    //deleting
    private static class deleteAsyncTaskReminder extends AsyncTask<Reminder_dbObj, Void, Void> {

        private Reminder_dao mAsyncTaskDao;

        deleteAsyncTaskReminder(Reminder_dao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Reminder_dbObj... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    //updateing
    //label
    private static class updateLabelAsyncTask extends AsyncTask<Reminder_dbObj, Void, Void> {

        private Reminder_dao mAsyncTaskDao;

        updateLabelAsyncTask(Reminder_dao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Reminder_dbObj... params) {
            mAsyncTaskDao.updateLabel(params[0].getLabel(),params[0].getId());
            return null;
        }
    }
    //location
    private static class updateLocationAsyncTask extends AsyncTask<Reminder_dbObj, Void, Void> {

        private Reminder_dao mAsyncTaskDao;

        updateLocationAsyncTask(Reminder_dao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Reminder_dbObj... params) {
            mAsyncTaskDao.updateLabel(params[0].getLocation(),params[0].getId());
            return null;
        }
    }
    //distance
    private static class updateDistanceAsyncTask extends AsyncTask<Reminder_dbObj, Void, Void> {

        private Reminder_dao mAsyncTaskDao;

        updateLocationAsyncTask(Reminder_dao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Reminder_dbObj... params) {
            mAsyncTaskDao.updateLabel(params[0].getLocation(),params[0].getId());
            return null;
        }
    }

}
