package com.example.project_cnpm.Model;

public interface IUser {
    public String getEmail();
    public void setEmail(String email);
    public String getPassword();
    public void setPassword(String password);
    public int getStatus();
    public void setStatus(int status);
    public int isValid();
}
