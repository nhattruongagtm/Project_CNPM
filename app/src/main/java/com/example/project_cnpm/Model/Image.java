package com.example.project_cnpm.Model;

public class Image {
    private String idImage;
    private String linkImage;
    private String idDish;

    public Image(String idImage, String linkImage, String idDish) {
        this.idImage = idImage;
        this.linkImage = linkImage;
        this.idDish = idDish;
    }
    public Image(){

    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getIdDish() {
        return idDish;
    }

    public void setIdDish(String idDish) {
        this.idDish = idDish;
    }

    @Override
    public String toString() {
        return "Image{" +
                "idImage='" + idImage + '\'' +
                ", linkImage='" + linkImage + '\'' +
                ", idDish='" + idDish + '\'' +
                '}';
    }
}
