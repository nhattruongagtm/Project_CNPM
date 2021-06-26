package com.example.project_cnpm.DishesManagement;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

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
import com.example.project_cnpm.R;
import com.google.android.material.textfield.TextInputEditText;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class AddDishActivity extends AppCompatActivity {
    Button btnSelectImage;
    RecyclerView rcvPhoto;
    PhotoAdapter photoAdapter;
    TextInputEditText txtTenMonAn, txtLoai, txtGiaS, txtGiaL, txtMoTa;
    CheckBox cbS, cbL;
    Button btnThem, btnHuy;

    String urlInsert = "https://appfooddb.000webhostapp.com/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);

        anhxa();

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

        cbS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(AddDishActivity.this, "Thêm size S", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddDishActivity.this, "Bỏ size S", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cbL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(AddDishActivity.this, "Thêm size L", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddDishActivity.this, "Bỏ size L", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenmonan = txtTenMonAn.getText().toString().trim();
                String loai = txtLoai.getText().toString().trim();
                String sizes = "";
                String gias = txtGiaS.getText().toString().trim();;
                String sizel = "";
                String gial = txtGiaL.getText().toString().trim();
                String mota = txtMoTa.getText().toString().trim();

                if(cbS.isChecked()){
                    sizes += cbS.getText().toString().trim();
                }
                if(cbL.isChecked()){
                    sizel += cbL.getText().toString().trim();
                }

                if(tenmonan.isEmpty() || loai.isEmpty() || mota.isEmpty() ){
                    Toast.makeText(AddDishActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    ThemMonAn(urlInsert);
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void ThemMonAn(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("Success")){
                            Toast.makeText(AddDishActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddDishActivity.this, DishActivity.class));
                        }else {
                            Toast.makeText(AddDishActivity.this, "Lỗi thêm!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddDishActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "Lỗi!\n" + error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tenMA",txtTenMonAn.getText().toString().trim());
                params.put("loaiMA",txtLoai.getText().toString().trim());
                params.put("sizeSMA",cbS.getText().toString().trim());
                params.put("giaSMA",txtGiaS.getText().toString().trim());
                params.put("sizeLMA",cbL.getText().toString().trim());
                params.put("giaLMA",txtGiaL.getText().toString().trim());
                params.put("motaMA",txtMoTa.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void anhxa(){
        btnThem = findViewById(R.id.btnAddDish);
        btnHuy = findViewById(R.id.btnCancelAdd);
        txtTenMonAn = findViewById(R.id.txtEditName);
        txtLoai = findViewById(R.id.txtEditCategory);
        txtGiaS = findViewById(R.id.txtEditPriceS);
        txtGiaL = findViewById(R.id.txtEditPriceL);
        txtMoTa = findViewById(R.id.txtDescription);
        cbS = findViewById(R.id.cbS);
        cbL = findViewById(R.id.cbL);
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
                                }
                            }
                        });
            }
}
