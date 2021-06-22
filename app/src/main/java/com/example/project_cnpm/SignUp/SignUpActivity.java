package com.example.project_cnpm.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_cnpm.Controller.SignUpController;
import com.example.project_cnpm.DAO.SignUpDAO;
import com.example.project_cnpm.Login.LoginActivity;
import com.example.project_cnpm.MainActivity;
import com.example.project_cnpm.R;
import com.example.project_cnpm.View.ISignUpView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements ISignUpView {
    TextView btnChangeLogin;
    LinearLayout btnBack;

    EditText email, password, repassword;
    Button btnSignUp;
    CheckBox cb;
    TextView notify;
    public HashMap<String,String> accounts = new HashMap<>();


    SignUpController signUpController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        mapping();
        getAllAccount();


        signUpController = new SignUpController(this,new SignUpDAO(this));

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
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String repass = repassword.getText().toString().trim();

                if(mail.equals("") && pass.equals("") && repass.equals("")){
                    showSignUpFail("*Vui lòng nhập đủ thông tin!");
                }
                else if (!cb.isChecked()){
                    showSignUpFail("*Vui lòng chấp nhận thỏa thuận của chúng tôi!");
                }
                else if(!pass.equals(repass)){
                    showSignUpFail("*Mật khẩu không khớp!");
                }
                else if(signUpController.signup(mail,pass)){
                    Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                    intent.putExtra("email_signup",mail);
                    intent.putExtra("notify","Đăng ký thành công!");
                    startActivity(intent);
                }
            }
        });
    }

    public void mapping(){
        btnChangeLogin = findViewById(R.id.changeLogin);
        btnBack = findViewById(R.id.btnBack_hompage);
        email = findViewById(R.id.email_signup);
        password = findViewById(R.id.pass_signup);
        repassword = findViewById(R.id.repass_signup);
        cb = findViewById(R.id.check_signup);
        btnSignUp = findViewById(R.id.btn_signup);
        notify = findViewById(R.id.notify_signup);
    }
    public void getAllAccount(){
        String url = "https://appfooddb.000webhostapp.com/getAllAccount.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length();i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                accounts.put(object.getString("email"),object.getString("password"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(stringRequest);
    }

    @Override
    public void showSignUpFail(String message) {
        notify.setText(message);
        notify.setTextColor(Color.RED);
    }

    @Override
    public void showSignUpSuccess(String message) {
        notify.setText(message);
        notify.setTextColor(Color.GREEN);
    }
}