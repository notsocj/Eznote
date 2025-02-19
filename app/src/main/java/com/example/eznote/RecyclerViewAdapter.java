package com.example.eznote;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<Model> modelArrayList;
    private Context context;

    public RecyclerViewAdapter(ArrayList<Model> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = modelArrayList.get(position);

        if (model.getName() != null && !model.getName().isEmpty()) {
            holder.profileLetter.setText(model.getName().substring(0, 1));
        } else {
            holder.profileLetter.setText("?");
        }

        holder.notesName.setText(model.getName());
        holder.notesOverview.setText(model.getNumber());
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView profileLetter, notesName, notesOverview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileLetter = itemView.findViewById(R.id.profileletter);
            notesName = itemView.findViewById(R.id.notesName);
            notesOverview = itemView.findViewById(R.id.notesOverview);


        }
    }
}
