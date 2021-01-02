package com.dev.jeevanyohan.notesapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = Constants.TABLE_NAME_NOTE)

public class MyChecklist implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private int check_list_id;


    @ColumnInfo(name = "checklist_title")
    private String checklist_title;
    @ColumnInfo(name = "checklist_done")
    private String checklist_done;

    @ColumnInfo(name = "checklist_pending")
    private String checklist_pending;


    public MyChecklist(int check_list_id, String checklist_title, String checklist_done, String checklist_pending) {
        this.check_list_id = check_list_id;
        this.checklist_title = checklist_title;
        this.checklist_done = checklist_done;
        this.checklist_pending = checklist_pending;
    }

    public int getCheck_list_id() {
        return check_list_id;
    }

    public void setCheck_list_id(int check_list_id) {
        this.check_list_id = check_list_id;
    }

    public String getChecklist_title() {
        return checklist_title;
    }

    public void setChecklist_title(String checklist_title) {
        this.checklist_title = checklist_title;
    }

    public String getChecklist_done() {
        return checklist_done;
    }

    public void setChecklist_done(String checklist_done) {
        this.checklist_done = checklist_done;
    }

    public String getChecklist_pending() {
        return checklist_pending;
    }

    public void setChecklist_pending(String checklist_pending) {
        this.checklist_pending = checklist_pending;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyChecklist)) return false;

        MyChecklist checklist = (MyChecklist) o;

        if (check_list_id != checklist.check_list_id) return false;
        return checklist_title != null ? checklist_title.equals(checklist.checklist_title) : checklist.checklist_title == null;
    }



    @Override
    public int hashCode() {
        int result = check_list_id;
        result = 31 * result + (checklist_title != null ? checklist_title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "checklist_title=" + checklist_title +
                ", checklist_done='" + checklist_done + '\'' +
                ", checklist_pending='" + checklist_pending + '\'' +
                '}';
    }
}
