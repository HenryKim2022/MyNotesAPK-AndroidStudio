package com.iti.mynotes.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes_Table")
public class NotesModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "notes_title")
    public String notesTitle;

    @ColumnInfo(name = "notes_subtitle")
    public String notesSubtitle;

    @ColumnInfo(name = "notes_date")
    public String notesDate;

    @ColumnInfo(name = "notes")
    public String notes;

    @ColumnInfo(name = "notes_priority")
    public String notesPriority;

}
