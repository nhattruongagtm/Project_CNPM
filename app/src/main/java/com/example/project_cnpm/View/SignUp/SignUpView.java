package com.example.project_cnpm.View.SignUp;

import androidx.annotation.NonNull;
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
import com.example.project_cnpm.View.Login.LoginActivity;
import com.example.project_cnpm.MainActivity;
import com.example.project_cnpm.Model.User;
import com.example.project_cnpm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUpView extends AppCompatActivity{
    TextView btnChangeLogin;
    LinearLayout btnBack;

    EditText email, password, repassword;
    Button btnSignUp;
    CheckBox cb;
    TextView notify;
    public HashMap<String,String> accounts = new HashMap<>();
    public ArrayList<User> accountFirebase = new ArrayList<>();

    SignUpController signUpController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        mapping();
        getAllAccount();
        getAccountFirebase();

        signUpController = new SignUpController(this,new SignUpDAO(this));

        btnChangeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpView.this, LoginActivity.class));
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpView.this, MainActivity.class));
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String repass = repassword.getText().toString().trim();

                if(mail.equals("") && pass.equals("") && repass.equals("")){
                    showSignUpFail("*Vui l??ng nh???p ????? th??ng tin!");
                }
                else if(!cb.isChecked() && !mail.equals("") && !pass.equals("") && !repass.equals("") && repass.equals(pass) && pass.length() >= 8){
                    showSignUpFail("*Vui l??ng ch???p nh???n th???a thu???n!");
                }
                else {
                    signUpController.signup(mail,pass,repass);
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
    public void getAccountFirebase(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("account");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    accountFirebase.add(user);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(SignUpView.this, "L???i firebase", Toast.LENGTH_SHORT).show();
            }
        });
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
                        Toast.makeText(SignUpView.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(stringRequest);
    }

    public void showSignUpFail(String message) {
        notify.setText(message);
        notify.setTextColor(Color.RED);
    }

    public void showSignUpSuccess(String message) {
        notify.setText(message);
        notify.setTextColor(Color.GREEN);
    }
}