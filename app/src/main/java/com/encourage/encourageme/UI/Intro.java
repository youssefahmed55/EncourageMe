package com.encourage.encourageme.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.encourageme.databinding.ActivityIntroBinding;

public class Intro extends AppCompatActivity {
    private ActivityIntroBinding binding;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.intobuttonIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("data1",MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("mykey1",true).apply();
                Intent intent = new Intent(Intro.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            });

    }
}