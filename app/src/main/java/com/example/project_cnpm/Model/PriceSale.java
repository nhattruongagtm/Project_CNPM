package com.example.project_cnpm.Model;

public class PriceSale {
    private String idDish;
    private int priceSale;
    private DateTime dateCreated;

    public PriceSale(String idDish, int priceSale, DateTime dateCreated) {
        this.idDish = idDish;
        this.priceSale = priceSale;
        this.dateCreated = dateCreated;
    }
    public PriceSale(){

    }

    public String getIdDish() {
        return idDish;
    }

    public void setIdDish(String idDish) {
        this.idDish = idDish;
    }

    public int getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(int priceSale) {
        this.priceSale = priceSale;
    }

    public DateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "PriceSale{" +
                "idDish='" + idDish + '\'' +
                ", priceSale=" + priceSale +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
