package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.expensetracker.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding.textSplash.animate().translationX(1000).setDuration(1000).setStartDelay(2500);
        Thread thread=new Thread(){
            public void run(){
                try{
                    Thread.sleep(4000);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(SplashActivity.this,TodayActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}