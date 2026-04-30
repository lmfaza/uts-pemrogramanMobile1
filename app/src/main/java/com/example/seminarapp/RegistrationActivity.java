package com.example.seminarapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputLayout tilNama, tilEmail, tilPhone, tilSeminar;
    private TextInputEditText etNama, etEmail, etPhone;
    private AutoCompleteTextView actvSeminar;
    private RadioGroup rgGender;
    private TextView tvGenderError, tvCheckboxError;
    private CheckBox cbAgreement;
    private MaterialButton btnSubmit;

    private String[] seminarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        tilNama = findViewById(R.id.tilNama);
        tilEmail = findViewById(R.id.tilEmail);
        tilPhone = findViewById(R.id.tilPhone);
        tilSeminar = findViewById(R.id.tilSeminar);
        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        actvSeminar = findViewById(R.id.actvSeminar);
        rgGender = findViewById(R.id.rgGender);
        tvGenderError = findViewById(R.id.tvGenderError);
        tvCheckboxError = findViewById(R.id.tvCheckboxError);
        cbAgreement = findViewById(R.id.cbAgreement);
        btnSubmit = findViewById(R.id.btnSubmit);

        setupSeminarDropdown();
        setupRealtimeValidation();

        btnSubmit.setOnClickListener(v -> validateAndSubmit());
    }

    private void setupSeminarDropdown() {
        seminarList = getResources().getStringArray(R.array.seminar_list);
        // Skip item pertama "-- Pilih Seminar --" saat display, tapi kita tetap tampilkan
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            seminarList
        );
        actvSeminar.setAdapter(adapter);
        actvSeminar.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 0) {
                actvSeminar.setText("");
                tilSeminar.setError("Pilih seminar terlebih dahulu");
            } else {
                tilSeminar.setError(null);
            }
        });
    }

    private void setupRealtimeValidation() {
        etNama.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                tilNama.setError(s.toString().trim().isEmpty() ? "Nama tidak boleh kosong" : null);
            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String email = s.toString().trim();
                if (email.isEmpty()) {
                    tilEmail.setError("Email tidak boleh kosong");
                } else if (!email.contains("@")) {
                    tilEmail.setError("Email harus mengandung '@'");
                } else {
                    tilEmail.setError(null);
                }
            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                validatePhone(s.toString().trim(), true);
            }
        });

        rgGender.setOnCheckedChangeListener((group, checkedId) -> {
            tvGenderError.setVisibility(View.GONE);
        });

        cbAgreement.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) tvCheckboxError.setVisibility(View.GONE);
        });
    }

    private boolean validatePhone(String phone, boolean realtimeOnly) {
        if (phone.isEmpty()) {
            tilPhone.setError(realtimeOnly ? "Nomor HP tidak boleh kosong" : "Nomor HP tidak boleh kosong");
            return false;
        }
        if (!phone.matches("[0-9]+")) {
            tilPhone.setError("Nomor HP hanya boleh angka");
            return false;
        }
        if (!phone.startsWith("08")) {
            tilPhone.setError("Nomor HP harus diawali dengan 08");
            return false;
        }
        if (phone.length() < 10 || phone.length() > 13) {
            tilPhone.setError("Nomor HP harus 10-13 digit");
            return false;
        }
        tilPhone.setError(null);
        return true;
    }

    private void validateAndSubmit() {
        String nama = etNama.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String seminar = actvSeminar.getText().toString().trim();
        boolean valid = true;

        // Validasi Nama
        if (nama.isEmpty()) {
            tilNama.setError("Nama tidak boleh kosong");
            valid = false;
        }

        // Validasi Email
        if (email.isEmpty()) {
            tilEmail.setError("Email tidak boleh kosong");
            valid = false;
        } else if (!email.contains("@")) {
            tilEmail.setError("Email harus mengandung '@'");
            valid = false;
        }

        // Validasi Phone
        if (!validatePhone(phone, false)) {
            valid = false;
        }

        // Validasi Gender
        if (rgGender.getCheckedRadioButtonId() == -1) {
            tvGenderError.setVisibility(View.VISIBLE);
            valid = false;
        }

        // Validasi Seminar
        if (seminar.isEmpty() || seminar.equals("-- Pilih Seminar --")) {
            tilSeminar.setError("Pilih seminar terlebih dahulu");
            valid = false;
        }

        // Validasi Checkbox
        if (!cbAgreement.isChecked()) {
            tvCheckboxError.setVisibility(View.VISIBLE);
            valid = false;
        }

        if (!valid) return;

        // Ambil gender
        String gender = (rgGender.getCheckedRadioButtonId() == R.id.rbMale) ? "Laki-laki" : "Perempuan";

        showConfirmationDialog(nama, email, phone, gender, seminar);
    }

    private void showConfirmationDialog(String nama, String email, String phone, String gender, String seminar) {
        String message = "Nama: " + nama + "\n"
            + "Email: " + email + "\n"
            + "Nomor HP: " + phone + "\n"
            + "Jenis Kelamin: " + gender + "\n"
            + "Seminar: " + seminar;

        new AlertDialog.Builder(this)
            .setTitle("Konfirmasi Data")
            .setMessage("Apakah data yang Anda isi sudah benar?\n\n" + message)
            .setPositiveButton("Ya, Lanjutkan", (dialog, which) -> {
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("nama", nama);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("gender", gender);
                intent.putExtra("seminar", seminar);
                startActivity(intent);
                finish();
            })
            .setNegativeButton("Tidak, Kembali", null)
            .show();
    }
}
