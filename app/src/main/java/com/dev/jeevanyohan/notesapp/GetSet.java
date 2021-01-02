package com.dev.jeevanyohan.notesapp;

public class GetSet {

    public static Note getSelectedNote() {
        return selectedNote;
    }

    public static void setSelectedNote(Note selectedNote) {
        GetSet.selectedNote = selectedNote;
    }

    public static Note selectedNote;
}
