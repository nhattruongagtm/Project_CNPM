package com.example.project_cnpm;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;

public class FlashScreen extends AppCompatActivity {
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);


        lottieAnimationView = findViewById(R.id.lottie_flash_screen);
        lottieAnimationView.setAnimation("food-loading.json");
        lottieAnimationView.playAnimation();
        lottieAnimationView.setRepeatCount(ValueAnimator.INFINITE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(FlashScreen.this,MainActivity.class));
                overridePendingTransition(R.anim.load_in,R.anim.load_out);
                finish();
            }
        },3000);
    }
}