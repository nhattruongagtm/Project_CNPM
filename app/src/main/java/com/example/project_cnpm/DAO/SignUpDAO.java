package com.example.project_cnpm.DAO;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_cnpm.Database.Database;
import com.example.project_cnpm.SignUp.SignUpActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SignUpDAO {
    SignUpActivity context;

    HashMap<String,String> accounts = new HashMap<>();

    public SignUpDAO(SignUpActivity context) {
        this.context = context;
    }

    public boolean checkEmail(String email) {
        for (Map.Entry<String, String> m : context.accounts.entrySet()) {
            if (m.getKey().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void checkSignUp(String email, String password) {
        String url = "https://appfooddb.000webhostapp.com/signUp.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SSS",response);
                        writeData(email,password);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("SSS",error.toString());
                    }
                }) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        Database.getInstance(context).excuteQuery(stringRequest);
    }

    public boolean signUp(String email, String password) {
        checkSignUp(email, password);
        getAllAccount();
        Log.d("SSS",accounts.toString());
        for (Map.Entry<String, String> m : accounts.entrySet()) {
            if (m.getKey().equals(email) && m.getValue().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public void sendMail(String emailTo){
        String sEmail = "eatsimple2021@gmail.com";
        String sPassword = "iacjphdbzujyglyy";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sEmail,sPassword);
            }
        });

        try {
            // create content
            Message message = new MimeMessage(session);
            // send mail
            message.setFrom(new InternetAddress(sEmail));

            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailTo));
            message.setSubject("Xác nhận tài khoản FFood");
            message.setText("Bạn đã đăng ký tài khoản thành công!");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void writeData(String email,String password){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("account");

        for (Map.Entry<String,String> m : context.accounts.entrySet()){
            myRef.child(setKey(m.getKey())).child("email").setValue(m.getKey());
            myRef.child(setKey(m.getKey())).child("password").setValue(m.getValue());

        }
        myRef.child(setKey(email)).child("email").setValue(email);
        myRef.child(setKey(email)).child("password").setValue(password);
    }
    public void getAllAccount(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("account");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String key = ds.getValue(String.class);
                    String value = ds.getValue(String.class);
                    accounts.put(key,value);
                }
                Log.d("SSS",accounts.toString());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                return;
            }
        });
    }
    public String setKey(String key){
        return key.substring(0,key.indexOf("@gmail.com"));
    }


}
