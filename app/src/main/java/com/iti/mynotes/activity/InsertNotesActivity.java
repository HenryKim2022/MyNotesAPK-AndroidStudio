package com.iti.mynotes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.widget.Toast;

import com.iti.mynotes.R;
import com.iti.mynotes.model.NotesModel;
import com.iti.mynotes.databinding.ActivityInsertNotesBinding;
import com.iti.mynotes.model.v_model.NotesViewModel;

import java.util.Date;
import java.util.Objects;

public class InsertNotesActivity extends AppCompatActivity {

    ActivityInsertNotesBinding binding;
    String title, subtitle, notes;
    NotesViewModel notesViewModel;
    String priority = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        binding.greenPrior.setOnClickListener(v -> {
            binding.greenPrior.setImageResource(R.drawable.baseline_done_24);
            binding.yellowPrior.setImageResource(0);
            binding.redPrior.setImageResource(0);

            priority = "1";
        });
        binding.yellowPrior.setOnClickListener(v -> {
            binding.greenPrior.setImageResource(0);
            binding.yellowPrior.setImageResource(R.drawable.baseline_done_24);
            binding.redPrior.setImageResource(0);

            priority = "2";
        });
        binding.redPrior.setOnClickListener(v -> {
            binding.greenPrior.setImageResource(0);
            binding.yellowPrior.setImageResource(0);
            binding.redPrior.setImageResource(R.drawable.baseline_done_24);

            priority = "3";
        });

        binding.doneNotesBtn.setOnClickListener(v -> {
            title = binding.notesTitle.getText().toString();
            subtitle = binding.notesSubtitle.getText().toString();
            notes = binding.notesData.getText().toString();

            CreateNotes("New notes created!",title, subtitle, notes);
        });
    }

    private void CreateNotes(String toastMessage, String title, String subtitle, String notes) {
        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        NotesModel notes1 = new NotesModel();
        notes1.notesTitle = title;
        if (notes1.notesTitle.equals("")){
            notes1.notesTitle = "Untitled";
        }
        notes1.notesSubtitle = subtitle;
        if (notes1.notesSubtitle.equals("")){
            notes1.notesSubtitle = "Subtitle";
        }
        notes1.notes = notes;
        notes1.notesPriority = priority;
        notes1.notesDate = sequence.toString();

        notesViewModel.insertNotes(notes1);

        if (!Objects.equals(toastMessage, "")) {
            Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
            finish();
        }else{
            finish();
        }

    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        // Do Autosave when activity is Destroyed
//        title = binding.notesTitle.getText().toString();
//        subtitle = binding.notesSubtitle.getText().toString();
//        notes = binding.notesData.getText().toString();
//
//        CreateNotes("",title, subtitle, notes);
//    }




}
