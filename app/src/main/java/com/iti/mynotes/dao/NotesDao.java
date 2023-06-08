package com.iti.mynotes.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.iti.mynotes.model.NotesModel;

import java.util.List;

@androidx.room.Dao
public interface NotesDao {

    @Query("SELECT * FROM Notes_Table")
    LiveData<List<NotesModel>> getallNotes();

    @Query("SELECT * FROM Notes_Table ORDER BY notes_priority DESC")
    LiveData<List<NotesModel>> highToLow();

    @Query("SELECT * FROM Notes_Table ORDER BY notes_priority ASC")
    LiveData<List<NotesModel>> lowToHigh();



    @Insert
    void insertNotes(NotesModel... notesModels);

    @Query("DELETE FROM Notes_Table WHERE id=:id;")
    void deleteNotes(int id);

    @Update
    void updateNotes(NotesModel notesModels);
}
