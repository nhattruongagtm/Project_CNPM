package com.example.project_cnpm.Model;

import java.util.ArrayList;
import java.util.Date;

public class Dish {
    private String id;
    private String name;
    private String describe;
    private ArrayList<Size> size;
    private ArrayList<Price> price;
    private ArrayList<PriceSale> priceSale;
    private ArrayList<Image> img;
    private DateTime dateCreated;
    private int restNumber;
    private String idCategory;
    private int status;

    public Dish(String id, String name, String describe, ArrayList<Size> size, ArrayList<Price> price, ArrayList<PriceSale> priceSale, ArrayList<Image> img, DateTime dateCreated, int restNumber, String idCategory, int status) {
        this.id = id;
        this.name = name;
        this.describe = describe;
        this.size = size;
        this.price = price;
        this.priceSale = priceSale;
        this.img = img;
        this.dateCreated = dateCreated;
        this.restNumber = restNumber;
        this.idCategory = idCategory;
        this.status = status;
    }

    public Dish(){

    }

    public ArrayList<Image> getImg() {
        return img;
    }

    public void setImg(ArrayList<Image> img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public ArrayList<Size> getSize() {
        return size;
    }

    public void setSize(ArrayList<Size> size) {
        this.size = size;
    }

    public ArrayList<Price> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Price> price) {
        this.price = price;
    }

    public ArrayList<PriceSale> getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(ArrayList<PriceSale> priceSale) {
        this.priceSale = priceSale;
    }

    public DateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getRestNumber() {
        return restNumber;
    }

    public void setRestNumber(int restNumber) {
        this.restNumber = restNumber;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", priceSale=" + priceSale +
                ", img=" + img +
                ", dateCreated=" + dateCreated +
                ", restNumber=" + restNumber +
                ", idCategory='" + idCategory + '\'' +
                ", status=" + status +
                '}';
    }
}
