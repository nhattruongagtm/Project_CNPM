package com.example.project_cnpm.DishesPage;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.project_cnpm.Database.Database;
import com.example.project_cnpm.Model.Dish;
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


public class DishesFragment extends Fragment implements View.OnClickListener{
    RecyclerView recyclerView;

    ColorStateList def;
    TextView item1, item2, item3, item4, tab;

    ArrayList<DishPageModel> dishes = new ArrayList<>();



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

        getDishes("ca_1");

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
            getDishes("ca_1");
        }
        else if(v.getId() == R.id.fragment_dishes_tab2){
            item2.setTextColor(Color.WHITE);
            item1.setTextColor(def);
            item3.setTextColor(def);
            item4.setTextColor(def);

            int size = item2.getWidth();
            tab.animate().x(size).setDuration(100);
            getDishes("ca_2");
        }
        else if(v.getId() == R.id.fragment_dishes_tab3){
            item3.setTextColor(Color.WHITE);
            item2.setTextColor(def);
            item1.setTextColor(def);
            item4.setTextColor(def);

            int size = item2.getWidth() * 2;
            tab.animate().x(size).setDuration(100);
            getDishes("ca_3");
        }
        else{
            item4.setTextColor(Color.WHITE);
            item2.setTextColor(def);
            item3.setTextColor(def);
            item1.setTextColor(def);

            int size = item2.getWidth() * 3;
            tab.animate().x(size).setDuration(100);
            dishes = new ArrayList<>();
            getDishes("ca_4");
        }

    }

    public void getDishes(String category){
        dishes = new ArrayList<>();
//        dishes.add(new DishPageModel("001","Pizza hải sản cao cấp",199000,R.drawable.item_dishes, Color.MAGENTA));
//        dishes.add(new DishPageModel("001","Pizza hải sản cao cấp",199000,R.drawable.item_dishes, Color.YELLOW));
//        dishes.add(new DishPageModel("001","Pizza hải sản cao cấp",199000,R.drawable.item_dishes, Color.BLUE));
//        dishes.add(new DishPageModel("001","Pizza hải sản cao cấp",199000,R.drawable.item_dishes, Color.GRAY));
//        dishes.add(new DishPageModel("001","Pizza hải sản cao cấp",199000,R.drawable.item_dishes, Color.LTGRAY));
//        dishes.add(new DishPageModel("001","Pizza hải sản cao cấp",199000,R.drawable.item_dishes, Color.MAGENTA));

        String url = "https://appfooddb.000webhostapp.com/getDishesByCategory.php";
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.d("dishes","AAA "+array.length());
                            for (int i = 0; i < array.length();i++){
                                try {
                                    JSONObject object = array.getJSONObject(i);
                                    Dish dish = new Dish();

                                    dish.setId(object.getString("idDish"));
                                    dish.setName(object.getString("name"));
                                    dish.setIdCategory(object.getString("idCategory"));
                                    dish.setStatus(Integer.parseInt(object.getString("status")));
                                    dish.setDescribe(object.getString("describe"));

                                    Log.d("dishes", dish.toString());

                                    DishPageModel dishPageModel = new DishPageModel();
                                    dishPageModel.setId(dish.getId());
                                    dishPageModel.setName(dish.getName());
                                    dishPageModel.setPrice(10000);
                                    dishPageModel.setId(dish.getId());
                                    dishes.add(dishPageModel);

                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

                                    DishesPageApdater adapter = new DishesPageApdater(getActivity(),dishes);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                    for (DishPageModel d : dishes){
                                        Random rd = new Random();
                                        int h = rd.nextInt(getCorlors().size());
                                        d.setBackground(getCorlors().get(h));

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("dishes", error.toString());
                    }
                }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("idCategory",category);
                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(jsonArrayRequest);




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