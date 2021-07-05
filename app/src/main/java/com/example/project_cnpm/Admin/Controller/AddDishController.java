package com.example.project_cnpm.Admin.Controller;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.project_cnpm.Admin.AddDishDAO;
import com.example.project_cnpm.Admin.DishesManagement.AddDishActivity;
import com.example.project_cnpm.Model.Dish;
import com.example.project_cnpm.Model.Image;
import com.example.project_cnpm.Model.Price;
import com.example.project_cnpm.Model.Size;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddDishController {
    AddDishActivity addDishView;
    AddDishDAO addDishModel;

    public AddDishController(AddDishActivity addDishView, AddDishDAO addDishModel) {
        this.addDishView = addDishView;
        this.addDishModel = addDishModel;
    }

    public void getCategories(){
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if (msg.what == 5){
                    Log.d("WWW","ok");
                    Bundle bundle = msg.getData();

                }
            }
        };
        addDishModel.setContext(addDishView);
        addDishModel.setHandler(handler);
    }

    public void addDish(String name, String describe, ArrayList<Size> size, ArrayList<Price> price, ArrayList<Uri> img){
//        if (name.equals("") || price.size() == 0 || idCategoy.equals("") || describe.equals("") || linkImage.size()==0){
//            addDishView.showResult("*Vui lòng nhập đủ thông tin!");
//        }
//        else{
            Dish dish = new Dish();
            dish.setId(addDishView.getIdDish());
            dish.setName(name);
            dish.setDescribe(describe);
            dish.setIdCategory(addDishView.getChooseCategory());
            dish.setSize(size);
            dish.setPrice(price);

            ArrayList<Image> images = new ArrayList<>();
            for (Uri uri : img){
                images.add(new Image("",uri.toString(),""));
            }
            dish.setImg(images);

            addDishModel.createDish(dish);
//        }
    }
    public void getIdDish(){
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if (msg.what == 12){
                    Bundle bundle = msg.getData();
                    String idDish = bundle.getString("idDish");
                    addDishView.setIdDish(idDish);
                }
            }
        };
        addDishModel.setHandler(handler);
        addDishModel.setContext(addDishView);
        addDishModel.getIdDish();
    }

}
