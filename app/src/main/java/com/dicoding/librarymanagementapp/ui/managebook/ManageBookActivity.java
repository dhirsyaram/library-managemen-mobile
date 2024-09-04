package com.dicoding.librarymanagementapp.ui.managebook;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dicoding.librarymanagementapp.R;
import com.dicoding.librarymanagementapp.databinding.ActivityManageBookBinding;

public class ManageBookActivity extends AppCompatActivity {

    private ActivityManageBookBinding manageBookBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        manageBookBinding = ActivityManageBookBinding.inflate(getLayoutInflater());
        setContentView(manageBookBinding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(manageBookBinding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}