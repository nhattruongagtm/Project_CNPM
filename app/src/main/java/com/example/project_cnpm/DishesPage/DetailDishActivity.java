package com.example.project_cnpm.DishesPage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.project_cnpm.R;


public class DetailDishActivity extends AppCompatActivity {
    ImageView btnBack;
    FrameLayout background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dish);



        mapping();

        Intent intent = getIntent();
        Log.d("aaa",intent.getIntExtra("backgroundColor",0)+"");
        if (intent !=null) {
            background.setBackgroundColor(intent.getIntExtra("backgroundColor",0));
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void mapping() {
        btnBack = findViewById(R.id.btnBack_from_detail);
        background = findViewById(R.id.background_detail);
    }
}