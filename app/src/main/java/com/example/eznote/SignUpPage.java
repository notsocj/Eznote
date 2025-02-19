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

public class SignUpPage extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, nicknameEditText;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dbHelper = new DBHelper(this);

        super.onCreate(savedInstanceState);
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

        Button registerButton = findViewById(R.id.signupbutton);
        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String nickname = nicknameEditText.getText().toString();

            dbHelper.addUser(email,password,nickname);
            Toast.makeText(this, "Registered!", Toast.LENGTH_SHORT).show();
        });


        Intent intent = getIntent();
        TextView textview = findViewById(R.id.LoginLink);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginPage = new Intent(SignUpPage.this, MainActivity.class);
                startActivity(LoginPage);
            }
        });

    }
}