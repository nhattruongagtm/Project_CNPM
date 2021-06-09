package com.example.project_cnpm.Model;

import android.text.TextUtils;
import android.util.Patterns;

import java.io.Serializable;
import java.util.regex.Pattern;

public class User implements Serializable, IUser {
    private String email;
    private String password;
    private int status;

    public User(String email, String password, int status) {
        this.email = email;
        this.password = password;
        this.status = status;
    }
    public User(){

    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int isValid() {
        if(TextUtils.isEmpty(getEmail())){
            // không nhập email
            return 0;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()){
            // không đúng định dạng email
            return 1;
        }
        else if(getPassword().length() == 0 || getPassword() == null){
            // không nhập password
            return 2;
        }
        else{
            return -1;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }
}
