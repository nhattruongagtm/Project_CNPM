package com.example.project_cnpm.DishesManagement;

public class DishManagement {
    private String id;
    private String name;
    private int price;
    private int priceSale;
    private String category;
    private boolean status;
    private int img;

    public DishManagement(String id, String name, int price, int priceSale, String category, boolean status, int img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.priceSale = priceSale;
        this.category = category;
        this.status = status;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(int priceSale) {
        this.priceSale = priceSale;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", priceSale=" + priceSale +
                ", category='" + category + '\'' +
                ", status=" + status +
                '}';
    }
}
