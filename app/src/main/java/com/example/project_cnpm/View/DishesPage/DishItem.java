package com.example.project_cnpm.View.DishesPage;

import java.io.Serializable;

public class DishItem implements Serializable {
    private String id;
    private String name;
    private int price;
    private String img;
    private int background;

    public DishItem(){

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    @Override
    public String toString() {
        return "DishPageModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", img=" + img +
                ", background=" + background +
                '}';
    }
}
