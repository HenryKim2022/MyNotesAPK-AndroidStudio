package com.iti.mynotes.model.v_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iti.mynotes.model.NotesModel;
import com.iti.mynotes.repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository repository;
    public LiveData<List<NotesModel>> getAllNotes;

    public LiveData<List<NotesModel>> hightolow;
    public LiveData<List<NotesModel>> lowtohigh;


    public NotesViewModel(Application application) {
        super(application);

        repository = new NotesRepository(application);
        getAllNotes = repository.getallNotes;

        hightolow = repository.hightolow;
        lowtohigh = repository.lowtohigh;
    }

    public void insertNotes(NotesModel notesModel){
        repository.insertNotes(notesModel);
    }


    public void deleteNotes(int id){
        repository.deleteNotes(id);
    }

    public void updateNotes(NotesModel notesModel){
        repository.updateNotes(notesModel);
    }
}
