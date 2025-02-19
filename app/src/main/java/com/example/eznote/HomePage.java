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
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    FloatingActionButton addnotes;
    private RecyclerViewAdapter adapter;
    private ArrayList<Model> modelArrayList;
    private RecyclerView recyclerView;

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

        nicknameTextView = findViewById(R.id.welcometext);
        Intent intent = getIntent();
        String nickname = intent.getStringExtra("nickname");

        if (nickname != null && !nickname.isEmpty()) {
            nicknameTextView.setText("Hi, " + nickname + "!");
        } else {
            nicknameTextView.setText("Welcome!");
        }

    }
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }


}