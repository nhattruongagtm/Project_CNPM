package com.example.project_cnpm.Controller;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.project_cnpm.DAO.DishDAO;
import com.example.project_cnpm.DishesPage.DishPageModel;
import com.example.project_cnpm.DishesPage.DishesFragment;
import com.example.project_cnpm.DishesPage.DishesPageApdater;
import com.example.project_cnpm.Model.Dish;
import com.example.project_cnpm.Model.DishView;

import java.util.ArrayList;
import java.util.Random;


public class DishController {
    private DishDAO dishModel;
    private DishesFragment dishView;

    public DishController(DishDAO dishModel, DishesFragment dishView) {
        this.dishModel = dishModel;
        this.dishView = dishView;
    }

    public void getDishes(String id){

        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if (msg.what == 25){
                    Bundle bundle = msg.getData();
                    ArrayList<DishPageModel> dishes = (ArrayList<DishPageModel>) bundle.getSerializable("dishes");

                    dishView.getRecyclerView().setHasFixedSize(true);
                    dishView.getRecyclerView().setLayoutManager(new GridLayoutManager(dishView.getContext(), 2));

                    DishesPageApdater adapter = new DishesPageApdater(dishView.getContext(),dishes);
                    dishView.getRecyclerView().setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }
        };
        dishModel.setHandler(handler);
        dishModel.setContext(dishView);
        dishModel.getDishes(id);
    }
}
