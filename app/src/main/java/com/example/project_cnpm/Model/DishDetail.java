package com.example.project_cnpm.Model;

public class DishDetail {
    private String idDish;
    private String idSize;
    private int restNumber;
    private int status;

    public DishDetail(String idDish, String idSize, int restNumber, int status) {
        this.idDish = idDish;
        this.idSize = idSize;
        this.restNumber = restNumber;
        this.status = status;
    }
    public DishDetail(){

    }

    public String getIdDish() {
        return idDish;
    }

    public void setIdDish(String idDish) {
        this.idDish = idDish;
    }

    public String getIdSize() {
        return idSize;
    }

    public void setIdSize(String idSize) {
        this.idSize = idSize;
    }

    public int getRestNumber() {
        return restNumber;
    }

    public void setRestNumber(int restNumber) {
        this.restNumber = restNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DishDetail{" +
                "idDish='" + idDish + '\'' +
                ", idSize='" + idSize + '\'' +
                ", restNumber=" + restNumber +
                ", status=" + status +
                '}';
    }
}
