package com.example.distance.db_things;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.distance.db_things.distanceRepository;

import java.util.List;

public class distanceViewModel extends AndroidViewModel {

    private distanceRepository repository;
    private LiveData<List<Reminder_dbObj>> lReminders;

    public distanceViewModel(Application application) {
        super(application);
        repository = new distanceRepository(application);
        lReminders = repository.getlreminders();
    }

    //wrapper methods
    public void insert(Reminder_dbObj reminder_dbObj) {repository.insert(reminder_dbObj);}
    public void deleteReminder(Reminder_dbObj reminder_dbObj) {repository.delete(reminder_dbObj);}
    public void updateLabel(Reminder_dbObj reminder_dbObj) {repository.updateLabel(reminder_dbObj);}
    public void updateLocation(Reminder_dbObj reminder_dbObj) {repository.updateLocation(reminder_dbObj);}
    public void updateDistance(Reminder_dbObj reminder_dbObj) {repository.updateDistance(reminder_dbObj);}


}
