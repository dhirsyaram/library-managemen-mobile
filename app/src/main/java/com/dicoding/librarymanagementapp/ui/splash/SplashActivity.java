package com.dicoding.librarymanagementapp.ui.splash;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dicoding.librarymanagementapp.R;
import com.dicoding.librarymanagementapp.databinding.ActivitySplashBinding;
import com.dicoding.librarymanagementapp.ui.login.LoginActivity;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private LinearProgressIndicator pbSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pbSplash = binding.progressSplash;

        ObjectAnimator pbAnimator = ObjectAnimator.ofInt(pbSplash, "progress", 0, 100);
        pbAnimator.setDuration(2000);
        pbAnimator.start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intentMain = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intentMain);
            finish();
        }, 5000L);
    }
}