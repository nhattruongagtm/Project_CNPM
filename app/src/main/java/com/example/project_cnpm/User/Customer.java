package com.example.project_cnpm.User;

import java.io.Serializable;

public class Customer implements Serializable {
    private User user;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String avatar;
    private boolean status;

    public Customer(User user, String name, String email, String phone, String address, String avatar, boolean status) {
        this.user = user;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.status = status;
    }

    public Customer(){

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "user=" + user +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                '}';
    }
}
