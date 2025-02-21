package com.example.eznote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Model> notesList;
    private Context context;

    public RecyclerViewAdapter(List<Model> notesList, Context context) {
        this.notesList = notesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model note = notesList.get(position);
        holder.titleTextView.setText(note.getName());
        holder.contentTextView.setText(note.getNumber());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNoteClick(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void updateNotes(List<Model> newNotes) {
        this.notesList.clear();
        this.notesList.addAll(newNotes);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, contentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.notesName);
            contentTextView = itemView.findViewById(R.id.notesOverview);
        }
    }
    public interface OnNoteClickListener {
        void onNoteClick(Model note);
    }

    private OnNoteClickListener listener;

    public void setOnNoteClickListener(OnNoteClickListener listener) {
        this.listener = listener;
    }

}
