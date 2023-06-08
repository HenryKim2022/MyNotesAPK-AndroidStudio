package com.iti.mynotes.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.iti.mynotes.R;
import com.iti.mynotes.model.NotesModel;
import com.iti.mynotes.databinding.ActivityUpdateNotesBinding;
import com.iti.mynotes.model.v_model.NotesViewModel;

import java.util.Date;
import java.util.Objects;

public class UpdateNotesActivity extends AppCompatActivity {
    ActivityUpdateNotesBinding binding;

    NotesViewModel notesViewModel;
    String priority = "1";
    Integer iid;
    String stitle, ssubtitle, snotes, spriority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iid = getIntent().getIntExtra("id", 0);
        stitle = getIntent().getStringExtra("title");
        ssubtitle = getIntent().getStringExtra("subtitle");
        snotes = getIntent().getStringExtra("note");
        spriority = getIntent().getStringExtra("priority");

        binding.upNotesTitle.setText(stitle);
        binding.upNotesSubtitle.setText(ssubtitle);
        binding.upNotesData.setText(snotes);

        if(spriority.equals("1")){
            binding.greenPrior.setImageResource(R.drawable.baseline_done_24);
            priority = "1";
        }else if(spriority.equals("2")){
            binding.yellowPrior.setImageResource(R.drawable.baseline_done_24);
            priority = "2";
        }else if(spriority.equals("3")){
            binding.redPrior.setImageResource(R.drawable.baseline_done_24);
            priority = "3";
        }

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


        binding.upNotesBtn.setOnClickListener(v->{
            String title = binding.upNotesTitle.getText().toString();
            String subtitle = binding.upNotesSubtitle.getText().toString();
            String notes = binding.upNotesData.getText().toString();

            UpdateNotes("Notes updated!", title, subtitle, notes);
        });

    }

    private void UpdateNotes(String toastMessage, String title, String subtitle, String notes) {
        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        NotesModel updateNotes = new NotesModel();
        updateNotes.id = iid;
        updateNotes.notesTitle = title;
        if (updateNotes.notesTitle.equals("")){
            updateNotes.notesTitle = "Untitled";
        }
        updateNotes.notesSubtitle = subtitle;
        if (updateNotes.notesSubtitle.equals("")){
            updateNotes.notesSubtitle = "Subtitle";
        }
        updateNotes.notes = notes;
        updateNotes.notesPriority = priority;
        updateNotes.notesDate = sequence.toString();

        notesViewModel.updateNotes(updateNotes);


        if (!Objects.equals(toastMessage, "")) {
            Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
            finish();
        }else{
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.delnav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_del){
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNotesActivity.this,
                    R.style.BottomSheetStyle);

            View view = LayoutInflater.from(UpdateNotesActivity.this).
                    inflate(R.layout.delete_confirmation, (LinearLayout) findViewById(R.id.bottom_confirm_layout));
            sheetDialog.setContentView(view);

            TextView yestv, notv;

            yestv = view.findViewById(R.id.delYesBtn);
            notv = view.findViewById(R.id.delNoBtn);

            yestv.setOnClickListener(v -> {
                notesViewModel.deleteNotes(iid);

                Toast toast = Toast.makeText(getApplicationContext(), "Notes deleted!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();

                finish();
            });
            notv.setOnClickListener(v -> {
                sheetDialog.dismiss();
            });

            sheetDialog.show();
        }
        return true;
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Do Autosave when activity is Destroyed
//        String title = binding.upNotesTitle.getText().toString();
//        String subtitle = binding.upNotesSubtitle.getText().toString();
//        String notes = binding.upNotesData.getText().toString();
//
//        UpdateNotes("", title, subtitle, notes);
//    }
}
