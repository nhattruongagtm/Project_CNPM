package com.example.project_cnpm.Admin.DishesManagement;

public class Dish {
    private String id;
    private String name;
    private String category;
    private String sizeS;
    private int priceS;
    private String sizeL;
    private int priceL;
    private String description;

    public Dish(String id, String name, String category, String sizeS, int priceS, String sizeL, int priceL, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.sizeS = sizeS;
        this.priceS = priceS;
        this.sizeL = sizeL;
        this.priceL = priceL;
        this.description = description;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSizeS() {
        return sizeS;
    }

    public void setSizeS(String sizeS) {
        this.sizeS = sizeS;
    }

    public int getPriceS() {
        return priceS;
    }

    public void setPriceS(int priceS) {
        this.priceS = priceS;
    }

    public String getSizeL() {
        return sizeL;
    }

    public void setSizeL(String sizeL) {
        this.sizeL = sizeL;
    }

    public int getPriceL() {
        return priceL;
    }

    public void setPriceL(int priceL) {
        this.priceL = priceL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}