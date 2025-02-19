package com.example.eznote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new DBHelper(this);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView signUpButton = findViewById(R.id.SignUpLink);
        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpPage.class);
            startActivity(intent);
        });

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.editTextPassword);

        Button loginButton = findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(v -> {
                    String email = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
            if (dbHelper.validateLogin(email, password)) {
                String nickname = dbHelper.getNickname(email);

                // After successful login, go to homepage
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                intent.putExtra("nickname", nickname);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show();
            }
        });

    }
}