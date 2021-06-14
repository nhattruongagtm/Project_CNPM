package com.example.project_cnpm.DishesPage;

public class DishPageModel {
    private String id;
    private String name;
    private int price;
    private int img;
    private int background;


    public DishPageModel(String id, String name, int price, int img, int background) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.img = img;
        this.background = background;
    }
    public DishPageModel(){

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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
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
