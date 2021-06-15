package com.example.project_cnpm.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project_cnpm.DishesManagement.DishesManagementFragment;
import com.example.project_cnpm.MainActivity;
import com.example.project_cnpm.R;

public class AdminPage extends AppCompatActivity {
    Button btnQLMA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_page);

        btnQLMA = findViewById(R.id.btnQLMA);

        btnQLMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPage.this, DishesManagementFragment.class));
            }
        });
    }
}