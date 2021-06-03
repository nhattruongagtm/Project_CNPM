package com.example.project_cnpm.Model;


import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {
    private String idCustomer;
    private User user;
    private String name;
    private String phone;
    private String address;
    private String avatar;
    private Date dateCreated;
    private int status;

    public Customer(String idCustomer, User user, String name, String phone, String address, String avatar, Date dateCreated, int status) {
        this.idCustomer = idCustomer;
        this.user = user;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.dateCreated = dateCreated;
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


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "idCustomer='" + idCustomer + '\'' +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                '}';
    }
}
