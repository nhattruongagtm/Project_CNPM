package com.example.project_cnpm.Controller;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.project_cnpm.DAO.DishDetailDAO;
import com.example.project_cnpm.DishesPage.DetailDishActivity;
import com.example.project_cnpm.Model.Dish;
import com.example.project_cnpm.Model.DishDetail;


public class DetailDishController {


    private DetailDishActivity view;

    private DishDetailDAO model;

    public DetailDishController(DetailDishActivity view, DishDetailDAO model) {
        this.view = view;
        this.model = model;
    }

    public DetailDishController(DishDetailDAO model) {
        this.model = model;
    }

    public void getDishById(String id) {

        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                Log.d("QQQ", msg.toString() + "");

                if (msg.what == 15) {
                    Bundle bundle = msg.getData();
                    Dish dish = (Dish) bundle.getSerializable("dish");
                    Log.d("QQQ", "yes" + dish.toString());
                    view.getName().setText(dish.getName());
                    view.getDes().setText(dish.getDescribe());
                    view.getPrice().setText(dish.getPrice().get(0).getPrice() + " VNĐ");
                    Glide.with(view).load(dish.getImg().get(0).getLinkImage()).into(view.getImg());
                    view.getNumberImg().setText(dish.getImg().size()+"");
                }
            }
        };
        model.setContext(view);
        model.setHandler(handler);
        model.getDishById(id);
    }
}
