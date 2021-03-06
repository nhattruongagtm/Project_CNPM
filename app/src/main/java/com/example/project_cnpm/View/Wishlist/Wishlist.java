package com.example.project_cnpm.View.Wishlist;

public class Wishlist {

    private String name;
    private String description;
    private double price;
    private int quantity;
    private int img;

    public Wishlist(String name, String description, double price, int quantity, int img) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String toString(){
        return name +" - "+ description+ " - " + price;
    }
}
