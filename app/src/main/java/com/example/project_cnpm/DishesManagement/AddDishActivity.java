package com.example.project_cnpm.DishesManagement;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_cnpm.Database.Database;
import com.example.project_cnpm.Model.Dish;
import com.example.project_cnpm.Model.Image;
import com.example.project_cnpm.Model.Price;
import com.example.project_cnpm.Model.Size;
import com.example.project_cnpm.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class AddDishActivity extends AppCompatActivity {
    Button btnSelectImage;
    RecyclerView rcvPhoto;
    PhotoAdapter photoAdapter;
    TextInputEditText name, priceM, priceL, describe;
    CheckBox cbS, cbL;
    Button btnAdd, btnCancel;
    Spinner category;
    String chooseCategory = "";
    HashMap<String,String> categories = new HashMap<>();
    ArrayList<String> spin = new ArrayList<>();
    ArrayList<Uri> linkImage = new ArrayList<>();
    ArrayList<String> linkFirebase = new ArrayList<>();
    String idDish = "";

    String urlInsert = "https://appfooddb.000webhostapp.com/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);

        anhxa();

        getCategories();

        // lấy id dish hiện tại
        getIdDish();


        btnSelectImage = findViewById(R.id.btn_select_image);
        rcvPhoto = findViewById(R.id.rcv_photo);
        photoAdapter = new PhotoAdapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        rcvPhoto.setLayoutManager(gridLayoutManager);
        rcvPhoto.setAdapter(photoAdapter);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });


//        cbS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    Toast.makeText(AddDishActivity.this, "Thêm size S", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(AddDishActivity.this, "Bỏ size S", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        cbL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    Toast.makeText(AddDishActivity.this, "Thêm size L", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(AddDishActivity.this, "Bỏ size L", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("NNN",new PhotoAdapter(AddDishActivity.this).getItemCount()+"");

                String nameDish = name.getText().toString().trim();
                String pM = priceM.getText().toString().trim();
                String pL = priceL.getText().toString().trim();
                String describeDish = describe.getText().toString().trim();

                if(nameDish.isEmpty() || describeDish.isEmpty() || (pM.equals("")&&pL.equals("")) || linkImage.size()==0){
                    Toast.makeText(AddDishActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                  // bat dau them mons an
                    Dish dish = new Dish();
                    dish.setId(idDish);
                    dish.setName(nameDish);
                    dish.setDescribe(describeDish);
                    dish.setIdCategory(chooseCategory);

                    ArrayList<Price> prices = new ArrayList<>();
                    ArrayList<Size> sizes = new ArrayList<>();
                    // nếu có 2 giá thì sẽ có 2 size
                    if (!pM.equals("") &&!pL.equals("")){

                        sizes.add(new Size("m","nhỏ"));
                        sizes.add(new Size("l","lớn"));

                        prices.add(new Price("",Integer.parseInt(pM),null));
                        prices.add(new Price("",Integer.parseInt(pL),null));
                    }
                    // không nhập size nhỏ
                    else if (pM.equals("") && !pL.equals("")){

                        sizes.add(new Size("l","lớn"));
                        prices.add(new Price("",Integer.parseInt(pL),null));
                    }
                    // không nhập size lớn
                    else if (!pM.equals("") && pL.equals("")){

                        sizes.add(new Size("m","nhỏ"));
                        prices.add(new Price("",Integer.parseInt(pM),null));
                    }
                    // không nhập gì cả
                    else {
                        Toast.makeText(AddDishActivity.this, "Vui lòng chọn size và giá!", Toast.LENGTH_SHORT).show();
                    }

                    dish.setPrice(prices);
                    dish.setSize(sizes);

                    // tạo dish lên firebase
                    //createDishToFirebase(dish.getId(),linkImage);

                    //upload ảnh online
                    for (Uri uri : linkImage){
                        uploadImage(uri,idDish);
                    }

                    // lấy ảnh về firebase
                    ArrayList<Image> images = new ArrayList<>();
                    for (int i = 0; i < linkFirebase.size();i++){
                        images.add(new Image("",linkFirebase.get(i),""));
                    }
                    dish.setImg(images);

                    // sau đó tiến hành thêm món ăn
                    createDish(dish);

                    Log.d("NNN",dish.toString());

                    Toast.makeText(AddDishActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void anhxa(){
        btnAdd = findViewById(R.id.btnAddDish);
        btnCancel = findViewById(R.id.btnCancelAdd);
        name = findViewById(R.id.txtEditName);
        category = findViewById(R.id.category_add);
        priceM = findViewById(R.id.txtEditPriceS);
        priceL = findViewById(R.id.txtEditPriceL);
        describe = findViewById(R.id.txtDescription);
    }
            private void requestPermission(){
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        selectImageFromGallery();
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        Toast.makeText(AddDishActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                    }
                };
                TedPermission.with(this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .check();
            }
            private void selectImageFromGallery(){
                TedBottomPicker.with(AddDishActivity.this)
                        .setPeekHeight(1600)
                        .showTitle(false)
                        .setCompleteButtonText("Done")
                        .setEmptySelectionText("No Select")
                        .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                            @Override
                            public void onImagesSelected(List<Uri> uriList) {
                                if(uriList != null && !uriList.isEmpty()){
                                    photoAdapter.setData(uriList);
                                    linkImage = (ArrayList<Uri>) photoAdapter.mListPhotos;
                                }
                            }
                        });
            }
     public void createDish(Dish dish){
         // thêm thông tin cơ bản: tên, mô tả, loại.
         createDishInfoBase(dish);

         // thêm danh sách ảnh
//         for (Image m : dish.getImg()){
//             createDishImage(dish.getId(),m.getLinkImage());
//         }

         // thêm danh sách size
         for (Size s : dish.getSize()){
             createDishSize(dish.getId(),s.getIdSize());
         }

         // thêm giá
         for (Price p : dish.getPrice()){
             createDishPrice(dish.getId(),p.getPrice());
         }

     }
    private void getCategories(){
        // lấy danh mục
        String url = "https://appfooddb.000webhostapp.com/getCategories.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i< array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                String id = object.getString("idCategory");
                                String name = object.getString("name");
                                categories.put(id,name);
                                spin.add(name);
                            }
                            ArrayAdapter spinner = new ArrayAdapter(AddDishActivity.this, android.R.layout.simple_list_item_1,spin);

                            category.setAdapter(spinner);

                            category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String s =  parent.getItemAtPosition(position).toString();
                                        for (Map.Entry<String,String> m : categories.entrySet()){
                                            if (s.equals(m.getValue())){
                                                chooseCategory = m.getKey();
                                            }
                                        }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    Toast.makeText(AddDishActivity.this, "Vui lòng chọn loại món ăn!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddDishActivity.this, "Loi load danh muc!", Toast.LENGTH_SHORT).show();
                    }
                });
        Database.getInstance(this).excuteQuery(stringRequest);

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
        Database.getInstance(this).excuteQuery(stringRequest);
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
        Database.getInstance(this).excuteQuery(stringRequest);
    }
    private void createDishSize(String idDish,String idSize){
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
        Database.getInstance(this).excuteQuery(stringRequest);
    }
    private void createDishPrice(String idDish,int price){
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
        Database.getInstance(this).excuteQuery(stringRequest);
    }
    private void uploadImage(Uri link, String idDish){

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
    private void createDishToFirebase(String idDish, ArrayList<Uri> linkImage){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("dish");
        database.child(idDish).child("linkImage").setValue(linkImage);
    }
    public void getIdDish(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("dish");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                idDish = "dish_"+(snapshot.getChildrenCount()+1);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
