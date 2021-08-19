package com.example.project_cnpm.Controller;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_cnpm.DAO.DishDetailDAO;
import com.example.project_cnpm.DishesPage.DishDetailView;
import com.example.project_cnpm.Model.Dish;


public class DishDetailController {


    private DishDetailView view;

    private DishDetailDAO model;

    public DishDetailController(DishDetailView view, DishDetailDAO model) {
        this.view = view;
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

                    view.showDish(dish);
                }
            }
        };
        model.setContext(view);
        model.setHandler(handler);
        model.getDishById(id);
    }
}
