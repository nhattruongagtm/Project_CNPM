package com.example.project_cnpm.DAO;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_cnpm.Database.Database;
import com.example.project_cnpm.DishesPage.DetailDishActivity;
import com.example.project_cnpm.Model.DateTime;
import com.example.project_cnpm.Model.Dish;
import com.example.project_cnpm.Model.Image;
import com.example.project_cnpm.Model.Price;
import com.example.project_cnpm.Model.PriceSale;
import com.example.project_cnpm.Model.Size;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DishDetailDAO {
    private DetailDishActivity context;
    public Handler handler;
    public Dish dish;

    public DishDetailDAO() {

    }

    public DishDetailDAO(DetailDishActivity context) {
        this.context = context;
    }

    public void getDishById(String id){
        dish = new Dish();
        String url = "https://appfooddb.000webhostapp.com/getDishById.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("size",response);
                            JSONObject object = new JSONObject(response);

                            dish.setId(object.getString("idDish"));
                            dish.setName(object.getString("name"));
                            dish.setDescribe(object.getString("describe"));
                            dish.setDateCreated(new DateTime(object.getString("dateCreated")));
                            dish.setStatus(Integer.parseInt(object.getString("status")));
                            dish.setIdCategory(object.getString("idCategory"));

                            DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("get_dish");
                            database.setValue(dish);

                            // lay gia
                            JSONArray price = new JSONArray(object.getString("price"));
                            ArrayList<Price> prices = new ArrayList<>();
                            for (int i = 0; i< price.length();i++){
                                Price p = new Price();
                                JSONObject ob = price.getJSONObject(i);
                                p.setIdDish(ob.getString("idDish"));
                                p.setPrice(Integer.parseInt(ob.getString("price")));
                                p.setDateCreated(new DateTime(ob.getString("dateCreated")));
                                prices.add(p);
                            }
                            // lay gia khuyen mai
                            JSONArray priceSalse = new JSONArray(object.getString("priceSale"));
                            ArrayList<PriceSale> priceSales = new ArrayList<>();
                            for (int i = 0; i< priceSalse.length();i++){
                                PriceSale p = new PriceSale();
                                JSONObject ob = priceSalse.getJSONObject(i);
                                p.setIdDish(ob.getString("idDish"));
                                p.setPriceSale(Integer.parseInt(ob.getString("priceSale")));
                                p.setDateCreated(new DateTime(ob.getString("dateCreated")));
                                priceSales.add(p);
                            }
                            // lay hinh anh
                            JSONArray image = new JSONArray(object.getString("linkImage"));
                            ArrayList<Image> images = new ArrayList<>();
                            for (int i = 0; i< image.length();i++){
                                Image im = new Image();
                                JSONObject ob = image.getJSONObject(i);
                                im.setIdImage(ob.getString("idImage"));
                                im.setLinkImage(ob.getString("linkImage"));
                                im.setIdDish(ob.getString("idDish"));
                                images.add(im);
                            }
                            // lay size
                            JSONArray size = new JSONArray(object.getString("size"));
                            ArrayList<Size> sizes = new ArrayList<>();
                            for (int i = 0; i< size.length();i++){
                                Size p = new Size();
                                JSONObject ob = size.getJSONObject(i);
                                p.setIdSize(ob.getString("idSize"));
                                p.setNameSize(ob.getString("nameSize"));
                                sizes.add(p);
                            }
                            dish.setImg(images);
                            dish.setSize(sizes);
                            dish.setPrice(prices);
                            dish.setPriceSale(priceSales);

                            context.setDish(dish);

                            // send message
                            Message message = new Message();
                            message.what = 15;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("dish", dish);
                            message.setData(bundle);
                           // handler = new Handler();
                            handler.sendMessage(message);

                           // des.setText(dish.getDescribe());

                            Log.d("SSS", message.toString()+"");

                            // hiển thị số hình ảnh
                          //  numberImg.setText(dish.getImg().size()+"");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Loi tai mon an", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("idDish",id);
                return params;
            }
        };
        Database.getInstance(context).excuteQuery(stringRequest);
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public DetailDishActivity getContext() {
        return context;
    }

    public void setContext(DetailDishActivity context) {
        this.context = context;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
