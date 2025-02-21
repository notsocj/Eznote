package com.example.eznote;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    FloatingActionButton addnotes;
    private RecyclerViewAdapter adapter;
    private ArrayList<Model> modelArrayList;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private FirebaseUser user;
    private TextView nicknameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        // ✅ Initialize FirebaseAuth and Firestore first
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // ✅ Initialize Views
        recyclerView = findViewById(R.id.recyclerView);
        nicknameTextView = findViewById(R.id.welcometext);
        addnotes = findViewById(R.id.addnotes);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            fetchUsername(user.getUid());
        } else {
            // Redirect to login if user is not authenticated
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        // ✅ Initialize modelArrayList before passing it to the adapter
        modelArrayList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(modelArrayList, this);

        // ✅ Now it's safe to set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Fetch Notes
        fetchNotes();

        // Add Note Button
        addnotes.setOnClickListener(v -> startActivity(new Intent(HomePage.this, AddNoteActivity.class)));
        adapter.setOnNoteClickListener(note -> {
            EditNoteDialog dialog = EditNoteDialog.newInstance(note.getName(), note.getContent(), note.getDocId());
            dialog.show(getSupportFragmentManager(), "EditNoteDialog");
        });

    }

    private void fetchUsername(String userId) {
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String username = documentSnapshot.getString("username");
                        if (username != null) {
                            nicknameTextView.setText("Welcome, " + username + "!");
                        } else {
                            Toast.makeText(HomePage.this, "Username field missing!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(HomePage.this, "User document does not exist!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(HomePage.this, "Firestore Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }


    private void fetchNotes() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(userId).collection("notes")
                .orderBy("timestamp")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Firestore Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (value == null || value.isEmpty()) {
                        Toast.makeText(this, "No notes found!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    modelArrayList.clear();

                    for (var doc : value.getDocuments()) {
                        String title = doc.getString("title");
                        String number = doc.getString("number");
                        String content = doc.getString("content");
                        String docId = doc.getId();
                        modelArrayList.add(new Model(title, number, content, docId));
                    }

                    adapter.notifyDataSetChanged();
                });
    }
}
