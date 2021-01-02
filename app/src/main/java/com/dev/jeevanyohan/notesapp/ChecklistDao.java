package com.dev.jeevanyohan.notesapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChecklistDao {
    @Query("SELECT * FROM "+ Constants.TABLE_NAME_CHECKLIST)
    List<MyChecklist> getAll();


    /*
     * Insert the object in database
     * @param MyChecklist, object to be inserted
     */
    @Insert
    void insert(MyChecklist MyChecklist);

    /*
     * update the object in database
     * @param MyChecklist, object to be updated
     */
    @Update
    void update(MyChecklist repos);

    /*
     * delete the object from database
     * @param MyChecklist, object to be deleted
     */
    @Delete
    void delete(MyChecklist MyChecklist);

    /*
     * delete list of objects from database
     * @param MyChecklist, array of objects to be deleted
     */
    @Delete
    void delete(MyChecklist... MyChecklist);
}
