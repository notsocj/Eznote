package com.example.eznote;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
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

    private TextView nicknameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        modelArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);

        modelArrayList.add(new Model("To do list for today", "Wash Dish...."));
        modelArrayList.add(new Model("Terquin bobo", "Overview of the Content"));
        modelArrayList.add(new Model("Mooaw", "OverView of the Content"));
        modelArrayList.add(new Model("Name of the Note 4", "OverView of the Content"));
        modelArrayList.add(new Model("Name of the Note 5 ", "OverView of the Content"));
        modelArrayList.add(new Model("Name of the Note 6", "OverView of the Content"));
        modelArrayList.add(new Model("Name of the Note 7", "OverView of the Content"));
        modelArrayList.add(new Model("Name of the Note 8", "OverView of the Content"));
        modelArrayList.add(new Model("Name of the Note 9", "OverView of the Content"));
        modelArrayList.add(new Model("Name of the Note 10", "OverView of the Content"));
        modelArrayList.add(new Model("Name of the Note 11", "OverView of the Content"));
        modelArrayList.add(new Model("Name of the Note 12", "OverView of the Content"));
        modelArrayList.add(new Model("Name of the Note 13", "OverView of the Content"));
        modelArrayList.add(new Model("Name of the Note 14", "OverView of the Content"));
        modelArrayList.add(new Model("Name of the Note 15", "OverView of the Content"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(modelArrayList, this);
        recyclerView.setAdapter(adapter);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        nicknameTextView = findViewById(R.id.welcometext);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            fetchUsername(user.getUid());
        }

    }
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }

    private void fetchUsername(String userId) {
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String username = documentSnapshot.getString("username");
                        nicknameTextView.setText("Welcome, " + username + "!");
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(HomePage.this, "Failed to fetch username", Toast.LENGTH_SHORT).show();
                });
    }


}