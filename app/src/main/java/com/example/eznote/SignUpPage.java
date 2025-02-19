package com.example.eznote;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class SignUpPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailEditText, passwordEditText, nicknameEditText;
    private Button registerButton;
    private DBHelper dbHelper;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailEditText = findViewById(R.id.emailfield);
        passwordEditText = findViewById(R.id.passwordfield);
        nicknameEditText = findViewById(R.id.nicknamefield);
        registerButton = findViewById(R.id.signupbutton);

        registerButton.setOnClickListener(view -> {
            String username = nicknameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(SignUpPage.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            registerUser(username, email, password);
        });

        TextView textview = findViewById(R.id.LoginLink);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginPage = new Intent(SignUpPage.this, MainActivity.class);
                startActivity(LoginPage);
            }
        });


    }

    public void registerUser(String username, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveUserToDatabase(user.getUid(), username, email);
                            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void saveUserToDatabase(String userId, String username, String email) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", username);
        userMap.put("email", email);

        db.collection("users").document(userId)
                .set(userMap)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(SignUpPage.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                    // Redirect to login screen
                    Intent intent = new Intent(SignUpPage.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SignUpPage.this, "Failed to save user: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}

