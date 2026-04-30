package com.example.seminarapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilUsername, tilPassword;
    private TextInputEditText etUsername, etPassword;
    private MaterialButton btnLogin;
    private TextView tvGoToRegister;

    // Data hardcode
    private static final String[][] HARDCODED_USERS = {
        {"admin", "admin123", "Administrator"},
        {"mahasiswa", "pass123", "Budi Santoso"},
        {"user1", "user1234", "Andi Pratama"},
        {"demo", "demo1234", "Demo User"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cek apakah sudah login
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        if (prefs.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        tilUsername = findViewById(R.id.tilUsername);
        tilPassword = findViewById(R.id.tilPassword);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToRegister = findViewById(R.id.tvGoToRegister);

        setupRealtimeValidation();

        btnLogin.setOnClickListener(v -> doLogin());

        tvGoToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void setupRealtimeValidation() {
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    tilUsername.setError("Username tidak boleh kosong");
                } else {
                    tilUsername.setError(null);
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    tilPassword.setError("Password tidak boleh kosong");
                } else {
                    tilPassword.setError(null);
                }
            }
        });
    }

    private void doLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        boolean valid = true;

        if (username.isEmpty()) {
            tilUsername.setError("Username tidak boleh kosong");
            valid = false;
        } else {
            tilUsername.setError(null);
        }

        if (password.isEmpty()) {
            tilPassword.setError("Password tidak boleh kosong");
            valid = false;
        } else {
            tilPassword.setError(null);
        }

        if (!valid) return;

        // Cek akun hardcode
        String loggedInName = null;
        for (String[] user : HARDCODED_USERS) {
            if (user[0].equals(username) && user[1].equals(password)) {
                loggedInName = user[2];
                break;
            }
        }

        // Cek akun dari Register
        if (loggedInName == null) {
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String savedUsername = prefs.getString("reg_username_" + username, null);
            String savedPassword = prefs.getString("reg_password_" + username, null);
            String savedName = prefs.getString("reg_name_" + username, null);

            if (savedUsername != null && savedPassword != null && savedPassword.equals(password)) {
                loggedInName = savedName;
            }
        }

        if (loggedInName != null) {
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            prefs.edit()
                .putBoolean("isLoggedIn", true)
                .putString("loggedInName", loggedInName)
                .apply();

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Username atau password salah!", Toast.LENGTH_SHORT).show();
            tilPassword.setError("Username atau password salah");
        }
    }
}
