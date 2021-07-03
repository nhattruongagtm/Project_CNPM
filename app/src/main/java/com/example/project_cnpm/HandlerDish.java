package com.example.project_cnpm;

public class HandlerDish {
    private static HandlerDish handler;

    public static HandlerDish getInstance(){
        if (handler == null){
            handler = new HandlerDish();
        }
        return handler;
    }
    public HandlerDish(){

    }



}
