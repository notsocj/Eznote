package com.example.eznote;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditNoteDialog extends DialogFragment {
    private EditText editTitle, editContent;
    private String noteId;

    public static EditNoteDialog newInstance(String title, String content, String noteId) {
        EditNoteDialog fragment = new EditNoteDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("content", content);
        args.putString("noteId", noteId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_note, null);

        editTitle = view.findViewById(R.id.editTitle);
        editContent = view.findViewById(R.id.editContent);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        if (getArguments() != null) {
            editTitle.setText(getArguments().getString("title"));
            editContent.setText(getArguments().getString("content"));
            noteId = getArguments().getString("noteId");
        }

        btnSave.setOnClickListener(v -> updateNote());
        btnCancel.setOnClickListener(v -> dismiss());

        builder.setView(view);
        return builder.create();
    }

    private void updateNote() {
        String newTitle = editTitle.getText().toString().trim();
        String newContent = editContent.getText().toString().trim();

        if (newTitle.isEmpty() || newContent.isEmpty()) {
            Toast.makeText(getActivity(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getUid()).collection("notes").document(noteId)
                .update("title", newTitle, "content", newContent)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getActivity(), "Note updated!", Toast.LENGTH_SHORT).show();
                    dismiss();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getActivity(), "Failed to update note!", Toast.LENGTH_SHORT).show()
                );
    }
}
