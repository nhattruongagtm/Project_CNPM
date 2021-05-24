package com.example.project_cnpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.project_cnpm.HomePage.Dish;
import com.example.project_cnpm.HomePage.DishRecommendAdapter;
import com.example.project_cnpm.HomePage.DishTodayAdapter;
import com.example.project_cnpm.Login.LoginActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView btnLoginMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // khởi tạo recyler view today
        createDishesToday();
        // khởi tạo recyler view recommend
        createDishesRecommended();

    }
    public void mapping(){

    }
    public void createDishesToday(){
        RecyclerView recyclerView = findViewById(R.id.home_page_dishToday_recyler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DishTodayAdapter adapter = new DishTodayAdapter(this,getDishesToday());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    public void createDishesRecommended(){
        RecyclerView recyclerView = findViewById(R.id.home_page_dishRecommended);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DishRecommendAdapter adapter = new DishRecommendAdapter(this,getDishesToday());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public ArrayList<Dish> getDishesToday(){
        ArrayList<Dish> list = new ArrayList<>();
        list.add(new Dish("001","Pizza hải sản cao cấp",159000,0,"Pizza",true,R.drawable.pizza_home_page_item));
        list.add(new Dish("001","Pizza hải sản cao cấp",159000,0,"Pizza",true,R.drawable.pizza_home_page_item));
        list.add(new Dish("001","Pizza hải sản cao cấp",159000,0,"Pizza",true,R.drawable.pizza_home_page_item));
        list.add(new Dish("001","Pizza hải sản cao cấp",159000,0,"Pizza",true,R.drawable.pizza_home_page_item));
        return list;
    }
}