package com.example.distance.db_things;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import timber.log.Timber;

public class distanceRepository {

    private Reminder_dao reminderDao;
    private LiveData<List<Reminder_dbObj>> lreminders;

    public distanceRepository(Application application) {
        room_database db = room_database.getDatabase(application);
        reminderDao = db.reminderDao();
        lreminders = reminderDao.getAll();
    }

    //get the live data
    public LiveData<List<Reminder_dbObj>> getlreminders() {return lreminders;}

    //add a new reminder to the list (by id)
    public void insert(Reminder_dbObj reminder) {
        Timber.d("reminder: "+reminder.getLabel()+" IS BEING added!");
        new insertAsyncTaskReminder(reminderDao).execute(reminder);
        Timber.d("reminder: "+reminder.getLabel()+" has been added !");
    }

    //update a current reminder label (by id)
    public void updateLabel(Reminder_dbObj reminder) {
        new updateLabelAsyncTask(reminderDao).execute(reminder);
    }
    //update a current reminder location (by id)
    public void updateLocation(Reminder_dbObj reminder) {
        new updateLocationAsyncTask(reminderDao).execute(reminder);
    }
    //update a current reminder distance (by id)
    public void updateDistance(Reminder_dbObj reminder) {
        new updateDistanceAsyncTask(reminderDao).execute(reminder);
    }

    //delete a reminder (by id)
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
            Timber.d("adding to async...");
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
            mAsyncTaskDao.updateLocation(params[0].getLocation(),params[0].getId());
            return null;
        }
    }
    //distance
    private static class updateDistanceAsyncTask extends AsyncTask<Reminder_dbObj, Void, Void> {

        private Reminder_dao mAsyncTaskDao;

        updateDistanceAsyncTask(Reminder_dao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Reminder_dbObj... params) {
            mAsyncTaskDao.updateDistance(params[0].getDistance(),params[0].getId());
            return null;
        }
    }

}
