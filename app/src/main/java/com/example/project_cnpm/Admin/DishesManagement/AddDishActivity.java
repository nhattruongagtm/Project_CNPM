package com.example.project_cnpm.Admin.DishesManagement;

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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.project_cnpm.Admin.AddDishDAO;
import com.example.project_cnpm.Admin.Controller.AddDishController;
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

    AddDishController controller = new AddDishController(this,new AddDishDAO(this));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);


        anhxa();
//
//        // lấy id dish hiện tại
        controller.getIdDish();


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

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("categories");
        HashMap<String,String> categories = (HashMap<String, String>) bundle.getSerializable("categories");

        for (Map.Entry<String,String> m : categories.entrySet()){
                spin.add(m.getValue());
        }

        ArrayAdapter spinner = new ArrayAdapter(this, android.R.layout.simple_list_item_1,spin);

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
                showResult("Vui lòng chọn loại món ăn!");
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

//                if(nameDish.isEmpty() || describeDish.isEmpty() || (pM.equals("")&&pL.equals("")) || linkImage.size()==0){
//                    Toast.makeText(AddDishActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
//                }else{
                  // bat dau them mons a
                    ArrayList<Size> sizes = new ArrayList<>();
                    ArrayList<Price> prices = new ArrayList<>();
                    if (pM.equals("") && !pL.equals("")){
                        sizes.add(new Size("l","lớn"));
                        prices.add(new Price("",Integer.parseInt(pL),null));
                    }
                    else if (!pM.equals("") && pL.equals("")){
                        sizes.add(new Size("m","nhỏ"));
                        prices.add(new Price("",Integer.parseInt(pM),null));
                    }
                    else if (!pM.equals("") && !pL.equals("")){
                        sizes.add(new Size("m","nhỏ"));
                        sizes.add(new Size("l","lớn"));
                        prices.add(new Price("",Integer.parseInt(pM),null));
                        prices.add(new Price("",Integer.parseInt(pL),null));
                    }

                    controller.addDish(nameDish,describeDish,sizes,prices,linkImage);
//                }
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

    public Button getBtnSelectImage() {
        return btnSelectImage;
    }

    public void setBtnSelectImage(Button btnSelectImage) {
        this.btnSelectImage = btnSelectImage;
    }

    public RecyclerView getRcvPhoto() {
        return rcvPhoto;
    }

    public void setRcvPhoto(RecyclerView rcvPhoto) {
        this.rcvPhoto = rcvPhoto;
    }

    public PhotoAdapter getPhotoAdapter() {
        return photoAdapter;
    }

    public void setPhotoAdapter(PhotoAdapter photoAdapter) {
        this.photoAdapter = photoAdapter;
    }

    public TextInputEditText getName() {
        return name;
    }

    public void setName(TextInputEditText name) {
        this.name = name;
    }

    public TextInputEditText getPriceM() {
        return priceM;
    }

    public void setPriceM(TextInputEditText priceM) {
        this.priceM = priceM;
    }

    public TextInputEditText getPriceL() {
        return priceL;
    }

    public void setPriceL(TextInputEditText priceL) {
        this.priceL = priceL;
    }

    public TextInputEditText getDescribe() {
        return describe;
    }

    public void setDescribe(TextInputEditText describe) {
        this.describe = describe;
    }

    public CheckBox getCbS() {
        return cbS;
    }

    public void setCbS(CheckBox cbS) {
        this.cbS = cbS;
    }

    public CheckBox getCbL() {
        return cbL;
    }

    public void setCbL(CheckBox cbL) {
        this.cbL = cbL;
    }

    public Button getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(Button btnAdd) {
        this.btnAdd = btnAdd;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(Button btnCancel) {
        this.btnCancel = btnCancel;
    }

    public Spinner getCategory() {
        return category;
    }

    public void setCategory(Spinner category) {
        this.category = category;
    }

    public String getChooseCategory() {
        return chooseCategory;
    }

    public void setChooseCategory(String chooseCategory) {
        this.chooseCategory = chooseCategory;
    }

    public void setCategories(HashMap<String, String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getSpin() {
        return spin;
    }

    public void setSpin(ArrayList<String> spin) {
        this.spin = spin;
    }

    public ArrayList<Uri> getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(ArrayList<Uri> linkImage) {
        this.linkImage = linkImage;
    }

    public ArrayList<String> getLinkFirebase() {
        return linkFirebase;
    }

    public void setLinkFirebase(ArrayList<String> linkFirebase) {
        this.linkFirebase = linkFirebase;
    }

    public String getIdDish() {
        return idDish;
    }

    public HashMap<String, String> getCategories() {
        return categories;
    }

    public void setIdDish(String idDish) {
        this.idDish = idDish;
    }

    public String getUrlInsert() {
        return urlInsert;
    }

    public void setUrlInsert(String urlInsert) {
        this.urlInsert = urlInsert;
    }

    public void showResult(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
