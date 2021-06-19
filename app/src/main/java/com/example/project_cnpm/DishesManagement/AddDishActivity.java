package com.example.project_cnpm.DishesManagement;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_cnpm.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class AddDishActivity extends AppCompatActivity {

        private Button btnCancel;
        private Button btnSelectImage;
        private RecyclerView rcvPhoto;
        private PhotoAdapter photoAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_dish);

            btnSelectImage = findViewById(R.id.btn_select_image);
            rcvPhoto = findViewById(R.id.rcv_photo);
            btnCancel = findViewById(R.id.btnCancelAdd);
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


            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AddDishActivity.this, DishesManagementFragment.class));
                }
            });
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
