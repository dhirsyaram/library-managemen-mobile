package com.dicoding.librarymanagementapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dicoding.librarymanagementapp.R;
import com.dicoding.librarymanagementapp.ui.addbook.AddBookActivity;
import com.dicoding.librarymanagementapp.data.pref.LoginPreferences;
import com.dicoding.librarymanagementapp.databinding.ActivityMainBinding;
import com.dicoding.librarymanagementapp.ui.listbook.ListBookActivity;
import com.dicoding.librarymanagementapp.ui.login.LoginActivity;
import com.dicoding.librarymanagementapp.ui.managebook.ManageBookActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding mainBind;
    private LoginPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mainBind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBind.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(mainBind.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pref = new LoginPreferences(this);

        mainBind.btnShowListBook.setOnClickListener(this);
        mainBind.btnAddBook.setOnClickListener(this);
        mainBind.btnManageBook.setOnClickListener(this);
        mainBind.logoutMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_show_list_book) {
            Intent intent = new Intent(MainActivity.this, ListBookActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_add_book) {
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_manage_book) {
            Intent intent = new Intent(MainActivity.this, ManageBookActivity.class);
            startActivity(intent);
        } else {
            performLogout();
        }
    }

    private void performLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Keluar")
                .setMessage("Apakah Kamu Yakin untuk Keluar?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    pref.clearLoginDetails();
                    startActivity(intent);
                    Toast.makeText(this, "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                    finish();
                }).show();
    }
}