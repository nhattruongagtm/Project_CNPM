package com.example.project_cnpm.Login;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_cnpm.HomePage.LoginFragment;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class LoginDAO {
    private LoginFragment context;
    public LoginDAO(LoginFragment context){
        this.context = context;
    }
    public void checkLogin(String username, String pass){
        String url = "";
        RequestQueue requestQueue = Volley.newRequestQueue(context.getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
           //     params.put("username",context.e)
                return super.getParams();
            }
        };


    }

}
