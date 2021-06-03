package com.example.project_cnpm.Login;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_cnpm.Model.Customer;
import com.example.project_cnpm.Model.User;
import com.example.project_cnpm.SharedReferences.DataLocalManager;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginDAO {
    private LoginActivity context;
    private boolean check;


    public LoginDAO(LoginActivity context) {
        this.context = context;
    }
    public static class CheckLoginDAO{

    }
    public boolean checkLogin(String email, String password) {
        String url = "https://appfooddb.000webhostapp.com/checkLogin.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("successful")) {

                            Log.d("CCC", response);
                            getAccount(email, password);

                            check = true;
                            DataLocalManager.setValid(true);

                            Log.d("RRR", "check1: "+ check);

                        } else if (response.trim().equals("fail")) {

                            check = false;

                        } else if (response.trim().equals("wrong password")) {

                            check = false;

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("CCC", error.toString());
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

            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        requestQueue.add(stringRequest).setSequence(1);
        Log.d("RRR", "check2: "+ check);
        return check;
    }
    public void getAccount(String email, String password) {
        Customer account = new Customer();
        String url = "http://appfooddb.000webhostapp.com/getAccount.php";
        if (email == null || password == null) {
            return;
        } else {
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String idCustomer = object.getString("idCustomer");
                                String name = object.getString("fullName");
                                String passwords = object.getString("password");
                                int status = object.getInt("status");
                                String phone = object.getString("phone");
                                String email = object.getString("email");
                                String imgCustomer = object.getString("imgCustomer");
                                String address = object.getString("address");
                                String d = object.getString("dateCreated");
                                String[] da = d.split("-");
                                Date date = new Date(Integer.parseInt(da[2]), Integer.parseInt(da[1]), Integer.parseInt(da[0]));

                                account.setIdCustomer(idCustomer);
                                account.setStatus(status);
                                account.setPhone(phone);
                                account.setDateCreated(date);
                                account.setAddress(address);
                                account.setName(name);
                                account.setAvatar(imgCustomer);
                                account.setUser(new User(email, passwords, status));

                                Log.d("CCC", account.toString());
                                Log.d("CCC", response.length() + "");

                                DataLocalManager.setAccount(account);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("CCC", "error: " + error.toString());
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
            requestQueue.add(jsonArrayRequest);
        }
    }
//    public static class Validate {
//        static String rs = "";
//        public Validate(){
//
//        }
//        public static String getValidateValue(String key) {
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference myRef = database.getReference(key);
//
//            myRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                    rs = snapshot.getValue(String.class);
//                }
//
//                @Override
//                public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                }
//            });
//            return rs;
//        }
//        public static void setValidateValue(String key, String value){
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference myRef = database.getReference(key);
//
//            myRef.setValue(value);
//        }
//    }


}
