package com.example.project_cnpm.Model;

import java.util.Date;

public class Price {
    private String idDish;
    private int price;
    private DateTime dateCreated;

    public Price(String idDish, int price, DateTime dateCreated) {
        this.idDish = idDish;
        this.price = price;
        this.dateCreated = dateCreated;
    }
    public Price(){

    }

    public String getIdDish() {
        return idDish;
    }

    public void setIdDish(String idDish) {
        this.idDish = idDish;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public DateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Price{" +
                "idDish='" + idDish + '\'' +
                ", price=" + price +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
