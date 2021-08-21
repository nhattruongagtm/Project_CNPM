package com.example.project_cnpm.Model;

public class Category {
    private String idCategory;
    private String name;
    private DateTime dateCreated;
    private int status;

    public Category(String idCategory, String name, DateTime dateCreated, int status) {
        this.idCategory = idCategory;
        this.name = name;
        this.dateCreated = dateCreated;
        this.status = status;
    }
    public Category(){

    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Category{" +
                "idCategory='" + idCategory + '\'' +
                ", name='" + name + '\'' +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                '}';
    }
}
