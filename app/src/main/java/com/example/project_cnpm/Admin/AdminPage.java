package com.example.project_cnpm.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.project_cnpm.Admin.DishesManagement.DishActivity;
import com.example.project_cnpm.Database.Database;
import com.example.project_cnpm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AdminPage extends AppCompatActivity {
    Button btnQLMA;
    ImageView btnMenuAdmin;

//      //AdminPage
//      Button btnQLMA;
//      ////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_page);
//        //AdminPage
//        btnQLMA = findViewById(R.id.btnQLMA);
//
//        btnQLMA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            Fragment f = new DishesManagementFragment();
//            loadFragment(f);
//            }
//        });
//        ////

//    private void loadFragment(Fragment fragment) {
//        // replace fragment
//        getSupportFragmentManager().beginTransaction().replace(R.id.admin_home,fragment).commit();
//        ////
        btnQLMA = findViewById(R.id.btnQLMA);
        btnMenuAdmin = findViewById(R.id.btnMenuAdmin);

        btnQLMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPage.this, DishActivity.class));
            }
        });
        btnMenuAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getBaseContext());
                dialog.setContentView(R.layout.dialog_admin);

                setPosition(dialog,120);

                dialog.show();
            }
        });
    }
    public void setPosition(Dialog dialog,int yValue) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.TOP | Gravity.LEFT;
        param.y = yValue;
        window.setAttributes(param);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

}