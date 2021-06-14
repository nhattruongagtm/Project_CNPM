package com.example.project_cnpm.Login;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_cnpm.MD5.MD5;
import com.example.project_cnpm.Model.Customer;
import com.example.project_cnpm.Model.User;
import com.example.project_cnpm.R;
import com.example.project_cnpm.SharedReferences.DataLocalManager;
import com.example.project_cnpm.SharedReferences.MyApplication;
import com.google.gson.JsonArray;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginDAO {
    private LoginActivity context;
    private boolean check;
    String result = "";
    private Handler handler;



    public LoginDAO(LoginActivity context) {
        this.context = context;
    }

    public void checkLogin(String email, String password){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "https://appfooddb.000webhostapp.com/checkLogin.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("successful")) {

                            getAccount(email, password);

                            check = true;
                            result = response;
                            DataLocalManager.setResultLogin("successful");


                            Message message = new Message();

                            message.what = 1;
                            message.arg1 = 0;
                            handler.sendMessage(message);

                            Log.d("RRR", "check1: " + check);

                        } else if (response.trim().equals("fail")) {

                            Message message = new Message();

                            message.what = 1;
                            message.arg1 = 1;
                            handler.sendMessage(message);

                            check = false;
                            result = response;
                            DataLocalManager.setResultLogin("fail");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("CCC", error.toString());
                        Message message = new Message();

                        message.what = 1;
                        message.arg1 = 2;
                        handler.sendMessage(message);
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
    private class LoginDAOAsyntask extends AsyncTask<Void, Void, Void> {

        private String email;
        private String password;

        public LoginDAOAsyntask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String url = "https://appfooddb.000webhostapp.com/checkLogin.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("successful")) {

                                getAccount(email, password);

                                check = true;
                                result = response;
                                DataLocalManager.setResultLogin("successful");

                                Log.d("RRR", "check1: " + check);

                            } else if (response.trim().equals("fail")) {

                                check = false;
                                result = response;
                                DataLocalManager.setResultLogin("fail");
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
            };
            requestQueue.add(stringRequest);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            result = DataLocalManager.getResultLogin();
        }
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
    public HashMap<String,String> getAllAccount(){
        HashMap<String,String> users = new HashMap<>();
        String url = "https://appfooddb.000webhostapp.com/getAllAccount.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            JSONObject object = new JSONObject();
                            try {
                                users.put(object.getString("email"),object.getString("password"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(stringRequest);

        Log.d("SSS",users.toString());

        return users;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
