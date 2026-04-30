package com.example.seminarapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ResultActivity extends AppCompatActivity {

    private TextView tvResultNama, tvResultEmail, tvResultPhone, tvResultGender, tvResultSeminar;
    private MaterialButton btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResultNama = findViewById(R.id.tvResultNama);
        tvResultEmail = findViewById(R.id.tvResultEmail);
        tvResultPhone = findViewById(R.id.tvResultPhone);
        tvResultGender = findViewById(R.id.tvResultGender);
        tvResultSeminar = findViewById(R.id.tvResultSeminar);
        btnBackHome = findViewById(R.id.btnBackHome);

        // Ambil data dari Intent
        Intent intent = getIntent();
        tvResultNama.setText(intent.getStringExtra("nama"));
        tvResultEmail.setText(intent.getStringExtra("email"));
        tvResultPhone.setText(intent.getStringExtra("phone"));
        tvResultGender.setText(intent.getStringExtra("gender"));
        tvResultSeminar.setText(intent.getStringExtra("seminar"));

        btnBackHome.setOnClickListener(v -> {
            Intent homeIntent = new Intent(this, HomeActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        finish();
    }
}
