package com.example.seminarapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout tilFullName, tilRegUsername, tilRegPassword, tilConfirmPassword;
    private TextInputEditText etFullName, etRegUsername, etRegPassword, etConfirmPassword;
    private MaterialButton btnRegister;
    private TextView tvGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tilFullName = findViewById(R.id.tilFullName);
        tilRegUsername = findViewById(R.id.tilRegUsername);
        tilRegPassword = findViewById(R.id.tilRegPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        etFullName = findViewById(R.id.etFullName);
        etRegUsername = findViewById(R.id.etRegUsername);
        etRegPassword = findViewById(R.id.etRegPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvGoToLogin = findViewById(R.id.tvGoToLogin);

        setupRealtimeValidation();

        btnRegister.setOnClickListener(v -> doRegister());

        tvGoToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void setupRealtimeValidation() {
        etFullName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                tilFullName.setError(s.toString().trim().isEmpty() ? "Nama tidak boleh kosong" : null);
            }
        });

        etRegUsername.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                tilRegUsername.setError(s.toString().trim().isEmpty() ? "Username tidak boleh kosong" : null);
            }
        });

        etRegPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String pass = s.toString().trim();
                if (pass.isEmpty()) {
                    tilRegPassword.setError("Password tidak boleh kosong");
                } else if (pass.length() < 6) {
                    tilRegPassword.setError("Password minimal 6 karakter");
                } else {
                    tilRegPassword.setError(null);
                }
            }
        });

        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String pass = etRegPassword.getText().toString();
                String confirm = s.toString();
                if (confirm.isEmpty()) {
                    tilConfirmPassword.setError("Konfirmasi password tidak boleh kosong");
                } else if (!confirm.equals(pass)) {
                    tilConfirmPassword.setError("Password tidak sama");
                } else {
                    tilConfirmPassword.setError(null);
                }
            }
        });
    }

    private void doRegister() {
        String name = etFullName.getText().toString().trim();
        String username = etRegUsername.getText().toString().trim();
        String password = etRegPassword.getText().toString().trim();
        String confirmPass = etConfirmPassword.getText().toString().trim();
        boolean valid = true;

        if (name.isEmpty()) {
            tilFullName.setError("Nama tidak boleh kosong");
            valid = false;
        }
        if (username.isEmpty()) {
            tilRegUsername.setError("Username tidak boleh kosong");
            valid = false;
        }
        if (password.isEmpty()) {
            tilRegPassword.setError("Password tidak boleh kosong");
            valid = false;
        } else if (password.length() < 6) {
            tilRegPassword.setError("Password minimal 6 karakter");
            valid = false;
        }
        if (confirmPass.isEmpty()) {
            tilConfirmPassword.setError("Konfirmasi password tidak boleh kosong");
            valid = false;
        } else if (!confirmPass.equals(password)) {
            tilConfirmPassword.setError("Password tidak sama");
            valid = false;
        }

        if (!valid) return;

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        if (prefs.contains("reg_username_" + username)) {
            tilRegUsername.setError("Username sudah digunakan");
            return;
        }

        prefs.edit()
            .putString("reg_username_" + username, username)
            .putString("reg_password_" + username, password)
            .putString("reg_name_" + username, name)
            .apply();

        Toast.makeText(this, "Registrasi berhasil! Silakan login.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
