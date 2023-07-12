package com.cookmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    private CheckBox checkBox;

    private final String API_URL = "https://cookmaster.lululu.fr/api/connexion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.button_login);
        usernameEditText = findViewById(R.id.editText_username);
        passwordEditText = findViewById(R.id.editText_password);
        checkBox = findViewById(R.id.checkBox);
        SharedPreferences savedIds = getSharedPreferences("savedIds", Context.MODE_PRIVATE);

        if (!savedIds.getString("username", "").isEmpty() && !savedIds.getString("password", "").isEmpty()) {
            checkBox.setChecked(true);
            usernameEditText.setText(savedIds.getString("username", ""));
            passwordEditText.setText(savedIds.getString("password", ""));
        }
        else if (!savedIds.getString("username", "").isEmpty()){
            usernameEditText.setText(savedIds.getString("username", ""));
        }
        SharedPreferences.Editor edit = savedIds.edit();
        edit.remove("token");
        edit.remove("role");
        edit.remove("id");
        edit.apply();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()) {
                    if (usernameEditText.getText().toString().isEmpty()) {
                        usernameEditText.setError("Veuillez entrer un nom d'utilisateur.");
                    }
                    if (passwordEditText.getText().toString().isEmpty()) {
                        passwordEditText.setError("Veuillez entrer un mot de passe.");
                    }
                    return;
                }

                    RequestQueue queue = Volley.newRequestQueue(getBaseContext());

                    StringRequest request = new StringRequest(Request.Method.POST, API_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        SharedPreferences.Editor edit = savedIds.edit();
                                        edit.putString("token", jsonResponse.getString("token"));
                                        edit.putString("role", jsonResponse.getString("role"));
                                        edit.putInt("id", jsonResponse.getInt("id"));
                                        edit.putString("username", usernameEditText.getText().toString());
                                        if (checkBox.isChecked()) {
                                            edit.putString("password", passwordEditText.getText().toString());
                                        } else {
                                            edit.remove("password");
                                        }
                                        edit.apply();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getBaseContext(), "Erreur lors de la connexion.", Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", usernameEditText.getText().toString());
                            params.put("password", passwordEditText.getText().toString());
                            return params;
                        }
                    };


                    queue.add(request);
                }
        });
    }
}
