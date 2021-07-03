package com.example.project_cnpm.DAO;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
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
import com.example.project_cnpm.Model.User;
import com.example.project_cnpm.SharedReferences.DataLocalManager;
import com.example.project_cnpm.SignUp.SignUpActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

    public HashMap<String, String> accounts = new HashMap<>();
    public ArrayList<User> acc = new ArrayList<>();

    String idCustomer = "";
    public String result = "";

    public SignUpDAO(SignUpActivity context) {
        this.context = context;

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("account");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                idCustomer = "kh_" + (snapshot.getChildrenCount() + 1);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(context, "không thể lấy idCustomer", Toast.LENGTH_SHORT).show();
            }
        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    acc.add(user);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(context, "không thể lấy idCustomer", Toast.LENGTH_SHORT).show();
            }
        });
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
                        Log.d("SSS", response);
                        writeData(email, password);
                        acc.add(new User(email,password,1));
                        accounts.put(email,password);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("SSS", "Lỗi kết nối! Vui lòng thử lại!");
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

    public void signUp(String email, String password) {

       // checkSignUp(email,password);

//        SignUpDAO dao = new SignUpDAO(context);
//
////        if (DataLocalManager.getUser() != null){
////            acc.add(DataLocalManager.getUser());
////        }
//
//      //  Log.d("SSS","accountFirebase: "+ context.accountFirebase.toString());
//        Log.d("SSS","account: "+ acc.toString());
//        Log.d("SSS","account: "+ DataLocalManager.getUser().toString());
//
//        for (User user : dao.acc){
//            if (email.equals(user.getEmail()) || password.equals(user.getPassword())){
//                return true;
//            }
//            else return false;
//        }
        final String[] result = {""};

        String url = "https://appfooddb.000webhostapp.com/signUp.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SSS", response);
                        writeData(email, password);
                        acc.add(new User(email,password,1));
                        accounts.put(email,password);
                        result[0] = response;
                        DataLocalManager.setValid(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("SSS", "Lỗi kết nối! Vui lòng thử lại!");
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

        waitResult();

//        Log.d("SSS","result"+ DataLocalManager.isValid());
//        if (result[0].equals("successful")){
//            return true;
//        }
      //  return false;
    }
    public boolean waitResult(){
        for (int i = 0;i < 5;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            if(result!=""){
                return true;
            }
        }
        return false;
    }

    public void sendMail1(String emailTo) {
        String sEmail = "appcnpm2021@gmail.com";
        String sPassword = "yjghgtlshghopoyi";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sEmail, sPassword);
            }
        });

        try {
            // create content
            Message message = new MimeMessage(session);
            // send mail
            message.setFrom(new InternetAddress(sEmail));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("Xác nhận tài khoản TVL Food");
            message.setText("Bạn đã đăng ký tài khoản thành công!");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void writeData(String email, String password) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("account");

        myRef.child(idCustomer).child("email").setValue(email);
        myRef.child(idCustomer).child("password").setValue(password);
        myRef.child(idCustomer).child("status").setValue(1);

        accounts.put(email, password);

    }

    public void getAllAccount() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d("signup", idCustomer);
        DatabaseReference myRef = database.getReference().child("account");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    accounts.put(user.getEmail(), user.getPassword());
                }
                Log.d("SSS", "firebase: " + accounts.toString());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                return;
            }
        });
    }

    public void sendMail(String emailTo) {
        String sEmail = "appcnpm2021@gmail.com";
        String sPassword = "yjghgtlshghopoyi";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sEmail, sPassword);
            }
        });

        try {
            // create content
            Message message = new MimeMessage(session);
            // send mail
            message.setFrom(new InternetAddress(sEmail));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("Xác nhận tài khoản TVL Food");
            message.setText("Đăng ký tài khoản TVL Food thành công! ");
            new SendMail().execute(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private class SendMail extends AsyncTask<Message, String, String> {


        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);

                return "success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "error";
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            if (s.equals("success")) {
                Log.d("mail", "gui mail thanh cong!");
            } else {
                Log.d("mail", "gui mail khong thanh cong!");
            }
        }
    }

}
