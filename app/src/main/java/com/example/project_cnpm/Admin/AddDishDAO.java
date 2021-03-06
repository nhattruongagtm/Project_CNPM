package com.example.project_cnpm.Admin;

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
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.project_cnpm.Admin.DishesManagement.AddDishActivity;
import com.example.project_cnpm.Database.Database;
import com.example.project_cnpm.Model.Dish;
import com.example.project_cnpm.Model.Image;
import com.example.project_cnpm.Model.Price;
import com.example.project_cnpm.Model.Size;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddDishDAO {

    private AddDishActivity context;
    private HashMap<String,String> categories = new HashMap<>();
    public Handler handler;
    public ArrayList<String> listImage = new ArrayList<>();
    ArrayList<String> linkFirebase = new ArrayList<>();
    public String idDish;
    public AddDishDAO(AddDishActivity context) {
        this.context = context;
    }

    public void createDish(Dish dish){
        // th??m th??ng tin c?? b???n: t??n, m?? t???, lo???i.
        createDishInfoBase(dish);


        // th??m danh s??ch ???nh
         for (Image m : dish.getImg()){
             uploadImage(Uri.parse(m.getLinkImage()),dish.getId());
         }

        // th??m danh s??ch size
        for (Size s : dish.getSize()){
            createDishSize(dish.getId(),s.getIdSize());
        }

        // th??m gi??
        for (Price p : dish.getPrice()){
            try{
                createDishPrice(dish.getId(),p.getPrice());
                Thread.sleep(1000);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        Log.d("WWW",dish.toString());

    }

    public void createDishInfoBase(Dish dish){
        String url ="https://appfooddb.000webhostapp.com/createDishInfoBase.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("add","can not add dish!");
            }
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("name",dish.getName());
                params.put("describe",dish.getDescribe());
                params.put("idCategory",dish.getCategory().getIdCategory());
                return params;
            }
        };
        Database.getInstance(context).excuteQuery(stringRequest);
    }

    public void createDishImage(String idDish,String linkImage){
        String url ="https://appfooddb.000webhostapp.com/createDishImage.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")){
                            Log.d("NNN","success");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("add","can not add image!");
            }
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("idDish",idDish);
                params.put("linkImage",linkImage);
                return params;
            }
        };
        Database.getInstance(context).excuteQuery(stringRequest);
    }

    public void createDishSize(String idDish,String idSize){
        String url ="https://appfooddb.000webhostapp.com/createDishSize.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")){
                            Log.d("add","success");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("add","can not add image!");
            }
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("idDish",idDish);
                params.put("idSize",idSize);
                return params;
            }
        };
        Database.getInstance(context).excuteQuery(stringRequest);
    }

    public void createDishPrice(String idDish,int price){
        String url ="https://appfooddb.000webhostapp.com/createDishPrice.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")){
                            Log.d("add","success");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("add","can not add image!");
            }
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("idDish",idDish);
                params.put("price",price+"");
                return params;
            }
        };
        Database.getInstance(context).excuteQuery(stringRequest);
    }

    public void uploadImage(Uri link, String idDish){

        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("dish");

        Log.d("NNN",storageRef.toString());

        long mis = System.currentTimeMillis();
        StorageReference mountainsRef = storageRef.child(idDish+"/"+mis+".jpg");

        Log.d("NNN",mountainsRef.toString());

        mountainsRef.putFile(link).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //Toast.makeText(ProfileActivity.this, "Upload successful!", Toast.LENGTH_LONG).show();

                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // l???y link fire them vao image c???a dish
                        String url = uri.toString();
                        Log.d("NNN",url);
                        createDishImage(idDish,url);
                        linkFirebase.add(url);
//                            imgAccount = url;
//                            changeImageAccount(account.getId(),url);
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference("dish").child(idDish);
                        database.child("link_hinh_dai_dien").setValue(url);
                    }
                });

            }

        });
        mountainsRef.putFile(link).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d("NNN","kh??ng th??nh c??ng!");
            }
        });
    }

    public void getIdDish(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("dish");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                idDish = "dish_"+(snapshot.getChildrenCount()+1);

                Message message = new Message();
                message.what = 12;
                Bundle bundle = new Bundle();
                bundle.putString("idDish",idDish);
                message.setData(bundle);
                handler.sendMessage(message);

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public AddDishActivity getContext() {
        return context;
    }

    public void setContext(AddDishActivity context) {
        this.context = context;
    }

    public void setCategories(HashMap<String, String> categories) {
        this.categories = categories;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
