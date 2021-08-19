package com.example.project_cnpm.DAO;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.project_cnpm.Database.Database;
import com.example.project_cnpm.DishesPage.DishItem;
import com.example.project_cnpm.DishesPage.DishesView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DishDAO {
    DishesView context;
    public Handler handler;
    public ArrayList<DishItem> dishes = new ArrayList<>();

    public DishDAO(DishesView context) {
        this.context = context;
    }

    public void getDishes(String idCategory){

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

                                    DishItem dishPageModel = new DishItem();
                                    dishPageModel.setId(object.getString("idDish"));
                                    dishPageModel.setName(object.getString("name"));
                                    dishPageModel.setPrice(Integer.parseInt(object.getString("price")));
                                    dishPageModel.setImg(object.getString("linkImage"));

                                    dishes.add(dishPageModel);

                                    for (DishItem d : dishes){
                                        Random rd = new Random();
                                        int h = rd.nextInt(getCorlors().size());
                                        d.setBackground(getCorlors().get(h));

                                    }

                                    Message message = new Message();
                                    message.what = 25;
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("dishes",dishes);
                                    message.setData(bundle);
                                    handler.sendMessage(message);

                                    Log.d("WWW",dishes.toString());


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
                params.put("idCategory",idCategory);
                return params;
            }
        };
        Database.getInstance(context.getActivity()).excuteQuery(jsonArrayRequest);

        dishes = new ArrayList<>();

        Log.d("WWW",dishes.toString());
    }
    public ArrayList<Integer> getCorlors(){
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

    public DishesView getContext() {
        return context;
    }

    public void setContext(DishesView context) {
        this.context = context;
    }
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }


}
