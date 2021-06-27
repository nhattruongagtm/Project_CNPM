package com.example.project_cnpm.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project_cnpm.R;

public class LoginAdminActivity extends AppCompatActivity {

    LinearLayout btnBack;

    TextView username,password,notify;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login_admin);

        mapping();

        handlerLogin();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void handlerLogin(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUsername = username.getText().toString().trim();
                String mPassword = password.getText().toString().trim();
                if (mUsername.equals("") || mPassword.equals("")){
                    notify.setText("*Vui lòng nhập đủ thông tin!");
                    notify.setTextColor(Color.RED);
                }
                else if (mUsername.equals("admin") && mPassword.equals("admin")){
                    notify.setTextColor(Color.GREEN);
                    notify.setText("*Đăng nhập thành công!");
                    startActivity(new Intent(LoginAdminActivity.this,AdminPage.class));
                }
                else{
                    notify.setText("*Tên tài khoản hoặc mật khẩu không chính xác!");
                    notify.setTextColor(Color.RED);
                }
            }
        });
    }
    private void mapping(){
        btnBack = findViewById(R.id.btnBack_hompage);
        username = findViewById(R.id.activity_login_admin_username);
        password = findViewById(R.id.activity_login_admin_password);
        btnLogin = findViewById(R.id.activity_login_admin_btnlogin);
        notify = findViewById(R.id.activity_login_admin_notify);

    }
}