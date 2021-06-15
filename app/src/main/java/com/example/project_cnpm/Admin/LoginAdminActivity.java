package com.example.project_cnpm.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.project_cnpm.R;

public class LoginAdminActivity extends AppCompatActivity {

    LinearLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login_admin);

        mapping();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void mapping(){
        btnBack = findViewById(R.id.btnBack_hompage);

    }
}