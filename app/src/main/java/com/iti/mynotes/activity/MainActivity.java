package com.iti.mynotes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iti.mynotes.R;
import com.iti.mynotes.adapter.NotesAdapter;
import com.iti.mynotes.model.NotesModel;
import com.iti.mynotes.model.v_model.NotesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton newNotesBtn;
    NotesViewModel notesViewModel;

    RecyclerView notesRecycler;
    NotesAdapter adapter;

    TextView nofilter, hightolow, lowtohigh;

    List<NotesModel> filterNotesAllList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNotesBtn = findViewById(R.id.newNotesBtn);
        notesRecycler = findViewById(R.id.notesRecy);

        nofilter = findViewById(R.id.no_filter);
        hightolow = findViewById(R.id.hilo_filter);
        lowtohigh = findViewById(R.id.lohi_filter);

        nofilter.setBackgroundResource(R.drawable.filter_selected_shape);
        nofilter.setOnClickListener(v -> {
            loadData(0);
            nofilter.setBackgroundResource(R.drawable.filter_selected_shape);
            hightolow.setBackgroundResource(R.drawable.filter_un_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_un_shape);
        });

        hightolow.setOnClickListener(v -> {
            loadData(1);
            nofilter.setBackgroundResource(R.drawable.filter_un_shape);
            hightolow.setBackgroundResource(R.drawable.filter_selected_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_un_shape);
        });

        lowtohigh.setOnClickListener(v -> {
            loadData(2);
            nofilter.setBackgroundResource(R.drawable.filter_un_shape);
            hightolow.setBackgroundResource(R.drawable.filter_un_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_selected_shape);
        });



        // notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        newNotesBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, InsertNotesActivity.class));
        });


        notesViewModel.getAllNotes.observe(this, new Observer<List<NotesModel>>() {
            @Override
            public void onChanged(List<NotesModel> notesModels) {
                setAdapter(notesModels);
                filterNotesAllList = notesModels;
            }
        });

    }

    private void loadData(int i){
        if(i==0){
            notesViewModel.getAllNotes.observe(this, new Observer<List<NotesModel>>() {
                @Override
                public void onChanged(List<NotesModel> notesModels) {
                    setAdapter(notesModels);
                    filterNotesAllList = notesModels;
                }
            });
        }else if(i==1){
            notesViewModel.hightolow.observe(this, new Observer<List<NotesModel>>() {
                @Override
                public void onChanged(List<NotesModel> notesModels) {
                    setAdapter(notesModels);
                    filterNotesAllList = notesModels;
                }
            });
        }else if(i==2){
            notesViewModel.lowtohigh.observe(this, new Observer<List<NotesModel>>() {
                @Override
                public void onChanged(List<NotesModel> notesModels) {
                    setAdapter(notesModels);
                    filterNotesAllList = notesModels;
                }
            });
        }
    }

    public void setAdapter(List<NotesModel> notesModels){
        notesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new NotesAdapter(MainActivity.this, notesModels);
        notesRecycler.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchnav_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.nav_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void NotesFilter(String newText) {
    //  Log.e("@@@@", "NotesFilter: "+newText);
        ArrayList<NotesModel> FilterNames = new ArrayList<>();
        for (NotesModel notesModel : this.filterNotesAllList){
            if(notesModel.notesTitle.toLowerCase().contains(newText.toLowerCase()) ||
                notesModel.notesSubtitle.toLowerCase().contains(newText.toLowerCase())
            ){
                FilterNames.add(notesModel);
            }
        }

        this.adapter.searchNotes(FilterNames);
    }
}
