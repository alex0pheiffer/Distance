package com.example.distance.db_things;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface Reminder_dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Reminder_dbObj userCards);

    //delete an individual field
    @Delete
    void delete(Reminder_dbObj userCards);

    //delete the entire table
    @Query("DELETE FROM Reminder_Table")
    void deleteAll();

    //returns a list of User_Cards
    @Query("SELECT * FROM Reminder_Table ORDER BY ID ASC")
    LiveData<List<Reminder_dbObj>> getAll();

    //updating the LABEL by order id
    @Query("UPDATE Reminder_Table SET LABEL=:newLabel WHERE id =:index")
    void updateLabel(String newLabel, int index);

    //updating the LOCATION by order id
    @Query("UPDATE Reminder_Table SET LOCATION=:newLocation WHERE id =:index")
    void updateLocation(String newLocation, int index);

    //updating the DISTANCE by order id
    @Query("UPDATE Reminder_Table SET DISTANCE=:newDistance WHERE id =:index")
    void updateDistance(int newDistance, int index);

}
