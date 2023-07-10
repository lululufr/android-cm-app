package com.cookmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText usernameEditText;
    private EditText passwordEditText;

    private CheckBox checkBox;

    private ImageView logo;

    private String url = "https://cookmaster.lululu.fr/api/recipe/";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.button_login);
        usernameEditText = findViewById(R.id.editText_username);
        passwordEditText = findViewById(R.id.editText_password);
        checkBox = findViewById(R.id.checkBox);
        logo = findViewById(R.id.logoView);
        SharedPreferences savedIds = getSharedPreferences("savedIds", Context.MODE_PRIVATE);

        if (!savedIds.getString("username", "").isEmpty() && !savedIds.getString("password", "").isEmpty()) {
            checkBox.setChecked(true);
            usernameEditText.setText(savedIds.getString("username", ""));
            passwordEditText.setText(savedIds.getString("password", ""));
        }

        //A REUTILISER POUR LES REQUETES API
        /*loginButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               sendAndRequestResponse();
                                           }

                                        }
        );*/




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameEditText.getText().toString().equals("admin") && passwordEditText.getText().toString().equals("admin")) {
                    SharedPreferences.Editor edit = savedIds.edit();
                    if (checkBox.isChecked()) {
                        edit.putString("username", usernameEditText.getText().toString());
                        edit.putString("password", passwordEditText.getText().toString());
                    } else {
                        edit.remove("username");
                        edit.remove("password");
                    }
                    edit.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();


                }
            }
        });
    }
}
