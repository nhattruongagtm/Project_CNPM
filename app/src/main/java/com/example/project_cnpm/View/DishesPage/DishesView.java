package com.example.project_cnpm.View.DishesPage;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project_cnpm.Controller.DishController;
import com.example.project_cnpm.DAO.DishDAO;
import com.example.project_cnpm.Model.DishView;
import com.example.project_cnpm.R;

import java.util.ArrayList;


public class DishesView extends Fragment implements View.OnClickListener, DishView {
    RecyclerView recyclerView;

    ColorStateList def;
    TextView item1, item2, item3, item4, tab;

    DishController dishController = new DishController(new DishDAO(this),this);


    public DishesView() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_dishes, container, false);


        dishController.getDishes("ca_1");

        recyclerView = view.findViewById(R.id.recyler_dishes_page);

        mapping(view);

        handler();

        item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        item3.setOnClickListener(this);
        item4.setOnClickListener(this);



        return view;

    }
    public void mapping(View view){
        item1 = view.findViewById(R.id.fragment_dishes_tab1);
        item2 = view.findViewById(R.id.fragment_dishes_tab2);
        item3 = view.findViewById(R.id.fragment_dishes_tab3);
        item4 = view.findViewById(R.id.fragment_dishes_tab4);

        tab = view.findViewById(R.id.fragment_dishes_tabs);

    }
    public void handler(){
        def = item2.getTextColors();
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_dishes_tab1){
            tab.animate().x(0).setDuration(100);
            item1.setTextColor(Color.WHITE);
            item2.setTextColor(def);
            item3.setTextColor(def);
            item4.setTextColor(def);

            dishController.getDishes("ca_1");
        }
        else if(v.getId() == R.id.fragment_dishes_tab2){
            item2.setTextColor(Color.WHITE);
            item1.setTextColor(def);
            item3.setTextColor(def);
            item4.setTextColor(def);

            int size = item2.getWidth();
            tab.animate().x(size).setDuration(100);

            dishController.getDishes("ca_2");
        }
        else if(v.getId() == R.id.fragment_dishes_tab3){
            item3.setTextColor(Color.WHITE);
            item2.setTextColor(def);
            item1.setTextColor(def);
            item4.setTextColor(def);

            int size = item2.getWidth() * 2;
            tab.animate().x(size).setDuration(100);

            dishController.getDishes("ca_3");
        }
        else{
            item4.setTextColor(Color.WHITE);
            item2.setTextColor(def);
            item3.setTextColor(def);
            item1.setTextColor(def);

            int size = item2.getWidth() * 3;
            tab.animate().x(size).setDuration(100);

            dishController.getDishes("ca_4");

        }
    }

    @Override
    public void show() {

    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void showDishes(ArrayList<DishItem> dishes){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        DishesPageApdater adapter = new DishesPageApdater(this.getContext(),dishes);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



}