# Seminar Registration App - UTS Pemrograman Mobile 1 Lukman Muhammad Faza

Aplikasi Pendaftaran Seminar Mahasiswa berbasis Android yang dibangun menggunakan **Jetpack Compose** dan **Material Design 3**. Aplikasi ini dirancang dengan antarmuka modern menggunakan tema *Glassmorphism* dan gradasi warna biru yang konsisten.

## 🚀 Fitur Utama
- **Autentikasi**: Fitur Login dan Register untuk keamanan akses.
- **Dashboard Utama**: Menampilkan profil singkat pengguna dan kartu informasi event.
- **Form Pendaftaran Seminar**:
  - Input: Nama, Email, No. WhatsApp, Jenis Kelamin (chek box), dan Topik Seminar (Dropdown).
  - **Validasi Real-time**: Error muncul saat mengetik jika data tidak valid (Email, Phone, Required Fields).
  - **Responsif**: Layout menyesuaikan ukuran layar (HP & Tablet).
- **Dialog Konfirmasi**: Memastikan data sudah benar sebelum dikirim.
- **Halaman Hasil**: Ringkasan data pendaftaran setelah berhasil submit.
- **Profil & Pengaturan**: Dilengkapi dengan menu pengaturan profil dan fitur **Logout**.
- **Bottom Navigation**: Navigasi cepat antara Home, Form Daftar, dan Profil.

## 🛠️ Tech Stack
- **Language**: Java
- **UI Framework**: Jetpack Compose (Material 3)
- **Navigation**: Jetpack Navigation Compose
- **Design Pattern**: Single Activity Architecture


## 🎥 Video Penjelasan (Link)
[Klik di sini untuk menonton video penjelasan kode dan UI](https://youtu.be/w-_1TdKL3WM?si=IqMj0fOdo6MSideQ)

---


## 📂 Struktur Proyek
- `HomeActivity.java`: Awal aplikasi.
- `ResultActivity.java`: Data class untuk menampung informasi pendaftaran.
- `layout/`: Folder berisi semua halaman UI (Login, Register, Home, Form, Result).
- `ui/theme/`: Konfigurasi tema, warna, tipografi, dan background gradasi.

## ⚙️ Cara Menjalankan
1. Clone repository ini.
2. Buka di **Android Studio Ladybug** atau versi terbaru.
3. Lakukan **Gradle Sync**.
4. Run pada Emulator atau Perangkat Fisik (Android API 24+).

---
**Create by:** [Lukman Muhammad Faza]
