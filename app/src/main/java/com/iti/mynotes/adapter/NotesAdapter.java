package com.iti.mynotes.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.mynotes.R;
import com.iti.mynotes.activity.MainActivity;
import com.iti.mynotes.activity.UpdateNotesActivity;
import com.iti.mynotes.model.NotesModel;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder> {

    MainActivity mainActivity;
    List<NotesModel> notesModels;
    List<NotesModel> allNotesItem;
    public NotesAdapter(MainActivity mainActivity, List<NotesModel> notesModels) {
        this.mainActivity = mainActivity;
        this.notesModels = notesModels;
        allNotesItem = new ArrayList<>(notesModels);

    }

    public void searchNotes(List<NotesModel> filterredName){
        this.notesModels = filterredName;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new notesViewHolder(LayoutInflater.from(mainActivity).inflate(R.layout.item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull notesViewHolder holder, int position) {
        NotesModel note = notesModels.get(position);

        switch (note.notesPriority) {
            case "1":
                holder.notesPriority.setBackgroundResource(R.drawable.green_shape);
                break;
            case "2":
                holder.notesPriority.setBackgroundResource(R.drawable.yellow_shape);
                break;
            case "3":
                holder.notesPriority.setBackgroundResource(R.drawable.red_shape);
                break;
        }

        holder.title.setText(note.notesTitle);
        holder.subtitle.setText(note.notesSubtitle);
        holder.notesDate.setText(note.notesDate);

        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(mainActivity, UpdateNotesActivity.class);
            intent.putExtra("id", note.id);
            intent.putExtra("title", note.notesTitle);
            intent.putExtra("subtitle", note.notesSubtitle);
            intent.putExtra("note", note.notes);
            intent.putExtra("priority", note.notesPriority);

            mainActivity.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return notesModels.size();
    }

    public static class notesViewHolder extends RecyclerView.ViewHolder{
        TextView title, subtitle, notesDate;
        View notesPriority;

        public notesViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.notesTitle);
            subtitle = itemView.findViewById(R.id.notesSubtitle);
            notesDate = itemView.findViewById(R.id.notesDate);
            notesPriority = itemView.findViewById(R.id.notesPriority);

        }
    }
}
