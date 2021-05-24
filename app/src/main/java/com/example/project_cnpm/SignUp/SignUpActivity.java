package com.example.project_cnpm.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project_cnpm.Login.LoginActivity;
import com.example.project_cnpm.MainActivity;
import com.example.project_cnpm.R;

public class SignUpActivity extends AppCompatActivity {
    TextView btnChangeLogin;
    LinearLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        btnChangeLogin = findViewById(R.id.changeLogin);
        btnBack = findViewById(R.id.btnBack_hompage);

        btnChangeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }
        });
    }
}