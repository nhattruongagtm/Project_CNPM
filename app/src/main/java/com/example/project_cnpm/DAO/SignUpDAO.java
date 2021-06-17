package com.example.project_cnpm.DAO;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_cnpm.SignUp.SignUpActivity;

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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SSS",response);
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
        requestQueue.add(stringRequest);
    }

    public boolean signUp(String email, String password) {
        checkSignUp(email, password);
        context.getAllAccount();
        for (Map.Entry<String, String> m : context.accounts.entrySet()) {
            if (m.getKey().equals(email) && m.getKey().equals(password)) {
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

    public static void main(String[] args) {
        SignUpActivity context = new SignUpActivity();
        SignUpDAO test = new SignUpDAO(context);
        test.sendMail("nhattruongagtmzxc@gmail.com");
    }
}
