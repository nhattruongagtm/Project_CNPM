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


    public void addDish(String idDish,String nameDish,String describeDish,ArrayList<Integer> price,ArrayList<Image> linkImage, String chooseCategory){
        Dish dish = new Dish();
        dish.setId(idDish);
        dish.setName(nameDish);
        dish.setDescribe(describeDish);
        dish.setIdCategory(chooseCategory);

        ArrayList<Price> prices = new ArrayList<>();
        ArrayList<Size> sizes = new ArrayList<>();
        // nếu có 2 giá thì sẽ có 2 size
        if (price.size() == 2){

            sizes.add(new Size("m","nhỏ"));
            sizes.add(new Size("l","lớn"));

            prices.add(new Price("",price.get(0),null));
            prices.add(new Price("",price.get(1),null));
        }
        // không nhập size nhỏ
        else if (price.get(0) == -1 && price.get(1) != -1){

            sizes.add(new Size("l","lớn"));
            prices.add(new Price("",price.get(1),null));
        }
        // không nhập size lớn
        else if (price.get(0) != -1 && price.get(1) == -1){

            sizes.add(new Size("m","nhỏ"));
            prices.add(new Price("",price.get(0),null));
        }
        // không nhập gì cả
        else {
           // Toast.makeText(context, "Vui lòng chọn size và giá!", Toast.LENGTH_SHORT).show();
        }

        dish.setPrice(prices);
        dish.setSize(sizes);

        // tạo dish lên firebase
        //createDishToFirebase(dish.getId(),linkImage);

        //upload ảnh online

//        for (Uri uri : linkImage){
//            uploadImage(uri,idDish);
//        }

        // lấy ảnh về firebase
//        ArrayList<Image> images = new ArrayList<>();
//        for (int i = 0; i < linkFirebase.size();i++){
//            images.add(new Image("",linkFirebase.get(i),""));
//        }
//        dish.setImg(images);

        // sau đó tiến hành thêm món ăn
//        createDish(dish);

        Log.d("NNN",dish.toString());

        Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show();
    }
    public void createDish(Dish dish){
        // thêm thông tin cơ bản: tên, mô tả, loại.
        createDishInfoBase(dish);

        // thêm danh sách ảnh
         for (Image m : dish.getImg()){
             uploadImage(Uri.parse(m.getLinkImage()),dish.getId());
         }

        // thêm danh sách size
        for (Size s : dish.getSize()){
            createDishSize(dish.getId(),s.getIdSize());
        }

        // thêm giá
        for (Price p : dish.getPrice()){
            createDishPrice(dish.getId(),p.getPrice());
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
                params.put("idCategory",dish.getIdCategory());
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
                        // lấy link fire them vao image của dish
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
                Log.d("NNN","không thành công!");
            }
        });
    }
    public void createDishToFirebase(String idDish, ArrayList<Uri> linkImage){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("dish");
        database.child(idDish).child("linkImage").setValue(linkImage);
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
