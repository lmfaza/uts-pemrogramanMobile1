package com.example.seminarapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class HomeActivity extends AppCompatActivity {

    private TextView tvWelcomeName;
    private MaterialButton btnDaftarSeminar, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvWelcomeName = findViewById(R.id.tvWelcomeName);
        btnDaftarSeminar = findViewById(R.id.btnDaftarSeminar);
        btnLogout = findViewById(R.id.btnLogout);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String name = prefs.getString("loggedInName", "Pengguna");
        tvWelcomeName.setText(name);

        btnDaftarSeminar.setOnClickListener(v -> {
            startActivity(new Intent(this, RegistrationActivity.class));
        });

        btnLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Konfirmasi Keluar")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya, Keluar", (dialog, which) -> {
                SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                prefs.edit().putBoolean("isLoggedIn", false).apply();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            })
            .setNegativeButton("Batal", null)
            .show();
    }

    @Override
    public void onBackPressed() {
        showLogoutDialog();
    }
}
