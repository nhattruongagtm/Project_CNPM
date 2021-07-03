package com.example.project_cnpm.DishesPage;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.controls.Control;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_cnpm.Controller.DishController;
import com.example.project_cnpm.DAO.DishDAO;
import com.example.project_cnpm.Database.Database;
import com.example.project_cnpm.Model.DateTime;
import com.example.project_cnpm.Model.Dish;
import com.example.project_cnpm.Model.DishView;
import com.example.project_cnpm.Model.Price;
import com.example.project_cnpm.Model.Size;
import com.example.project_cnpm.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class DishesFragment extends Fragment implements View.OnClickListener, DishView {
    RecyclerView recyclerView;

    ColorStateList def;
    TextView item1, item2, item3, item4, tab;

    ArrayList<DishPageModel> dishes = new ArrayList<>();

    DishController dishController = new DishController(new DishDAO(this),this);

   // DishController controller = new DishController(new DishDAO(this,recyclerView),this);



    public DishesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dishes, container, false);




        dishController.getDishes("ca_1");
       // Log.d("SSS",new DishDAO(this,recyclerView).dishes.toString());

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

//            controller.getDishes("ca_1");
        //    Log.d("SSS",new DishDAO(this,recyclerView).dishes.toString());
           dishController.getDishes("ca_1");
        }
        else if(v.getId() == R.id.fragment_dishes_tab2){
            item2.setTextColor(Color.WHITE);
            item1.setTextColor(def);
            item3.setTextColor(def);
            item4.setTextColor(def);

            int size = item2.getWidth();
            tab.animate().x(size).setDuration(100);
//            controller.getDishes("ca_1");
          //  Log.d("SSS",new DishDAO(this,recyclerView).dishes.toString());
            dishController.getDishes("ca_2");
        }
        else if(v.getId() == R.id.fragment_dishes_tab3){
            item3.setTextColor(Color.WHITE);
            item2.setTextColor(def);
            item1.setTextColor(def);
            item4.setTextColor(def);

            int size = item2.getWidth() * 2;
            tab.animate().x(size).setDuration(100);
           // controller.getDishes("ca_1");
         //   Log.d("SSS",new DishDAO(this,recyclerView).dishes.toString());
            dishController.getDishes("ca_3");
        }
        else{
            item4.setTextColor(Color.WHITE);
            item2.setTextColor(def);
            item3.setTextColor(def);
            item1.setTextColor(def);

            int size = item2.getWidth() * 3;
            tab.animate().x(size).setDuration(100);
           // controller.getDishes("ca_1");
       //     Log.d("SSS",new DishDAO(this,recyclerView).dishes.toString());

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

    public ColorStateList getDef() {
        return def;
    }

    public void setDef(ColorStateList def) {
        this.def = def;
    }

    public TextView getItem1() {
        return item1;
    }

    public void setItem1(TextView item1) {
        this.item1 = item1;
    }

    public TextView getItem2() {
        return item2;
    }

    public void setItem2(TextView item2) {
        this.item2 = item2;
    }

    public TextView getItem3() {
        return item3;
    }

    public void setItem3(TextView item3) {
        this.item3 = item3;
    }

    public TextView getItem4() {
        return item4;
    }

    public void setItem4(TextView item4) {
        this.item4 = item4;
    }

    public TextView getTab() {
        return tab;
    }

    public void setTab(TextView tab) {
        this.tab = tab;
    }

    public ArrayList<DishPageModel> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<DishPageModel> dishes) {
        this.dishes = dishes;
    }
}