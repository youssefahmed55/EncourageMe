package com.encourage.encourageme.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.encourageme.databinding.ActivitySplashBinding;

public class Splash extends AppCompatActivity {
 private ActivitySplashBinding binding;
 private SharedPreferences sharedPreferences;
 private View view;
    private static final String TAG = "Splash";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             try {
                 sharedPreferences = getSharedPreferences("data1",MODE_PRIVATE);
                 if(sharedPreferences.getBoolean("mykey1",false)){
                     Intent intent = new Intent(Splash.this,MainActivity.class);
                     startActivity(intent);
                     finish();
                 }else{
                     Intent intent = new Intent(Splash.this,Intro.class);
                     startActivity(intent);
                     finish();
                 }
             }catch (Exception e){
                Log.d(TAG, "run: "+e.getMessage());
            }


            }
        },2000);
    }
}