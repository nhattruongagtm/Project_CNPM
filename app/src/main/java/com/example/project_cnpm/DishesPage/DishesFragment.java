package com.example.project_cnpm.DishesPage;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_cnpm.HomePage.Dish;
import com.example.project_cnpm.R;

import java.util.ArrayList;
import java.util.Random;


public class DishesFragment extends Fragment {
    RecyclerView recyclerView;


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

        recyclerView = view.findViewById(R.id.recyler_dishes_page);

        createDishesView();



        return view;

    }
    public void createDishesView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        DishesPageApdater adapter = new DishesPageApdater(getActivity(),getDishes());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public ArrayList<DishPageModel> getDishes(){
        ArrayList<DishPageModel> dishes = new ArrayList<>();
        dishes.add(new DishPageModel("001","Pizza hải sản cao cấp",199000,R.drawable.item_dishes, Color.MAGENTA));
        dishes.add(new DishPageModel("001","Pizza hải sản cao cấp",199000,R.drawable.item_dishes, Color.YELLOW));
        dishes.add(new DishPageModel("001","Pizza hải sản cao cấp",199000,R.drawable.item_dishes, Color.BLUE));
        dishes.add(new DishPageModel("001","Pizza hải sản cao cấp",199000,R.drawable.item_dishes, Color.GRAY));
        dishes.add(new DishPageModel("001","Pizza hải sản cao cấp",199000,R.drawable.item_dishes, Color.LTGRAY));
        dishes.add(new DishPageModel("001","Pizza hải sản cao cấp",199000,R.drawable.item_dishes, Color.MAGENTA));


        for (DishPageModel d : dishes){
            Random rd = new Random();
            int i = rd.nextInt(getCorlors().size());
            d.setBackground(getCorlors().get(i));

        }
        return dishes;
    }
    private ArrayList<Integer> getCorlors(){
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(180,255,218));
        colors.add(Color.rgb(255,250,180));
        colors.add(Color.rgb(200,180,255));
        colors.add(Color.rgb(255,180,190));
        colors.add(Color.rgb(180,249,255));
        colors.add(Color.rgb(255,218,180));
        colors.add(Color.rgb(255,179,255));
        colors.add(Color.rgb(187,255,179));
        colors.add(Color.rgb(255,179,226));
        colors.add(Color.rgb(147,133,255));
        colors.add(Color.rgb(240,255,162));
        colors.add(Color.rgb(144,255,251));
        colors.add(Color.rgb(255,177,144));
        colors.add(Color.rgb(255,144,252));
        return colors;
    }
}