package com.dicoding.librarymanagementapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dicoding.librarymanagementapp.R;
import com.dicoding.librarymanagementapp.data.pref.LoginPreferences;
import com.dicoding.librarymanagementapp.databinding.ActivityLoginBinding;
import com.dicoding.librarymanagementapp.ui.main.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding bindLogin;
    private MaterialButton btnMasuk;
    private TextInputEditText editTextEmail, editTextPass;
    private LoginPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        bindLogin = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(bindLogin.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(bindLogin.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnMasuk = bindLogin.btnLogin;
        editTextEmail = bindLogin.edtEmail;
        editTextPass = bindLogin.edtPass;

        pref = new LoginPreferences(this);

        btnMasuk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            performLogin();
        }
    }

    private void performLogin() {
        String email = Objects.requireNonNull(editTextEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(editTextPass.getText()).toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Silakan Mengisi Email & Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.equals("dhir@admin.com") && password.equals("admin01")) {
            pref.saveLoginDetails(email);
            Toast.makeText(this, "Berhasil Masuk", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Email atau Password Tidak Valid", Toast.LENGTH_SHORT).show();
        }
    }
}