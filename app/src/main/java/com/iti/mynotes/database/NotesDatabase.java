package com.iti.mynotes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.iti.mynotes.dao.NotesDao;
import com.iti.mynotes.model.NotesModel;

@Database(entities = {NotesModel.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();
    public static NotesDatabase INSTANCE;
    public static NotesDatabase getDatabaseInstance(Context context){
        if(INSTANCE == null){
           INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                   NotesDatabase.class,"Notes_Table").allowMainThreadQueries().build();
        }
        return  INSTANCE;
    }

}
