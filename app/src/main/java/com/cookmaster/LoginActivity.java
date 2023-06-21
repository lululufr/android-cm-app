package com.cookmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.button_login);
        usernameEditText = findViewById(R.id.editText_username);
        passwordEditText = findViewById(R.id.editText_password);

    loginButton.setOnClickListener(v -> {
        if (usernameEditText.getText().toString().equals("admin") && passwordEditText.getText().toString().equals("admin")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    });
    }
}
