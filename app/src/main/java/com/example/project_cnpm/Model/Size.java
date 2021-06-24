package com.example.project_cnpm.Model;

public class Size {
    private String idSize;
    private String nameSize;

    public Size(String idSize, String nameSize) {
        this.idSize = idSize;
        this.nameSize = nameSize;
    }
    public Size(){

    }

    public String getIdSize() {
        return idSize;
    }

    public void setIdSize(String idSize) {
        this.idSize = idSize;
    }

    public String getNameSize() {
        return nameSize;
    }

    public void setNameSize(String nameSize) {
        this.nameSize = nameSize;
    }

    @Override
    public String toString() {
        return "Size{" +
                "idSize='" + idSize + '\'' +
                ", nameSize='" + nameSize + '\'' +
                '}';
    }
}
