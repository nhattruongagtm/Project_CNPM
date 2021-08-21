package com.example.project_cnpm.Controller;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.project_cnpm.DAO.DishDAO;
import com.example.project_cnpm.DishesPage.DishItem;
import com.example.project_cnpm.DishesPage.DishesView;
import com.example.project_cnpm.DishesPage.DishesPageApdater;

import java.util.ArrayList;


public class DishController {
    private DishDAO dishModel;
    private DishesView dishView;

    public DishController(DishDAO dishModel, DishesView dishView) {
        this.dishModel = dishModel;
        this.dishView = dishView;
    }

    public void getDishes(String idCategory){

        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if (msg.what == 25){
                    Bundle bundle = msg.getData();
                    ArrayList<DishItem> dishes = (ArrayList<DishItem>) bundle.getSerializable("dishes");
                    dishView.showDishes(dishes);
                }
            }
        };
        dishModel.setHandler(handler);
        dishModel.setContext(dishView);
        dishModel.getDishes(idCategory);
    }
}
