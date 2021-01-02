package com.dev.jeevanyohan.notesapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = Constants.TABLE_NAME_NOTE)

public class Note implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private int note_id;

    @ColumnInfo(name = "note_body")
    private String note_body;

    private String note_title;

    public Note(String note_body, String note_title) {
        this.note_body = note_body;
        this.note_title = note_title;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getNote_body() {
        return note_body;
    }

    public void setNote_body(String note_body) {
        this.note_body = note_body;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;

        Note note = (Note) o;

        if (note_id != note.note_id) return false;
        return note_title != null ? note_title.equals(note.note_title) : note.note_title == null;
    }



    @Override
    public int hashCode() {
        int result = note_id;
        result = 31 * result + (note_title != null ? note_title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "note_id=" + note_id +
                ", note_body='" + note_body + '\'' +
                ", note_title='" + note_title + '\'' +
                '}';
    }
}
