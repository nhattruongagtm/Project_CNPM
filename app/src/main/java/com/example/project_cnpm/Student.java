package com.example.project_cnpm;

public class Student {
    private String name;
    private String address;
    private String dob;
    private double grade;

    public Student(String name, String address, String dob, double grade) {
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
