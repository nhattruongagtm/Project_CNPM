package com.example.project_cnpm.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.project_cnpm.MainActivity;
import com.example.project_cnpm.R;
import com.example.project_cnpm.SignUp.SignUpActivity;

public class LoginActivity extends AppCompatActivity {

//    private LottieAnimationView lottieAnimationView;

    TextView btnChangeSignUp;
    LinearLayout btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnChangeSignUp = findViewById(R.id.changeSignUp);
        btnBack = findViewById(R.id.btnBack_hompage);

        btnChangeSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

//        lottieAnimationView = findViewById(R.id.lottie_login);
//        lottieAnimationView.setAnimation("food-carousel.json");
//        lottieAnimationView.playAnimation();
//        lottieAnimationView.setRepeatCount(ValueAnimator.INFINITE);


    }
}